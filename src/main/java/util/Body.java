package util;


import java.util.HashMap;
import java.util.Map;

public class Body {
    private Map<String, Object> map = new HashMap<>();

    public Body add(String key, Object obj) {
        map.put(key, obj);
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }

    public static Body create() {
        return new Body();
    }
}
