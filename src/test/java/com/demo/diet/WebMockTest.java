package com.demo.diet;

import com.demo.diet.model.Product;
import com.demo.diet.model.ProductDto;
import com.demo.diet.service.ProductsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // need for integration tests instead of @WebMvcTest
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService productsService;

    @Test
    void shouldReturnNameOfTheProductWhenGivenProperEndpoint() throws Exception {
        // given
        when(productsService.getAllProducts())
                .thenReturn(List.of(
                        new Product().setName("Bread").setKcal(200),
                        new Product().setName("Milk").setKcal(300)
                ));

        // then
        this.mockMvc.perform(get("/api/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bread")));
    }

    @Test
    void shouldReturnJsonObjectWhenGivenProperJsonBody() throws Exception {
        // given
        ProductDto productDto = new ProductDto();
        productDto.setName("Bread");
        productDto.setKcal(200);

        // then
        this.mockMvc.perform(post("/api/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDto)))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
