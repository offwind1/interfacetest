package cn.vr168.interfacetest.inter.mizhu.api.lessonInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.bean.BeanPage;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.people.Jigou;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

@RequiredArgsConstructor(staticName = "of")
public class MyLessonPC extends BasicsInterface {

    @Data
    @Builder
    public static class Bean extends BeanPage {
        private String lessonName;
        private String lessonTerm;
        private String userId;
        private String token;
    }

    @Step
    public JSONObject myLessonPC(Bean bean) {
        return post(beanToMap(bean));
    }

    @Test
    public void test() {
        JSONObject object = myLessonPC(Bean.builder()
                .lessonName("")
                .lessonTerm("1")
                .userId(Jigou.getInstance().getUserId())
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(object);
    }

    @Override
    public String route() {
        return "mizhu/api/lessonInfo/myLessonPC";
    }
}
