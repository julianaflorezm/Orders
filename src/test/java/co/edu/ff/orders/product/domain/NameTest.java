package co.edu.ff.orders.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @TestFactory
    @DisplayName("Is should create a valid name")
    Stream<DynamicTest> isShouldPass(){
        return Stream.of(
                "name-1234",
                "v",
                "Product name, product name, product name, product name" +
                        "Product name, product name, product name, pro"
        ).map(name -> {
            String testName = String.format("It should be valid for name: %s", name);
            Executable executable = () -> {
                ThrowingSupplier<Name> supplier = () -> Name.of(name);
                assertAll(
                        () -> assertDoesNotThrow(supplier),
                        () -> assertNotNull(supplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("Is should not create valid names")
    Stream<DynamicTest> isShouldNotPass(){
        return Stream.of(
                "",
                "Invalid name for one product, invalid name for one product," +
                        "Invalid name for one product, invalid name for one product," +
                        "Invalid name for one product, invalid name for one product"
        ).map(name -> {
            String testName = String.format("It should not be valid for description: %s", name);
            Executable executable = () -> {
                Executable object = () -> Name.of(name);
                assertThrows(IllegalArgumentException.class, object);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @Test
    @DisplayName("It should not create a null product name")
    void IsShouldNotPassNull(){
        assertThrows(NullPointerException.class, () -> Name.of(null));
    }


    @TestFactory
    @DisplayName("valueOf should return the same value entered for name")
    Stream<DynamicTest> valueOfSameValue(){
        return Stream.of(
                "name-1234",
                "v",
                "Product name, product name, product name, product name" +
                        "Product name, product name, product name, pro"
        ).map(name -> {
            String testName = String.format("It should return the same value for product name: %s", name);
            Executable executable = () -> {
                Name instance = Name.of(name);

                String message = String.format("Was expected %s for a name instance created with %s", instance.valueOf(), name);
                assertEquals(instance.valueOf(), name, message);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}