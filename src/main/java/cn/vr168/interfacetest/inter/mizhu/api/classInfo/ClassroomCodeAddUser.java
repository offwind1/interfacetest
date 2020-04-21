package cn.vr168.interfacetest.inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class ClassroomCodeAddUser extends BasicsInterface {

    @Step
    public JSONObject classroomCodeAddUser(User user, String classroomCode) {
        return post(Body.create()
                .add("token", user.getToken())
                .add("userId", user.getUserId())
                .add("classroomCode", classroomCode)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/api/classInfo/classroomCodeAddUser";
    }
}
