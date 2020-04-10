package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.Lesson;
import parameter.LessonStore;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

/**
 * 作业模块列表
 */
@RequiredArgsConstructor(staticName = "of")
public class GetWorkList extends BasicsInterface {

    @Step
    public JSONObject getWorkList(String token, String lessonId) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .build());
    }

    @Test
    public void test() {
        Lesson lesson = LessonStore.takeOut();
        JSONObject object = getWorkList(Jigou.getInstance().getToken(), lesson.getLessonId());
        SampleAssert.assertResult0(object);
    }

    @Override
    public String route() {
        return "mizhu/web/answer/getWorkList";
    }
}
