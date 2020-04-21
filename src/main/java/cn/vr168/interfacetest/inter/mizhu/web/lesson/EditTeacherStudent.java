package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class EditTeacherStudent extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        public String token;
        public String lessonId;
        public String stuId;
    }

    @Step
    public JSONObject editTeacherStudent(Bean bean) {
        return post(beanToMap(bean));
    }

    @Test
    public void test() {
        String lessonId = Environment.getValue("lessonId.weike");

        JSONObject jsonObject = editTeacherStudent(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .stuId("520")
                .lessonId(lessonId)
                .build());
        SampleAssert.assertCode200(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/web/lesson/editTeacherStudent";
    }
}
