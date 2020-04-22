package cn.vr168.interfacetest.config;

import cn.vr168.interfacetest.kit.util.JsonProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    public static Properties properties;

    static {
        InputStream stream = JsonProperties.class.getClassLoader().getResourceAsStream("config.properties");
        properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    public static String getWebHost() {
        return getValue("WebHost") + getServerType() + "/";
    }

    public static String getManageHost() {
        return getValue("ManageHost") + getServerType() + "/";
    }

    public static String getKacaHost() {
        return getValue(getServerType() + "." + "KacaHost");
    }

    public static String getServerType() {
        return getValue("ServerType");
    }

}

