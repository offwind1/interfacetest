package cn.vr168.interfacetest.inter.mizhu.web.usr;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.Teacher;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class OrgDelTeacher extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String token;
        private String orgId;
        private String teacherId;
        private String deleteType;

        public Bean init(User jigou, User teacher) {
            token = jigou.getToken();
            orgId = jigou.getOrgId();
            teacherId = teacher.getUserId();
            deleteType = "2";
            return this;
        }
    }

    @Step
    public JSONObject orgDelTeacher(Bean bean) {
        return post(Body.create()
                .add("token", bean.getToken())
                .add("orgId", bean.getOrgId())
                .add("teacherId", bean.getTeacherId())
                .add("deleteType", bean.getDeleteType())
                .build());
    }

    @Test
    public void test() {

        JSONObject jsonObject = orgDelTeacher(Bean.builder()
                .token(Jigou.getInstance().getToken())
                .orgId(Jigou.getInstance().getOrgId())
                .teacherId(Teacher.getInstance().getUserId())
                .deleteType("2").build());
    }

    @Override
    public String route() {
        return "mizhu/web/usr/orgDelTeacher";
    }
}
