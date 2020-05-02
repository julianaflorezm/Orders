package co.edu.ff.orders.product.controllers;

import co.edu.ff.orders.product.domain.*;
import co.edu.ff.orders.product.exceptions.ProductAlreadyExists;
import co.edu.ff.orders.product.exceptions.ProductDoesNotExist;
import co.edu.ff.orders.product.exceptions.ProductException;
import co.edu.ff.orders.product.services.ProductServices;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private ProductServices services;

    @Test
    void createOne() throws Exception {
        Product productCreated = getProductCreated();
        ProductOperationRequest requestProduct = getProductSended();
        ProductOperation productOperation = ProductOperationSuccess.of(productCreated);
        String answer = this.gson.toJson(productOperation);
        String content = this.gson.toJson(requestProduct);
        when(services.createProduct(any(ProductOperationRequest.class)))
                .thenReturn(productOperation);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/products").contentType("application/json").content(content);
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(answer));
    }

    @Test
    void createProductAlreadyExists() throws Exception {
        ProductException exception = ProductAlreadyExists.of(Name.of("ProductName123"));
        ProductOperation productOperation = ProductOperationFailure.of(exception);
        ProductOperationRequest productRequest = getProductSended();
        String content = this.gson.toJson(productRequest);
        String answer = this.gson.toJson(productOperation);
        when(services.createProduct(any(ProductOperationRequest.class)))
                .thenReturn(productOperation);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/products").contentType("application/json").content(content);
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(answer));
    }

    @Test
    void getProductById() throws Exception {
        Product product = getProductCreated();
        ProductOperation productOperation = ProductOperationSuccess.of(product);
        String answer = this.gson.toJson(productOperation);
        when(services.findById(any(ProductId.class)))
                .thenReturn(Optional.of(productOperation));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/products/2");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(answer));
    }

    @Test
    void getProductDoesntExist() throws Exception {
        ProductException exception = ProductDoesNotExist.of(ProductId.of(2L));
        ProductOperation productOperation = ProductOperationFailure.of(exception);
        String answer = this.gson.toJson(productOperation);
        when(services.findById(any(ProductId.class)))
                .thenReturn(Optional.of(productOperation));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/products/2");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(answer));
    }



    @Test
    void getAllProducts() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(Product.from(
                    ProductId.of(2L),
                    Name.of("Productname123"),
                    Description.of("ProductDescription123"),
                    BasePrice.of(new BigDecimal(123)),
                    TaxRate.of(new BigDecimal(0.2)),
                    ProductStatus.BORRADOR,
                    InventoryQuantity.of(12)));
        productList.add(Product.from(
                ProductId.of(3L),
                Name.of("Productname456"),
                Description.of("ProductDescription456"),
                BasePrice.of(new BigDecimal(456)),
                TaxRate.of(new BigDecimal(0.7)),
                ProductStatus.PUBLICADO,
                InventoryQuantity.of(78)));
        String GsonList = this.gson.toJson(productList);
        when(services.findAll()).thenReturn(productList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/products");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(content().json(GsonList));
    }

    @Test
    void deleteProduct() throws Exception {
        Product product = getProductCreated();
        ProductOperation productOperation = ProductOperationSuccess.of(product);
        String answer = this.gson.toJson(productOperation);
        when(services.deleteOne(any(ProductId.class)))
                .thenReturn(productOperation);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/products/2");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(answer));
    }

    @Test
    void deleteProductDoesntExist() throws Exception {
        ProductException exception = ProductDoesNotExist.of(ProductId.of(2L));
        ProductOperation productOperation = ProductOperationFailure.of(exception);
        String answer = this.gson.toJson(productOperation);
        when(services.deleteOne(any(ProductId.class)))
                .thenReturn(productOperation);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/products/2");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(answer));
    }

    @Test
    void updateProduct() throws Exception {
        Product product = getProductCreated();
        ProductOperationRequest productRequest = getProductSended();
        ProductOperation productOperation = ProductOperationSuccess.of(product);
        String content = this.gson.toJson(productRequest);
        String answer = this.gson.toJson(productOperation);
        when(services.updateOne(any(ProductId.class), any(ProductOperationRequest.class)))
                .thenReturn(productOperation);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/products/2").contentType("application/json").content(content);
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(answer));
    }

    @Test
    void updateProductDoesntExist() throws Exception {
        ProductException exception = ProductDoesNotExist.of(ProductId.of(2L));
        ProductOperation productOperation = ProductOperationFailure.of(exception);
        ProductOperationRequest productRequest = getProductSended();
        String content = this.gson.toJson(productRequest);
        String answer = this.gson.toJson(productOperation);
        when(services.updateOne(any(ProductId.class), any(ProductOperationRequest.class)))
                .thenReturn(productOperation);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/products/2").contentType("application/json").content(content);
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(answer));
    }

    Product getProductCreated(){
        return Product.from(
                ProductId.of(2L),
                Name.of("Productname123"),
                Description.of("ProductDescription"),
                BasePrice.of(new BigDecimal(123)),
                TaxRate.of(new BigDecimal(0.2)),
                ProductStatus.BORRADOR,
                InventoryQuantity.of(12)
        );
    }

    ProductOperationRequest getProductSended(){
        return ProductOperationRequest.from(
                Name.of("Productname123"),
                Description.of("ProductDescription"),
                BasePrice.of(new BigDecimal(123)),
                TaxRate.of(new BigDecimal(0.2)),
                ProductStatus.BORRADOR,
                InventoryQuantity.of(12)
        );
    }
}

