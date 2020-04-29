package co.edu.ff.orders.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {

    @TestFactory
    @DisplayName("Is should create a valid description")
    Stream<DynamicTest> isShouldPass(){
        return Stream.of(
                "V",
                "Valid description for one product, Valid description for one product" +
                        "Valid description for one product, Valid description for one product" +
                        "Valid description for one product, Valid description for one product" +
                        "Valid description for one product, Valid description for one product" +
                        "Valid de"
        ).map(description -> {
            String testName = String.format("It should be valid for description: %s", description);
            Executable executable = () -> {
                ThrowingSupplier<Description> supplier = () -> Description.of(description);
                assertAll(
                        () -> assertDoesNotThrow(supplier),
                        () -> assertNotNull(supplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("Is should not create a valid description")
    Stream<DynamicTest> isShouldNotPass(){
        return Stream.of(
                "",
                "Invalid description for one product, Invalid description for one product, " +
                        "Invalid description for one product, Invalid description for one product, " +
                        "Invalid description for one product, Invalid description for one product, " +
                        "Invalid description for one product, Invalid description for one product."
        ).map(description -> {
            String testName = String.format("It should not be valid for description: %s", description);
            Executable executable = () -> {
                Executable object = () -> Description.of(description);
                assertThrows(IllegalArgumentException.class, object);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @Test
    @DisplayName("It should not create a null description")
    void IsShouldNotPassNull(){
        assertThrows(NullPointerException.class, () -> Description.of(null));
    }

    @TestFactory
    @DisplayName("valueOf should return the same value entered for description")
    Stream<DynamicTest> valueOfSameValue(){
        return Stream.of(
                "Valid description for one product"
        ).map(description -> {
            String testName = String.format("It should return the same value for description: %s", description);
            Executable executable = () -> {
                Description instance = Description.of(description);

                String message = String.format("Was expected %s for a description instance created with %s", instance.valueOf(), description);
                assertEquals(instance.valueOf(), description, message);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}