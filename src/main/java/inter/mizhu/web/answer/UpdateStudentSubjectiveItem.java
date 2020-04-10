package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class UpdateStudentSubjectiveItem extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String answerCardId;
        private String classroomId;
        private String seq;
        private String userId;
        private String answerResult;
        private String token;
    }

    @Step
    public JSONObject updateStudentSubjectiveItem(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/answer/updateStudentSubjectiveItem";
    }
}
