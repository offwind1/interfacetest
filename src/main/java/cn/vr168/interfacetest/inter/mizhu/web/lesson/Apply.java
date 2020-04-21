package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.people.HasToken;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class Apply extends BasicsInterface {


    public JSONObject apply(HasToken hasToken, String lessonId) {
        return apply(hasToken.getToken(), lessonId);
    }

    @Step
    public JSONObject apply(String token, String lessonId) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .build());
    }


    @BeforeClass
    public void beforeClass() {
        LessonFactory.putIn(Lesson.builder(Jigou.getInstance().getToken()).build());
    }

    @Test(description = "正常")
    public void test() {
        JSONObject jsonObject = apply(Jigou.getInstance(),
                Lesson.builder(Jigou.getInstance().getToken()).build().getLessonId());
        assert jsonObject.getStr("code").equals("200");
    }

    @Test(description = "token为空")
    public void test1() {
        JSONObject jsonObject = apply(new HasToken() {
            @Override
            public String getToken() {
                return "";
            }
        }, Lesson.builder(Jigou.getInstance().getToken()).build().getLessonId());
        assert jsonObject.getStr("code").equals("300");
        assert jsonObject.getStr("msg").equals("token无效或参数有误!");
    }

    @Test(description = "lessonId 为空")
    public void test2() {
        JSONObject jsonObject = apply(Jigou.getInstance(), "");
        assert jsonObject.getStr("code").equals("300");
        assert jsonObject.getStr("msg").equals("token无效或参数有误!");
    }


    @Override
    public String route() {
        return "mizhu/web/lesson/apply";
    }
}
