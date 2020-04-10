package inter.mizhumanage.web.lesson;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.LessonNeedClassroomExcelCreator;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class LessonUpfile extends BasicsInterface {


    @Step
    public JSONObject lessonUpfile(String token, String createUserId, byte[] bytes) {
        HttpRequest request = HttpRequest.post(getUrl());
        request = request.form("upfile", bytes, "111.xlsx")
                .form("token", token)
                .form("createUserId", createUserId);
        return post(request);
    }

    @Test
    public void test() {
        byte[] bytes = LessonNeedClassroomExcelCreator.of().addLesson(LessonNeedClassroomExcelCreator.Bean.builder()
                .lessonName("测试课程")
                .startDate(DateUtil.date())
                .teacherPhone("18767126032")
                .build(), 5).build();

        JSONObject jsonObject = lessonUpfile(Jigou.getInstance().getToken(), Jigou.getInstance().getUserId(), bytes);
        SampleAssert.assertCode200(jsonObject);
    }


    @Override
    public String route() {
        return "mizhumanage/web/lesson/lessonUpfile";
    }
}
