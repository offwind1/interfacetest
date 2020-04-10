package inter.mizhumanage.web.lesson;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.LessonStore;
import parameter.people.Admin;
import parameter.people.HasToken;
import util.Body;
import util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class LessonReply extends BasicsInterface {

    public JSONObject lessonReply(HasToken hasToken, String lessonId, String pubType, String pubMsg) {
        return lessonReply(hasToken.getToken(), lessonId, pubType, pubMsg);
    }

    @Step
    public JSONObject lessonReply(String token, String lessonId, String pubType, String pubMsg) {
        return post(Body.create()
                .add("token", token)
                .add("lessonId", lessonId)
                .add("pubType", pubType)
                .add("pubMsg", pubMsg)
                .build());
    }

    public JSONObject lessonReplyPass(HasToken hasToken, String lessonId) {
        return lessonReply(hasToken, lessonId, "9", "");
    }

    public JSONObject lessonReplyPass(String token, String lessonId) {
        return lessonReply(token, lessonId, "9", "");
    }

    public JSONObject lessonReplyNoPass(HasToken hasToken, String lessonId) {
        return lessonReply(hasToken, lessonId, "2", "");
    }

    public JSONObject lessonReplyNoPass(String token, String lessonId) {
        return lessonReply(token, lessonId, "2", "");
    }

    @Test(description = "正常调用 不通过")
    public void test() {

        JSONObject jsonObject = lessonReplyNoPass(Admin.getInstance(), LessonStore.takeOut().getLessonId());
        assert jsonObject.getStr("code").equals("200");
    }

    @Test(description = "正常调用 通过")
    public void test1() {
        JSONObject jsonObject = lessonReplyPass(Admin.getInstance(), LessonStore.takeOut().getLessonId());
        assert jsonObject.getStr("code").equals("200");
    }

    @Test(description = "token为空")
    public void test2() {
        JSONObject jsonObject = lessonReplyPass(new HasToken() {
            @Override
            public String getToken() {
                return "";
            }
        }, LessonStore.takeOut().getLessonId());
        assert jsonObject.getStr("code").equals("300");
        assert jsonObject.getStr("msg").equals("token无效!");
    }

//    @Test(description = "lessonId为空")
    public void test3() {
        JSONObject jsonObject = lessonReplyPass(Admin.getInstance(), "");
        SampleAssert.assertCode(jsonObject, "300");
        SampleAssert.assertMsg(jsonObject, "lessonId无效!");
    }

//    @Test(description = "pubType 为100")
    public void test4() {
        JSONObject jsonObject = lessonReply(Admin.getInstance(), LessonStore.takeOut().getLessonId(), "100", "");
        SampleAssert.assertCode(jsonObject, "300");
        SampleAssert.assertMsg(jsonObject, "pubType无效!");
    }

    @Override
    public String route() {
        return "mizhumanage/web/lesson/lessonReply";
    }
}
