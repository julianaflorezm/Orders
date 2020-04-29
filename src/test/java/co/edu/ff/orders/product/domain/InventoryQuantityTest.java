package co.edu.ff.orders.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InventoryQuantityTest {

    @TestFactory
    @DisplayName("Is should create a valid inventory quantity")
    Stream<DynamicTest> isShouldPass() {
        return Stream.of(
                0,
                1,
                3000
        ).map(inventoryQuantity -> {
            String testName = String.format("It should be valid for inventory quantity: %s", inventoryQuantity);
            Executable executable = () -> {
                ThrowingSupplier<InventoryQuantity> supplier = () -> InventoryQuantity.of(inventoryQuantity);
                assertAll(
                        () -> assertDoesNotThrow(supplier),
                        () -> assertNotNull(supplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("Is should not create a valid inventory quantity")
    Stream<DynamicTest> isShouldNotPass(){
        return Stream.of(
                -1
        ).map(inventoryQuantity -> {
            String testName = String.format("It should not be valid for inventory quantity: %s", inventoryQuantity);
            Executable executable = () -> {
                Executable object =  () -> InventoryQuantity.of(inventoryQuantity);

                assertThrows(IllegalArgumentException.class, object);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @Test
    @DisplayName("It should not create a null inventory quantity")
    void IsShouldNotPassNull(){
        assertThrows(NullPointerException.class, () -> InventoryQuantity.of(null));
    }

    @TestFactory
    @DisplayName("valueOf should return the same value entered for inventory quantity")
    Stream<DynamicTest> valueOfSameValue(){
        return Stream.of(
                0,
                1,
                3000
        ).map(inventoryQuantity -> {
            String testName = String.format("It should return the same value for inventory quantity: %s", inventoryQuantity);
            Executable executable = () -> {
                InventoryQuantity instance = InventoryQuantity.of(inventoryQuantity);

                String message = String.format("Was expected %s for an inventory quantity instance created with %s", instance.valueOf(), inventoryQuantity);
                assertEquals(instance.valueOf(), inventoryQuantity, message);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

}