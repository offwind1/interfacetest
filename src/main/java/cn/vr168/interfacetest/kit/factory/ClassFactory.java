package cn.vr168.interfacetest.kit.factory;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.web.grade.ClassList;
import cn.vr168.interfacetest.parameter.Clazz;
import cn.vr168.interfacetest.parameter.people.Jigou;

public class ClassFactory {

    public static Clazz creatClass() {
        return creatClass("班级" + RandomUtil.randomString(6));
    }

    public static Clazz creatClass(String className) {
        return new Clazz(className, "1");
    }

    public static Clazz findClass(String className) {
        JSONObject classList = ClassList.of().classList(Jigou.getInstance());
        for (JSONObject object : classList.getJSONObject("data").getJSONArray("list").jsonIter()) {
            if (object.getStr("className").equals(className)) {
                return new Clazz(object);
            }
        }
        return creatClass(className);
    }

    public static Clazz findClass() {
        return findClass("答题卡专用");
    }
}
