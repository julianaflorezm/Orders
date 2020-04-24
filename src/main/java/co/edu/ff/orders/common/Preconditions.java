package co.edu.ff.orders.common;

import java.util.Objects;

public class Preconditions {

    public static void checkNotNull(Object reference, String mensaje) {
        if(Objects.isNull(reference)){
            throw new NullPointerException(mensaje);
        }
    }

    public static void checkArgument(boolean argument, String mensaje) {
        if(!argument) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
