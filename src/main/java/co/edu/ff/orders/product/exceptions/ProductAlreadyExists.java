package co.edu.ff.orders.product.exceptions;

import co.edu.ff.orders.product.domain.Name;
import lombok.EqualsAndHashCode;
import lombok.Value;
import sun.plugin2.jvm.RemoteJVMLauncher;

@EqualsAndHashCode(callSuper = true)
@Value(staticConstructor = "of")
public class ProductAlreadyExists extends ProductException{
    Name name;
    public ProductAlreadyExists(Name name) {
        super(String.format("Product %s already exists", name.getValue()));
        this.name = name;
    }
}
