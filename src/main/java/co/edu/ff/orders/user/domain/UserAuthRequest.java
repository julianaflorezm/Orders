package co.edu.ff.orders.user.domain;

import co.edu.ff.orders.common.Preconditions;
import lombok.Value;

@Value(staticConstructor = "from")
public class UserAuthRequest {
    Username username;
    String password;

    public UserAuthRequest(Username username, String password) {
        Preconditions.checkNotNull(username, "Username reference must not be null");
        Preconditions.checkNotNull(password, "Password reference must not be null");
        this.username = username;
        this.password = password;
    }
}
