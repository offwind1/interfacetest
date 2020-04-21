package cn.vr168.interfacetest.config;

import cn.hutool.core.io.FileUtil;

public class ResourcesUtil {

    public static byte[] getCourse(String courseName) {
        return FileUtil.readBytes("course/" + courseName);
    }

}
