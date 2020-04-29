package co.edu.ff.orders.common;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class NonEmptyString {
    String value;

    public NonEmptyString(String value) {
        Preconditions.checkNotNull(value, "Null value is not allowed");
        Preconditions.checkArgument(StringUtils.isNoneBlank(value),"Blank value is not allowed");
        this.value = value;
    }
}
