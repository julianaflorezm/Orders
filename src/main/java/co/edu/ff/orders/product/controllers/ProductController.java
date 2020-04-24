package co.edu.ff.orders.product.controllers;

import co.edu.ff.orders.product.domain.Product;
import co.edu.ff.orders.product.domain.ProductId;
import co.edu.ff.orders.product.domain.ProductOperation;
import co.edu.ff.orders.product.domain.ProductOperationRequest;
import co.edu.ff.orders.product.services.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServices service;

    @PostMapping
    public ResponseEntity<ProductOperation> createOne(@RequestBody ProductOperationRequest productBody){
            ProductOperation productOperation = service.createProduct(productBody);
            if (productOperation.isValid()){
                return ResponseEntity.ok(productOperation);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(productOperation);
            }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOperation> getProductById(@PathVariable Long id){
        ProductId idObject = ProductId.of(id);
        Optional<ProductOperation> productExistence = service.findById(idObject);
        ProductOperation productOperation = productExistence.get();
        if(productOperation.isValid()){
            return ResponseEntity.ok(productOperation);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(productOperation);
        }
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductOperation> deleteProduct(@PathVariable Long id){
        ProductId idObject = ProductId.of(id);
        ProductOperation productOperation = service.deleteOne(idObject);
        if(productOperation.isValid()){
            return ResponseEntity.ok(productOperation);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(productOperation);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOperation> updateProduct(@PathVariable Long id, @RequestBody ProductOperationRequest product){
        ProductId idObject = ProductId.of(id);
        ProductOperation productOperation = service.updateOne(idObject, product);
        if(productOperation.isValid()){
            return ResponseEntity.ok(productOperation);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(productOperation);
        }
    }
}
