package cn.vr168.interfacetest.inter.mizhu.api.course;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class CoursewareInfo extends BasicsInterface {

    @Step
    public JSONObject coursewareInfo(String token, String coursewareId) {
        return post(Body.create()
                .add("token", token)
                .add("coursewareId", coursewareId)
//                .add("orgId", orgId)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/api/course/coursewareInfo";
    }
}
