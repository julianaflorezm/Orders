package co.edu.ff.orders.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    @TestFactory
    @DisplayName("Is should create a valid product ID")
    Stream<DynamicTest> isShouldPass() {
        return Stream.of(
                2L,
                100L
        ).map(productId -> {
            String testName = String.format("It should be valid for product id: %s", productId);
            Executable executable = () -> {
                ThrowingSupplier<ProductId> supplier = () -> ProductId.of(productId);
                assertAll(
                        () -> assertDoesNotThrow(supplier),
                        () -> assertNotNull(supplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("Is should not create a valid product ID")
    Stream<DynamicTest> isShouldNotPass(){
        return Stream.of(
                1L,
                -1L,
                0L
        ).map(productId -> {
            String testName = String.format("It should not be valid for product id: %s", productId);
            Executable executable = () -> {
                Executable object =  () -> ProductId.of(productId);

                assertThrows(IllegalArgumentException.class, object);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @Test
    @DisplayName("It should not create a null product id")
    void IsShouldNotPassNull(){
        assertThrows(NullPointerException.class, () -> ProductId.of(null));
    }

    @TestFactory
    @DisplayName("valueOf should return the same value entered for product ID")
    Stream<DynamicTest> valueOfSameValue(){
        return Stream.of(
                2L,
                100L
        ).map(productId -> {
            String testName = String.format("It should return the same value for product id: %s", productId);
            Executable executable = () -> {
                ProductId instance = ProductId.of(productId);

                String message = String.format("Was expected %s for an product id instance created with %s", instance.valueOf(), productId);
                assertEquals(instance.valueOf(), productId, message);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

}