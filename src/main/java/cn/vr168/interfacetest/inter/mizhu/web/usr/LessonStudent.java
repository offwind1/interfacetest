package cn.vr168.interfacetest.inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class LessonStudent extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String currentPage;
        private String pageSize;
        private String lessonId;
        private String accounts;
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
        Lesson lesson = LessonFactory.takeOut();
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
