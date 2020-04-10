package inter.mizhu.api.classInfo;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import lombok.RequiredArgsConstructor;
import util.Body;

@RequiredArgsConstructor(staticName = "of")
public class ClassroomEnd extends BasicsInterface {

    public JSONObject classroomEnd(String classroomVideoId, String cloudAccount) {
        return post(Body.create()
                .add("classroomVideoId", classroomVideoId)
                .add("cloudAccount", cloudAccount)
                .build());
    }

    @Override
    public String route() {
        return "mizhu/api/classInfo/classroomEnd";
    }
}
