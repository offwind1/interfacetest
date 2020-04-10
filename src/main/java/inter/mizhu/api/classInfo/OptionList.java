package inter.mizhu.api.classInfo;

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
public class OptionList extends BasicsInterface {

    @Step
    public JSONObject optionList(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .build());
    }

    @Test
    public void test() {
        JSONObject object = optionList(Jigou.getInstance().getToken(), LessonStore.takeOut().getClassRoom(0).getClassroomId());
        SampleAssert.assertResult0(object);
    }

    @Override
    public String route() {
        return "mizhu/api/classInfo/optionList";
    }
}
