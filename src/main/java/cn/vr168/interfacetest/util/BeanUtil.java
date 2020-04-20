package cn.vr168.interfacetest.util;

import lombok.Builder;
import lombok.Data;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    @Data
    @Builder
    public static class Bean {
        private String name;
        private String token;
        private String asd = "123";
    }


    @Test
    public void test() throws IllegalAccessException {
        System.out.println(beanToMap(Bean.builder().name("123").build()));
    }


    public Map<String, Object> beanToMap(Bean bean) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        for (Field field : bean.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName() + field.get(bean));
            if (field.get(bean) != null) {
                map.put(field.getName(), field.get(bean));
            } else {
                map.put(field.getName(), "");
            }
        }
        return map;
    }


}
