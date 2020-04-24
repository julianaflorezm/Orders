package co.edu.ff.orders.product.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.StringSerializable;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class Name implements StringSerializable {

    String value;

    private Name(String value) {
        Preconditions.checkNotNull(value, "Name must not be null");
        Preconditions.checkArgument(StringUtils.isNoneBlank(value), "Name must not be blank");
        Preconditions.checkArgument(value.length() <= 100, "Name must not have more than 100 characters");
        this.value = value;
    }

    @Override
    public String valueOf() {
        return value;
    }
}
