package co.edu.ff.orders.product.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.BigDecimalSerializable;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class TaxRate implements BigDecimalSerializable {
    BigDecimal value;

    private TaxRate(BigDecimal value) {
        Preconditions.checkNotNull(value, "Tax rate must not be null");
        Preconditions.checkArgument(value.doubleValue() >= 0 && value.doubleValue() <= 1,
                                    "Tax rate must not be less than zero nor greater than one");
        this.value = value;
    }

    @Override
    public BigDecimal valueOf() {
        return value;
    }
}
