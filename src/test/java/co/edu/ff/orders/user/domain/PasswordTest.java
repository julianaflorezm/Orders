package co.edu.ff.orders.user.domain;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.junit.jupiter.api.function.Executable;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    /*
    @BeforeAll
    static void initAll(){
        //initializes all tests before running
    }

    @BeforeEach
    void init(){
        //initializes each test before running (Example: an Array)
    }
    */

    @TestFactory
    @DisplayName("Is should create valid passwords")
    Stream<DynamicTest> isShouldPass(){
        return Stream.of(
                "password",
                "password-1234"
        ).map(password -> {
                    String testName = String.format("It should be valid for pasword: %s", password);
                    Executable executable = () -> {
                        ThrowingSupplier<Password> suplier = () -> Password.of(password);
                        assertAll(
                                () -> assertDoesNotThrow(suplier),
                                () -> assertNotNull(suplier.get())
                        );
                    };
                    return DynamicTest.dynamicTest(testName, executable);
                });

    }

    @TestFactory
    @DisplayName("Is should not create valid password")
    Stream<DynamicTest> isShouldNotPass(){
        return Stream.of(
                "",
                "pass123"
        ).map(password -> {
            String testName = String.format("It should not be valid for password: %s", password);
            Executable executable = () -> {
                assertThrows(IllegalArgumentException.class, () -> Password.of(password));
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @Test
    @DisplayName("Is should not create valid null password")
    void isShouldNotPassNull(){
        assertThrows(NullPointerException.class, () -> Password.of(null));
    }

    @TestFactory
    //@Disabled("Razon")
    @DisplayName("valueOf should return the same value entered for password")
    Stream<DynamicTest> valueOfSameValue(){
        return Stream.of(
                "password",
                "password-1234"
        ).map(password -> {
            String testName = String.format("it should return the same value for pasword: %s", password);
            Executable executable = () -> {
                Password instance = Password.of(password);
                assertEquals(instance.valueOf(), password);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    /*
    @AfterEach
    void tearDown(){
        //things to do after each test
    }

    @AfterAll
    static void tearDownAll(){
        //things to do after all tests
    }
     */
}