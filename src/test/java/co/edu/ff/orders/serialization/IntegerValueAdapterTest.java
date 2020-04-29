package co.edu.ff.orders.serialization;

import co.edu.ff.orders.common.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Value;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IntegerValueAdapterTest {

    static Gson gson;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(IntegerTest.class, new IntegerValueAdapter<>(IntegerTest::of))
                .create();
    }

    @TestFactory
    @DisplayName("it should deserialize from Gson to integer object")
    Stream<DynamicTest> deserialize() {
        return Stream.of(
                "30"
        ).map(integerGson -> {
            String testName = String.format("it should deserialize %s to Java object", integerGson);
            Executable executable = () -> {
                IntegerTest actual = gson.fromJson(integerGson, IntegerTest.class);

                Integer value = Integer.parseInt(integerGson);
                IntegerTest expected = IntegerTest.of(value);

                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });

    }

    @TestFactory
    @DisplayName("It should serialize an integer object to Gson")
    Stream<DynamicTest> serialize() {
        return Stream.of(
                IntegerTest.of(40)
        ).map(objInteger -> {
            String testName = String.format("it should serialize %s to Gson", objInteger.valueOf());
            Executable executable = () -> {
                String actual = gson.toJson(objInteger);
                String expected = objInteger.valueOf().toString();
                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}

@Value(staticConstructor = "of")
class IntegerTest implements IntegerSerializable {
    Integer value;

    public IntegerTest(Integer value) {
        Preconditions.checkNotNull(value, "It should not be null");
        this.value = value;
    }

    @Override
    public Integer valueOf() {
        return value;
    }
}