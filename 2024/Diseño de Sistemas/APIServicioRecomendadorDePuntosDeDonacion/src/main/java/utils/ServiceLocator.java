package utils;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static Map<Class<?>, Object> services = new HashMap<>();

    public static <T> void registerService(Class<T> serviceClass, T serviceInstance) {
        services.put(serviceClass, serviceInstance);
    }
    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }
}