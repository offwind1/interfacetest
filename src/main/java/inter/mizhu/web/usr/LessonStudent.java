package inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.Lesson;
import parameter.LessonStore;
import parameter.people.Jigou;
import util.SampleAssert;

import java.util.Base64;

@RequiredArgsConstructor(staticName = "of")
public class LessonStudent extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String currentPage;
        private String pageSize;
        private String lessonId;
        private String account;
        private String token;
    }

    public JSONObject lessonStudent(String token, String lessonId) {
        return lessonStudent(Bean.builder()
                .token(token)
                .lessonId(lessonId)
                .currentPage("1")
                .pageSize("15")
                .build());
    }

    public JSONObject lessonStudent(String token, String lessonId, int pageSize) {
        return lessonStudent(Bean.builder()
                .token(token)
                .lessonId(lessonId)
                .currentPage("1")
                .pageSize(String.valueOf(pageSize))
                .build());
    }

    @Test
    public void test() {
        Lesson lesson = LessonStore.takeOut();
        JSONObject object = lessonStudent(Jigou.getInstance().getToken(), lesson.getLessonId());
        SampleAssert.assertCode200(object);
    }


    @Step
    public JSONObject lessonStudent(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/usr/lessonStudent";
    }
}
