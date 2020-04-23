package cn.vr168.interfacetest.inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import cn.vr168.interfacetest.kit.util.Body;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class Delete extends BasicsInterface {

    @Step
    public JSONObject delete(String token, String classroomIds, String lessonId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomIds", classroomIds)
                .add("lessonId", lessonId)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/web/classroom/delete";
    }
}
