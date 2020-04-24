package co.edu.ff.orders.serialization;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.function.Function;

public class IntegerValueAdapter<T extends IntegerSerializable> implements GsonAdapter<T> {
    private final Function<Integer, T> factory;

    public IntegerValueAdapter(Function<Integer, T> factory) {
        this.factory = factory;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Integer value = jsonElement.getAsInt();
        return factory.apply(value);
    }

    @Override
    public JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext) {
        Integer value = t.valueOf();
        return new JsonPrimitive(value);
    }
}
