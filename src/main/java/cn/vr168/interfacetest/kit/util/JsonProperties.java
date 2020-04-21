package cn.vr168.interfacetest.kit.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JsonProperties {
    public static Properties properties;

    static {
        InputStream stream = JsonProperties.class.getClassLoader().getResourceAsStream("jsonData.properties");
        properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getJson(String key) {
        String jsonPath = JsonProperties.class.getResource(properties.getProperty(key)).getPath();
        System.out.println(jsonPath);
        if (FileUtil.isFile(jsonPath)) {
            return JSONUtil.parseObj(FileUtil.readString(jsonPath, "UTF-8"));
        }

        throw new RuntimeException("json 路径不正确");
    }

    public static void main(String[] args) {
        System.out.println(getJson("json.grade"));
    }
}
