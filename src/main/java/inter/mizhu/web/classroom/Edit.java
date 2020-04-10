package inter.mizhu.web.classroom;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class Edit extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String lessonId;
        private String classroomId;
        private String classroomName;
        private String interaction;
        private String startTime;
        private String coursewareId;
        private String timeLong;
        private String classroomOrdery;
        private String classroomRemark;
        private String teacherId;
        private String token;
    }

    @Step
    public JSONObject edit(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/classroom/edit";
    }
}
