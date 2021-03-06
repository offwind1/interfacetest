package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.Student;

@RequiredArgsConstructor(staticName = "of")
public class AddTeacherStudent extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String ids;
        private String lessonId;
        private String stuId;
        private String token;
    }

    @Step
    public JSONObject addTeacherStudent(Bean bean) {
        return post(beanToMap(bean));
    }

    public JSONObject addTeacherStudent(String token, String lessonId, String ids) {
        return addTeacherStudent(Bean.builder()
                .token(token)
                .lessonId(lessonId)
                .ids(ids)
                .build());
    }

    @Test
    public void test() {
        JSONObject add = addTeacherStudent(Jigou.getInstance().getToken(), LessonFactory.takeOut().getLessonId(), Student.getInstance().getUserId());
        String msg = add.getStr("msg");
        assert msg.equals("加失败，重复添加！") || msg.equals("添加成功");
    }


    @Override
    public String route() {
        return "mizhu/web/lesson/addTeacherStudent";
    }
}
