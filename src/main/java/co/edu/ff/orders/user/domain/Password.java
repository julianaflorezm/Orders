package co.edu.ff.orders.user.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.StringSerializable;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class Password implements StringSerializable {
    String value;

    public Password(String value){
        Preconditions.checkNotNull(value, "Password must not be null");
        Preconditions.checkArgument(StringUtils.isNoneBlank(value), "Password must not be blank");
        Preconditions.checkArgument(value.length() >= 8, "Password must not have less than eight characters");
        this.value = value;
    }

    @Override
    public String valueOf() {
        return value;
    }
}
