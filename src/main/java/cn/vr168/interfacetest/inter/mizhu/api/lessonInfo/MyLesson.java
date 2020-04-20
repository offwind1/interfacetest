package cn.vr168.interfacetest.inter.mizhu.api.lessonInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.HasToken;
import cn.vr168.interfacetest.parameter.people.Student;
import cn.vr168.interfacetest.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class MyLesson extends BasicsInterface {

    @Step("我的课程")
    public JSONObject myLesson(HasToken hasToken, String lessonName, String page, String lessonTerm) {
        return post(Body.create()
                .add("token", hasToken.getToken())
                .add("lessonName", lessonName)
                .add("page", page)
                .add("lessonTerm", lessonTerm)
                .build());
    }

    public JSONObject myLesson(HasToken hasToken) {
        return myLesson(hasToken, "", "1", "1");
    }

    public JSONObject myLesson(HasToken hasToken, String lessonName) {
        return myLesson(hasToken, lessonName, "1", "1");
    }

    public JSONObject myLesson(HasToken hasToken, String lessonName, String lessonTerm) {
        return myLesson(hasToken, lessonName, "1", lessonTerm);
    }

    @Test(description = "正常调用")
    public void test() {
        JSONObject jsonObject = myLesson(Student.getInstance());
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "userId 替代token")
    public void test1() {
        JSONObject jsonObject = myLesson(new HasToken() {
            @Override
            public String getToken() {
                return Student.getInstance().getUserId();
            }
        });
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "调用微课")
    public void test2() {
        JSONObject jsonObject = myLesson(Student.getInstance(), "", "2");
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "lessonTerm 调用3")
    public void test3() {
        JSONObject jsonObject = myLesson(Student.getInstance(), "", "3");
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "page 调用负数")
    public void test5() {
        JSONObject jsonObject = myLesson(Student.getInstance(), "", "-3", "1");
        assert jsonObject.getStr("result").equals("0");
    }

    @Test(description = "token为空")
    public void test4() {
        JSONObject jsonObject = myLesson(new HasToken() {
            @Override
            public String getToken() {
                return "";
            }
        });
        assert jsonObject.getStr("result").equals("-100");
        assert jsonObject.getStr("msg").equals("无效TOKEN!");
    }


    @Override
    public String route() {
        return "/mizhu/api/lessonInfo/myLesson";
    }
}
