package inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.LessonStore;
import parameter.people.Jigou;
import parameter.people.Teacher;
import util.Body;

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
                LessonStore.takeOut().getLessonId()
        );


    }


    @Override
    public String route() {
        return "mizhu/web/classroom/setTeacher";
    }
}
