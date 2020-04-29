package co.edu.ff.orders.product.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.IntegerSerializable;
import co.edu.ff.orders.serialization.LongSerializable;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProductId implements LongSerializable {
    Long value;

    private ProductId(Long value) {
        Preconditions.checkNotNull(value, "Product ID must not be null");
        Preconditions.checkArgument(value > 1, "Product ID must not be less than or equal to one");
        this.value = value;
    }

    @Override
    public Long valueOf() {
        return value;
    }
}
