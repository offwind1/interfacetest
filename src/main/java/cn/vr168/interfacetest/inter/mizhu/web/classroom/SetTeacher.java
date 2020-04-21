package cn.vr168.interfacetest.inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class SetTeacher extends BasicsInterface {

    @Step
    public JSONObject setTeacher(String token, String teacherIds, String classroomIds) {
        return post(Body.create()
                .add("token", token)
                .add("teacherIds", teacherIds)
                .add("classroomIds", classroomIds)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = setTeacher(Jigou.getInstance().getToken(),
                Teacher.getInstance().getUserId(),
                LessonFactory.takeOut().getLessonId()
        );


    }


    @Override
    public String route() {
        return "mizhu/web/classroom/setTeacher";
    }
}
