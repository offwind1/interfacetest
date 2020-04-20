package cn.vr168.interfacetest.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.NonNull;

public class SampleAssert {

    public static void assertCode200(@NonNull JSONObject object) {
        assertStr(object, "code", "200");
    }

    public static void assertCode(@NonNull JSONObject object, String code) {
        assertStr(object, "code", code);
    }

    public static void assertResult0(@NonNull JSONObject object) {
        assertStr(object, "result", "0");
    }

    public static void assertMsg(@NonNull JSONObject object, String value) {
        assertStr(object, "msg", value);
    }

    public static void assertStr(@NonNull JSONObject object, String key, String value) {
        assert object.getStr(key).equals(value) : "key:" + key + "期望值:" + value + " 实际值:" + object.getStr(key);
    }

    public static void assertAnyMatch(@NonNull JSONArray array, String key, String value) {
        assert array.stream().anyMatch(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr(key).equals(value);
        }) : "列表值未找到 key为:" + key + " 值为:" + value + "的对象";
    }

}
