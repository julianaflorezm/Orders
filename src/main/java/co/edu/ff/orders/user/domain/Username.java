package co.edu.ff.orders.user.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.serialization.StringSerializable;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class Username implements StringSerializable {
    String value;

    private Username(String value){
        Preconditions.checkNotNull(value, "Username must not be null");
        Preconditions.checkArgument(StringUtils.isNoneBlank(value), "Username must not be blank");
        Preconditions.checkArgument(value.length() >= 6,
                                    "Username must not have less than six characters");
        this.value = value;
    }

    @Override
    public String valueOf() {
        return value;
    }
}
