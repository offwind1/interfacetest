package cn.vr168.interfacetest.inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.LessonStore;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.Body;
import cn.vr168.interfacetest.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class GetClassroomById extends BasicsInterface {

    @Step
    public JSONObject getClassroomById(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = getClassroomById(Jigou.getInstance().getToken(),
                LessonStore.getFirstLesson(Jigou.getInstance()).getClassRoom(0).getClassroomId()
        );
        SampleAssert.assertCode200(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/web/classroom/getClassroomById";
    }
}
