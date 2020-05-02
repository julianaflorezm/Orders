package co.edu.ff.orders.user.services;

import co.edu.ff.orders.user.domain.*;
import co.edu.ff.orders.user.repositories.SqlUserRepository;
import co.edu.ff.orders.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServicesTest {

    @Autowired
    private UserServices services;

    @MockBean
    private UserRepository repository;

    @Test
    void createUser() {
        Username username = Username.of("Username1234");
        Password password = Password.of("Password1234");
        UserCreated userCreated = UserCreated.of(
                username,
                password,
                1L
        );
        Username usernameMock = ArgumentMatchers.any(Username.class);
        when(repository.findByUsername(usernameMock))
                .thenReturn(Optional.of(userCreated));

        UserOperation user = this.services.createUser(username, password);

        assertFalse(user.isValid(), "El ususario no es valido");
    }

    @Test
    void findById() {
    }
}