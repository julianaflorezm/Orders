package co.edu.ff.orders.serialization;

import co.edu.ff.orders.common.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Value;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LongValueAdapterTest {

    static Gson gson;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LongTest.class, new LongValueAdapter<>(LongTest::of))
                .create();
    }

    @TestFactory
    @DisplayName("It should deserialize from Gson to long object")
    Stream<DynamicTest> deserialize() {
        return Stream.of(
                "4"
        ).map(longGson -> {
            String testName = String.format("it should deserialize %s to Java object", longGson);
            Executable executable = () -> {
                LongTest actual = gson.fromJson(longGson, LongTest.class);

                Long value = Long.parseLong(longGson);
                LongTest expected = LongTest.of(value);

                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("It should serialize Product ID to Gson")
    Stream<DynamicTest> serialize() {
        return Stream.of(
                LongTest.of(4L)
        ).map(objLong -> {
            String testName = String.format("it should serialize %s to Gson", objLong.valueOf());
            Executable executable = () -> {
                String actual = gson.toJson(objLong);
                String expected = objLong.valueOf().toString();
                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}

@Value(staticConstructor = "of")
class LongTest implements LongSerializable {
    Long value;

    public LongTest(Long value) {
        Preconditions.checkNotNull(value, "It should not be null");
        this.value = value;
    }

    @Override
    public Long valueOf() {
        return value;
    }
}