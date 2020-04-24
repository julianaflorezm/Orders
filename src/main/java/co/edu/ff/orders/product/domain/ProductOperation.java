package co.edu.ff.orders.product.domain;

public interface ProductOperation {
    Product value();
    String failure();
    Boolean isValid();
}
