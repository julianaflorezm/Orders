package co.edu.ff.orders.product.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.IntegerSerializable;
import lombok.Value;

@Value(staticConstructor = "of")
public class InventoryQuantity implements IntegerSerializable {
    Integer value;

    private InventoryQuantity(Integer value) {
        Preconditions.checkNotNull(value, "The inventory quantity must not be null");
        Preconditions.checkArgument(value >= 0, "The inventory quantity must not be less than zero");
        this.value = value;
    }

    @Override
    public Integer valueOf() {
        return value;
    }
}
