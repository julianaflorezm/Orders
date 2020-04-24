package co.edu.ff.orders.product.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.BigDecimalSerializable;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class BasePrice implements BigDecimalSerializable {
    BigDecimal value;

    private BasePrice(BigDecimal value) {
        Preconditions.checkNotNull(value, "The base price must not be null");
        Preconditions.checkArgument(value.doubleValue() >= 0, "The base price must not be less than zero");
        this.value = value;
    }

    @Override
    public BigDecimal valueOf() {
        return value;
    }
}
