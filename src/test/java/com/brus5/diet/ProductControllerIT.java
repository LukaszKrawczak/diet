package com.brus5.diet;

import com.brus5.diet.dto.NutrientsDto;
import com.brus5.diet.dto.ProductDto;
import com.brus5.diet.model.ApiError;
import com.brus5.diet.model.ApiValidationError;
import com.brus5.diet.model.ApiSubError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import helper.ApiSubErrorDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql({"/scripts/schema.sql", "/data/clearAll.sql", "/data/testData.sql"})
    public void shouldReturn200AndProductWhenExistsInDbTables() throws Exception {
        // given
        ProductDto expected = ProductDto.builder()
                .name("Test product")
                .nutrientsDto(
                        NutrientsDto.builder()
                                .proteins(123)
                                .carbohydrates(123)
                                .fats(123)
                                .build()
                )
                .build();
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(asJsonString(expected), content);
    }

    @Test
    @Sql({"/scripts/schema.sql", "/data/clearAll.sql", "/data/testData.sql"})
    public void shouldReturn200AndSecondProductWithDifferentIdWhenExistsInDbTables() throws Exception {
        // given
        ProductDto expected = ProductDto.builder()
                .name("Test product 2")
                .nutrientsDto(
                        NutrientsDto.builder()
                                .proteins(123)
                                .carbohydrates(123)
                                .fats(123)
                                .build()
                )
                .build();
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/products/2"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(asJsonString(expected), content);
    }

    @Test
    @Sql({"/scripts/schema.sql", "/data/clearAll.sql", "/data/testData.sql"})
    public void shouldReturn404AndWhenProductNotExistsInDbTables() throws Exception {
        // given 🤣
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ApiError apiError = objectMapper.readValue(content, ApiError.class);
        assertEquals(apiError.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(apiError.getDebugMessage(), "Product with id '999' not found");
        assertEquals(apiError.getMessage(), "Entity not found");
    }

    @Test
    @Sql({"/scripts/schema.sql", "/data/clearAll.sql", "/data/testData.sql"})
    public void shouldReturn409WhenTryingToAddNewProduct() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .name("Test product")
                .nutrientsDto(
                        NutrientsDto.builder()
                                .proteins(123)
                                .carbohydrates(123)
                                .fats(123)
                                .build()
                )
                .build();
        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/products/add")
                        .content(asJsonString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        String content = mvcResult.getResponse().getContentAsString();
        ApiError apiError = getMappedApiError(content);
        assertEquals(apiError.getStatus(), HttpStatus.CONFLICT);
        assertEquals(apiError.getDebugMessage(), "Product with name 'Test product' already exists");
        assertEquals(apiError.getMessage(), "Entity already exists");
    }

    @Test
    @Sql({"/scripts/schema.sql", "/data/clearAll.sql"})
    public void shouldReturn201AndAddProductToDbTablesWhenThereIsNotSimilarProductInDb() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .name("Test product")
                .nutrientsDto(
                        NutrientsDto.builder()
                                .proteins(123)
                                .carbohydrates(123)
                                .fats(123)
                                .build()
                )
                .build();
        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/products/add")
                        .content(asJsonString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(asJsonString(productDto), content);
    }

    @Test
    public void shouldReturn400AndSubErrorMessagesWhenValidationFails() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .name("T")
                .nutrientsDto(
                        NutrientsDto.builder()
                                .proteins(-1)
                                .carbohydrates(-1)
                                .fats(-1)
                                .build()
                )
                .build();
        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/products/add")
                        .content(asJsonString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        // then
        String content = mvcResult.getResponse().getContentAsString();
        ApiError apiError = getMappedApiError(content);
        List<ApiValidationError> apiValidationErrors = new ArrayList<>();
        apiValidationErrors.add(new ApiValidationError("productDto", "name", "T", "The length of full name must be between 2 and 100 characters."));
        apiValidationErrors.add(new ApiValidationError("productDto", "nutrientsDto.carbohydrates", -1, "Carbohydrates must be positive or zero."));
        apiValidationErrors.add(new ApiValidationError("productDto", "nutrientsDto.fats", -1, "Fats must be positive or zero."));
        apiValidationErrors.add(new ApiValidationError("productDto", "nutrientsDto.proteins", -1, "Proteins must be positive or zero."));
        assertEquals(apiValidationErrors.size(), apiError.getSubError().size());
        assertEquals(apiValidationErrors, apiError.getSubError());
        assertEquals(apiError.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(apiError.getMessage(), "Bad input data");
    }

    @Test
    public void shouldReturn415AndReturnErrorMessageWhenUnsupportedHttpMediaType() throws Exception {
        // given
        ProductDto productDto = ProductDto.builder()
                .name("TTT")
                .nutrientsDto(
                        NutrientsDto.builder()
                                .proteins(123)
                                .carbohydrates(123)
                                .fats(123)
                                .build()
                )
                .build();

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/products/add")
                        .content(asJsonString(productDto))
                        .contentType(MediaType.APPLICATION_JSON + "MalformedData"))
                .andExpect(status().isUnsupportedMediaType())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        ApiError apiError = getMappedApiError(content);
        assertEquals(apiError.getStatus(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        assertEquals(apiError.getDebugMessage(), "Content-Type 'application/jsonmalformeddata;charset=UTF-8' is not supported");
        assertEquals(apiError.getMessage(), "Unsupported media type");
        assertNull(apiError.getSubError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ApiError getMappedApiError(String content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ApiSubError.class, new ApiSubErrorDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(content, ApiError.class);
    }

}
