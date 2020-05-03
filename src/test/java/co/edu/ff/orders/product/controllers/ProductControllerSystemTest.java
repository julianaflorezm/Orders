package co.edu.ff.orders.product.controllers;

import co.edu.ff.orders.product.domain.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ProductControllerSystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void createAndFindProduct() throws Exception {
        ProductOperationRequest request = getProductActual();
        String actual = this.gson.toJson(request);
        Product productCreated = getProductCreated();
        ProductOperation answer = ProductOperationSuccess.of(productCreated);
        String expect = this.gson.toJson(answer);
        mockMvc.perform(
                post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(actual)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect));
        mockMvc.perform(
                get("/api/v1/products/2")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect));

    }


    @Test
    void CreateAndListProducts() throws Exception {
        ProductOperationRequest request1 = getProductActual();
        String actual1 = this.gson.toJson(request1);
        Product productCreated1 = getProductCreated();
        ProductOperation answer1 = ProductOperationSuccess.of(productCreated1);
        String expect1 = this.gson.toJson(answer1);
        ProductOperationRequest request2 = getProductActualMultiple();
        String actual2 = this.gson.toJson(request2);
        Product productCreated2 = getProductCreatedMultiple();
        ProductOperation answer2 = ProductOperationSuccess.of(productCreated2);
        String expect2 = this.gson.toJson(answer2);
        mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actual1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect1));
        mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actual2)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect2));
        List<Product> productList = new ArrayList<>();
        productList.add(productCreated1);
        productList.add(productCreated2);
        String gsonList = this.gson.toJson(productList);
        mockMvc.perform(
                get("/api/v1/products")
        )
                .andDo(print())
                .andExpect(content().json(gsonList));
    }

    @Test
    void deleteAndList() throws Exception {
        ProductOperationRequest request1 = getProductActual();
        String actual1 = this.gson.toJson(request1);
        Product productCreated1 = getProductCreated();
        ProductOperation answer1 = ProductOperationSuccess.of(productCreated1);
        String expect1 = this.gson.toJson(answer1);
        ProductOperationRequest request2 = getProductActualMultiple();
        String actual2 = this.gson.toJson(request2);
        Product productCreated2 = getProductCreatedMultiple();
        ProductOperation answer2 = ProductOperationSuccess.of(productCreated2);
        String expect2 = this.gson.toJson(answer2);

        //creating
        mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actual1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect1));
        mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actual2)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect2));

        //deleting one
        mockMvc.perform(
                delete("/api/v1/products/2")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expect1));

        //Creating list
        List<Product> productList = new ArrayList<>();
        productList.add(productCreated2);
        String gsonList = this.gson.toJson(productList);
        mockMvc.perform(
                get("/api/v1/products")
        )
                .andDo(print())
                .andExpect(content().json(gsonList));
    }

    Product getProductCreated(){
        return Product.from(
                ProductId.of(2L),
                Name.of("ProductName"),
                Description.of("ProductDescription"),
                BasePrice.of(new BigDecimal(123)),
                TaxRate.of(new BigDecimal(0.2)),
                ProductStatus.BORRADOR,
                InventoryQuantity.of(12)
        );
    }

    Product getProductCreatedMultiple(){
        return Product.from(
                ProductId.of(3L),
                Name.of("ProductNameMultiple"),
                Description.of("ProductDescriptionMultiple"),
                BasePrice.of(new BigDecimal(456)),
                TaxRate.of(new BigDecimal(0.6)),
                ProductStatus.PUBLICADO,
                InventoryQuantity.of(98)
        );
    }

    ProductOperationRequest getProductActual(){
        return ProductOperationRequest.from(
                Name.of("ProductName"),
                Description.of("ProductDescription"),
                BasePrice.of(new BigDecimal(123)),
                TaxRate.of(new BigDecimal(0.2)),
                ProductStatus.BORRADOR,
                InventoryQuantity.of(12)
        );
    }

    ProductOperationRequest getProductActualMultiple(){
        return ProductOperationRequest.from(
                Name.of("ProductNameMultiple"),
                Description.of("ProductDescriptionMultiple"),
                BasePrice.of(new BigDecimal(456)),
                TaxRate.of(new BigDecimal(0.6)),
                ProductStatus.PUBLICADO,
                InventoryQuantity.of(98)
        );
    }
}

