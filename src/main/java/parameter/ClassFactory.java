package parameter;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import inter.mizhu.web.grade.AddClass;
import inter.mizhu.web.grade.ClassList;
import parameter.people.Jigou;
import util.SampleAssert;

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
}
