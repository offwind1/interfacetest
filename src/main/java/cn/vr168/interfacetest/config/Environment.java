package cn.vr168.interfacetest.config;

public class Environment {
    private static String serverType = ConfigUtil.getServerType();

    public static String getValue(String key) {
        return ConfigUtil.getValue(serverType + "." + key);
    }

}
