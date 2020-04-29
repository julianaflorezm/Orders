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

class TaxRateTest {

    @TestFactory
    @DisplayName("Is should create a valid tax rate")
    Stream<DynamicTest> isShouldPass() {
        return Stream.of(
                new BigDecimal(0),
                new BigDecimal(0.5),
                new BigDecimal(1)
        ).map(taxRate -> {
            String testName = String.format("It should be valid for tax rate: %s", taxRate);
            Executable executable = () -> {
                ThrowingSupplier<TaxRate> supplier = () -> TaxRate.of(taxRate);
                assertAll(
                        () -> assertDoesNotThrow(supplier),
                        () -> assertNotNull(supplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("Is should not create a valid tax rate")
    Stream<DynamicTest> isShouldNotPass(){
        return Stream.of(
                new BigDecimal(-0.1),
                new BigDecimal(1.1),
                new BigDecimal(2)
        ).map(taxRate -> {
            String testName = String.format("It should not be valid for tax rate: %s", taxRate);
            Executable executable = () -> {
                Executable object =  () -> TaxRate.of(taxRate);

                assertThrows(IllegalArgumentException.class, object);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @Test
    @DisplayName("It should not create a null tax rate")
    void IsShouldNotPassNull(){
        assertThrows(NullPointerException.class, () -> TaxRate.of(null));
    }

    @TestFactory
    @DisplayName("valueOf should return the same value entered for tax rate")
    Stream<DynamicTest> valueOfSameValue(){
        return Stream.of(
                new BigDecimal(0),
                new BigDecimal(0.5),
                new BigDecimal(1)
        ).map(taxRate -> {
            String testName = String.format("It should return the same value for tax rate: %s", taxRate);
            Executable executable = () -> {
                TaxRate instance = TaxRate.of(taxRate);
                String message = String.format("Was expected %s for a tax rate instance created with %s", instance.valueOf(), taxRate);
                assertEquals(instance.valueOf(), taxRate, message);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}