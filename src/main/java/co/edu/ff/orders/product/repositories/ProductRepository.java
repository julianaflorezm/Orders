package co.edu.ff.orders.product.repositories;

import co.edu.ff.orders.product.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    ProductOperation insertOne(ProductOperationRequest product);
    Optional<ProductOperation> findByName(Name name);
    Optional<ProductOperation> findById(ProductId id);
    List<Product> findAll();
    ProductOperation updateOne(ProductId id, ProductOperationRequest product);
    ProductOperation deleteOne(ProductId id);
}
