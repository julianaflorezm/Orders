package co.edu.ff.orders.serialization;

import co.edu.ff.orders.common.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Value;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringValueAdapterTest {

    static Gson gson;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(StringTest.class, new StringValueAdapter<>(StringTest::of))
                .create();
    }

    @TestFactory
    @DisplayName("It should deserialize from Gson to String object")
    Stream<DynamicTest> deserialize() {

        return Stream.of(
                String.format("\"%s\"", "StringTest")
        ).map(gsonString -> {
            String testName = String.format("It should deserialize %s to Java Object", gsonString);
            Executable executable = () -> {
                StringTest actual = gson.fromJson(gsonString, StringTest.class);
                String value = gsonString.replaceAll("[\"]", "");
                StringTest expected = StringTest.of(value);
                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }

    @TestFactory
    @DisplayName("It should serialize user name, password, product name and product description to Gson")
    Stream<DynamicTest> serialize() {
        return Stream.of(
                StringTest.of("StringTest")
        ).map(object -> {
            String nameTest = String.format("It should serialize %s to Gson", object.valueOf());
            Executable executable = () -> {
                String actual = gson.toJson(object);
                String expected = String.format("\"%s\"", object.valueOf());
                assertEquals(actual, expected);
            };
            return DynamicTest.dynamicTest(nameTest, executable);
        });
    }
}

@Value(staticConstructor = "of")
class StringTest implements StringSerializable {
    String value;

    public StringTest(String value) {
        Preconditions.checkNotNull(value, "It should not be null");
        this.value = value;
    }

    @Override
    public String valueOf() {
        return value;
    }
}