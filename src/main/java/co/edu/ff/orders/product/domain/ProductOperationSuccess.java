package co.edu.ff.orders.product.domain;

import co.edu.ff.orders.product.exceptions.ProductException;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProductOperationSuccess implements ProductOperation {

    Product value;

    @Override
    public Product value() {
        return value;
    }

    @Override
    public String failure() {
        return null;
    }

    @Override
    public Boolean isValid() {
        return true;
    }
}
