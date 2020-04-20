package cn.vr168.interfacetest.inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.LessonStore;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class GetTeacherList extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String lessonId;
        private String classroomId;
        private String token;
    }

    @Step
    public JSONObject getTeacherList(Bean bean) {
        return post(beanToMap(bean));
    }

    public JSONObject getTeacherList(String token, String lessonId, String classroomId) {
        return getTeacherList(Bean.builder()
                .token(token)
                .lessonId(lessonId)
                .classroomId(classroomId)
                .build());
    }

    public JSONObject getTeacherList(String token, String lessonId) {
        return getTeacherList(token, lessonId, "");
    }


    @Test(description = "正常调用")
    public void test() {
        JSONObject jsonObject = getTeacherList(Jigou.getInstance().getToken(),
                LessonStore.takeOut().getLessonId());
        SampleAssert.assertResult0(jsonObject);
    }

    @Test(description = "正常调用2")
    public void test1() {
        JSONObject jsonObject = getTeacherList(Jigou.getInstance().getToken(),
                LessonStore.takeOut().getLessonId(),
                LessonStore.takeOut().getClassRoom(0).getClassroomId());
        SampleAssert.assertResult0(jsonObject);
    }


    @Override
    public String route() {
        return "mizhu/web/classroom/getTeacherList";
    }
}
