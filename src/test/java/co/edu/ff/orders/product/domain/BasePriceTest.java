package co.edu.ff.orders.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BasePriceTest {

    @TestFactory
    @DisplayName("Is should create a valid base price")
    Stream<DynamicTest> isShouldPass() {
        return Stream.of(
                new BigDecimal(0),
                new BigDecimal(1)
        ).map(basePrice -> {
            String testName = String.format("It should be valid for base price: %s", basePrice);
            Executable executable = () -> {
                ThrowingSupplier<BasePrice> supplier = () -> BasePrice.of(basePrice);
                assertAll(
                        () -> assertDoesNotThrow(supplier),
                        () -> assertNotNull(supplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("Is should not create a valid base price")
    Stream<DynamicTest> isShouldNotPass(){
        return Stream.of(
                new BigDecimal(-0.1)
        ).map(basePrice -> {
            String testName = String.format("It should not be valid for base price: %s", basePrice);
            Executable executable = () -> {
                Executable object =  () -> BasePrice.of(basePrice);

                assertThrows(IllegalArgumentException.class, object);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @Test
    @DisplayName("It should not create a null base price")
    void IsShouldNotPassNull(){
        assertThrows(NullPointerException.class, () -> BasePrice.of(null));
    }

    @TestFactory
    @DisplayName("valueOf should return the same value entered for base price")
    Stream<DynamicTest> valueOfSameValue(){
        return Stream.of(
                new BigDecimal(0),
                new BigDecimal(1)
        ).map(basePrice -> {
            String testName = String.format("It should return the same value for base price: %s", basePrice);
            Executable executable = () -> {
                BasePrice instance = BasePrice.of(basePrice);
                String message = String.format("Was expected %s for a base price instance created with %s", instance.valueOf(), basePrice);
                assertEquals(instance.valueOf(), basePrice, message);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}