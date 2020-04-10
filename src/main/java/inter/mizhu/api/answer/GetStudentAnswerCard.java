package inter.mizhu.api.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class GetStudentAnswerCard extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String answerCardId;
        private String lessonIs;
        private String classroomId;
        private String token;
    }

    @Step
    public JSONObject getStudentAnswerCard(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/api/answer/getStudentAnswerCard";
    }
}
