package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.HasToken;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.kit.util.Body;

import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class Add extends BasicsInterface {

    public static Add getInstance() {
        return new Add();
    }

    @Step
    public JSONObject add(Map<String, Object> map) {
        return post(map);
    }

    public JSONObject add(HasToken hasToken, int classroomCount) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < classroomCount; i++) {
            JSONObject object = new JSONObject();
            object.put("interaction", 4);
            object.put("startTime", DateUtil.offsetDay(DateUtil.date(), i).toString());
            object.put("classroomId", 1);
            array.add(object);
        }

        return add(Body.create()
                .add("token", hasToken.getToken())
                .add("gradeIds", "1")
                .add("gradeNames", "一年级")
                .add("lessonTypeId", "1")
                .add("startTime", DateUtil.date().toString())
                .add("endTime", DateUtil.offsetDay(DateUtil.date(), classroomCount).toString())
                .add("tryLook", "0")
                .add("lessonTerm", "1")
                .add("lessonName", "接口新建课程" + DateUtil.today())
                .add("faceImg", "http://images.mizholdings.com/bh4nopY6Mea.png")
                .add("classroomCount", String.valueOf(classroomCount))
                .add("classTime", "60")
                .add("classroomPrice", "0")
                .add("discount", String.valueOf(10 * classroomCount))
                .add("free", "1")
                .add("classInfo", array.toString())
                .add("studentCount", "200")
                .add("custRelease", "1")
                .build());
    }


    @Test
    public void test() {
        add(Teacher.getInstance(), 3);
    }


    @Override
    public String route() {
        return "/mizhu/web/lesson/add";
    }
}
