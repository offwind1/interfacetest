package inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import inter.mizhu.web.usr.LessonStudent;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.Lesson;
import parameter.LessonStore;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class GroupByClass extends BasicsInterface {

    @Step
    public JSONObject groupByClass(String token, String lessonId) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .build());
    }


    @Test
    public void test() {
        JSONObject jsonObject = groupByClass(Jigou.getInstance().getToken(), LessonStore.takeOut().getLessonId());
        SampleAssert.assertCode200(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/web/lesson/groupByClass";
    }
}
