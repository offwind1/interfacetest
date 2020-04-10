package inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import inter.mizhu.web.grade.ClassList;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.Lesson;
import parameter.people.Jigou;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class StudentByClassId extends BasicsInterface {

    @Step
    public JSONObject studentByClassId(String token, String lessonId, String stuId) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .add("stuId", stuId)
                .build());
    }

    @Test
    public void test() {

        JSONObject lessonList = List.of().list(Jigou.getInstance().getToken());
        String lessonId = lessonList.getJSONObject("data").getJSONArray("list")
                .getJSONObject(0).getStr("lessonId");

        JSONObject classList = ClassList.of().classList(Jigou.getInstance());
        String stuId = classList.getJSONObject("data").getJSONArray("list")
                .getJSONObject(0).getStr("stuId");

        JSONObject jsonObject = studentByClassId(Jigou.getInstance().getToken(),
                lessonId, stuId);
        SampleAssert.assertCode200(jsonObject);
    }


    @Override
    public String route() {
        return "mizhu/web/lesson/studentByClassId";
    }
}
