package co.edu.ff.orders.product.exceptions;

import co.edu.ff.orders.product.domain.Product;
import co.edu.ff.orders.product.domain.ProductId;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.security.auth.callback.Callback;

@EqualsAndHashCode(callSuper = true)
@Value(staticConstructor = "of")
public class ProductDoesNotExist extends ProductException {
    ProductId productId;
    public ProductDoesNotExist(ProductId productId) {
       super(String.format("Product %s doesn't exist", productId.getValue()));
       this.productId = productId;
    }
}
