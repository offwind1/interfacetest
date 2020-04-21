package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class GetLessonInfoById extends BasicsInterface {

    @Step
    public JSONObject getLessonInfoById(String token, String lessonId) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = getLessonInfoById(Teacher.getInstance().getToken(),
                LessonFactory.takeOut().getLessonId());
        assert jsonObject.getStr("code").equals("200");
        assert jsonObject.getJSONObject("data").getJSONArray("classroomList").size() == 1;
    }

    @Test(description = "token为空")
    public void test1() {
        JSONObject jsonObject = getLessonInfoById("",
                LessonFactory.takeOut().getLessonId());
        assert jsonObject.getStr("code").equals("300");
        assert jsonObject.getStr("msg").equals("token无效或参数有误!");
    }

    @Test(description = "lessonId为空")
    public void test2() {
        JSONObject jsonObject = getLessonInfoById(Teacher.getInstance().getToken(),
                "");
        assert jsonObject.getStr("code").equals("300");
        assert jsonObject.getStr("msg").equals("token无效或参数有误!");
    }

    @Override
    public String route() {
        return "mizhu/web/lesson/getLessonInfoById";
    }
}
