package co.edu.ff.orders.serialization;

import co.edu.ff.orders.serialization.StringSerializable;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.function.Function;

public class StringValueAdapter<T extends StringSerializable> implements GsonAdapter<T> {
    private final Function<String, T> factory;

    public StringValueAdapter(Function<String, T> factory) {
        this.factory = factory;
    }


    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String value = json.getAsString();
        return factory.apply(value);
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        String value = src.valueOf();
        return new JsonPrimitive(value);
    }
}
