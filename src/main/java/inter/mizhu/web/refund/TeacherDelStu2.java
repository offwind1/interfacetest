package inter.mizhu.web.refund;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import parameter.Lesson;
import parameter.people.HasToken;
import parameter.people.Jigou;
import parameter.people.Student;
import parameter.people.Teacher;
import util.Body;

@RequiredArgsConstructor(staticName = "of")
public class TeacherDelStu2 extends BasicsInterface {
    @Step
    public JSONObject teacherDelStu2(HasToken hasToken, String lessonId, String userIds) {
        return post(Body.create()
                .add("token", hasToken.getToken())
                .add("lessonId", lessonId)
                .add("userIds", userIds)
                .build());
    }

//    @BeforeClass
//    public void before() {
//        LessonUtil.getInstance().applyAndReply();
//    }

    @Test(description = "正常调用")
    public void test() {
        JSONObject jsonObject = teacherDelStu2(Jigou.getInstance(),
                Lesson.builder(Jigou.getInstance().getToken()).build().getLessonId(),
                Student.getInstance().getUserId());
        assert jsonObject.getStr("code").equals("200");
        assert jsonObject.getStr("msg").equals("删除成功");
    }

    @Test(description = "userId为空")
    public void test1() {
        JSONObject jsonObject = teacherDelStu2(Jigou.getInstance(),
                Lesson.builder(Jigou.getInstance().getToken()).build().getLessonId(),
                "");
        assert jsonObject.getStr("code").equals("300");
        assert jsonObject.getStr("msg").equals("token无效或参数有误!");
    }


    @Override
    public String route() {
        return "/mizhu/web/refund/teacherDelStu2";
    }
}
