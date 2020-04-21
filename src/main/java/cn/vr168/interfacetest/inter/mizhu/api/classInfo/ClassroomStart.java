package cn.vr168.interfacetest.inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import lombok.RequiredArgsConstructor;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class ClassroomStart extends BasicsInterface {

    public JSONObject classroomStart(String token, String classroomId) {
        return post(Body.create()
                .add("token", token)
                .add("classroomId", classroomId)
                .add("isopenHeat", "0")
                .add("vs", "0")
                .add("recorded", "0")
                .add("appId", "dkzp6zxxh")
                .build());
    }
    
    @Override
    public String route() {
        return "mizhu/api/classInfo/classroomStart";
    }
}
