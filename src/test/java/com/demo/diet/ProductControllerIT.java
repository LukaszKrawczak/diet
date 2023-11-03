package com.demo.diet;

import com.demo.diet.model.NutrientsDto;
import com.demo.diet.model.ProductDto;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql({"/scripts/schema.sql", "/data/clearAll.sql", "/data/testData.sql"})
    public void shouldReturnProductWhenExistsInDbTables() throws Exception {
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
    public void shouldReturnSecondProductWithDifferentIdWhenExistsInDbTables() throws Exception {
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
    public void shouldReturnNotFoundWhenProductNotExistsInDbTables() throws Exception {
        // given 🤣
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content);
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
