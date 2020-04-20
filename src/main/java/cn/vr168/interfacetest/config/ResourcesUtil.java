package cn.vr168.interfacetest.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.Resource;
import cn.vr168.interfacetest.util.JsonProperties;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;

public class ResourcesUtil {

    public static byte[] getCourse(String courseName) {
        return FileUtil.readBytes("course/" + courseName);
    }

}
