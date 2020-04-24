package co.edu.ff.orders.product.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.StringSerializable;
import co.edu.ff.orders.serialization.StringValueAdapter;
import com.sun.org.glassfish.external.statistics.StringStatistic;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class Description implements StringSerializable {
    String value;

    private Description(String value) {
        Preconditions.checkNotNull(value, "The description must not be null");
        Preconditions.checkArgument(StringUtils.isNoneBlank(value), "The description must not be blank");
        Preconditions.checkArgument(value.length() <= 280, "The description must not have more than 280 characters");
        this.value = value;
    }

    @Override
    public String valueOf() {
        return value;
    }
}
