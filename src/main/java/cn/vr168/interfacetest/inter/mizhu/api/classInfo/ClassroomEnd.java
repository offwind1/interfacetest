package cn.vr168.interfacetest.inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import lombok.RequiredArgsConstructor;
import cn.vr168.interfacetest.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class ClassroomEnd extends BasicsInterface {

    public JSONObject classroomEnd(String token, String classroomVideoId, String cloudAccount) {
        return post(Body.create()
                .add("classroomVideoId", classroomVideoId)
                .add("cloudAccount", cloudAccount)
                .add("token", token)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/api/classInfo/classroomEnd";
    }
}
