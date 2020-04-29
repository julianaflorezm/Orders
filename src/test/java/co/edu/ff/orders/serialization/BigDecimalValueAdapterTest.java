package co.edu.ff.orders.serialization;

import co.edu.ff.orders.common.Preconditions;
import com.google.gson.*;
import lombok.Value;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalValueAdapterTest {

    static Gson gson;


    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(BigDecimalTest.class, new BigDecimalValueAdapter<>(BigDecimalTest::of))
                .create();
    }

    @TestFactory
    @DisplayName("It should deserialize from Gson to Big decimal object")
    Stream<DynamicTest> deserialize() {
        return Stream.of(
                "23"
        ).map(bigDecimalGson -> {
            String testName = String.format("It should deserialize %s to Java object", bigDecimalGson);
            Executable executable = () -> {
                BigDecimalTest actual = gson.fromJson(bigDecimalGson, BigDecimalTest.class);

                BigDecimal value = new BigDecimal(bigDecimalGson);
                BigDecimalTest expected = BigDecimalTest.of(value);

                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("It should serialize Big decimal object to Gson")
    Stream<DynamicTest> serialize() {
        return Stream.of(
                BigDecimalTest.of(new BigDecimal(23))
        ).map(object -> {
            String testName = String.format("It should serialize %s to Gson", object.valueOf());
            Executable executable = () -> {
                String actual = gson.toJson(object);
                String expected = object.valueOf().toString();
                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(testName,executable);
        });
    }
}

@Value(staticConstructor = "of")
class BigDecimalTest implements BigDecimalSerializable {
    BigDecimal value;

    public BigDecimalTest(BigDecimal value) {
        Preconditions.checkNotNull(value, "It should not be null");
        this.value = value;
    }

    @Override
    public BigDecimal valueOf() {
        return value;
    }
}