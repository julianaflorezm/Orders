package co.edu.ff.orders.serialization;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface GsonAdapter<T> extends JsonDeserializer<T>, JsonSerializer<T> {
}
