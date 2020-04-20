package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.inter.mizhu.web.grade.ClassList;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.Body;
import cn.vr168.interfacetest.util.SampleAssert;

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
