package inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.LessonStore;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class ClassroomOption extends BasicsInterface {

    @Step
    public JSONObject classroomOption(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build());
    }

    @Test
    public void test() {
        JSONObject jsonObject = classroomOption(Jigou.getInstance().getToken(),
                LessonStore.getFirstLesson(Jigou.getInstance()).getClassRoom(0).getClassroomId()
        );
        SampleAssert.assertCode200(jsonObject);
    }
    
    @Override
    public String route() {
        return "mizhu/web/classroom/classroomOption";
    }
}
