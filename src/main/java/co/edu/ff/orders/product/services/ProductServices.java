package co.edu.ff.orders.product.services;

import co.edu.ff.orders.product.domain.*;
import co.edu.ff.orders.product.exceptions.ProductAlreadyExists;
import co.edu.ff.orders.product.exceptions.ProductDoesNotExist;
import co.edu.ff.orders.product.exceptions.ProductException;
import co.edu.ff.orders.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {

    private final ProductRepository repository;

    @Autowired
    public ProductServices(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductOperation createProduct(ProductOperationRequest product){
        Optional<ProductOperation> productExistence = repository.findByName(product.getName());
        if(productExistence.isPresent()){
            ProductAlreadyExists exception = ProductAlreadyExists.of(product.getName());
            return ProductOperationFailure.of(exception);
        }else{
            return repository.insertOne(product);
        }
    }

    public Optional<ProductOperation> findById(ProductId id){
        Optional<ProductOperation> productExistence = repository.findById(id);
        if(!productExistence.isPresent()){
            ProductDoesNotExist exception = ProductDoesNotExist.of(id);
            return Optional.of(ProductOperationFailure.of(exception));
        }else {
            return productExistence;
        }
    }

    public List<Product> findAll(){
        return repository.findAll();
    }

    public ProductOperation deleteOne(ProductId id){
        return repository.deleteOne(id);
    }

    public ProductOperation updateOne(ProductId id, ProductOperationRequest product){
        Optional<ProductOperation> productExistence = repository.findById(id);
        if(!productExistence.isPresent()){
            ProductDoesNotExist exception = ProductDoesNotExist.of(id);
            return ProductOperationFailure.of(exception);
        }else{
            return repository.updateOne(id, product);
        }
    }


}
