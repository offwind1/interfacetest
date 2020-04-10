package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 学生主观题列表
 */
@RequiredArgsConstructor(staticName = "of")
public class GetStudentSubjectiveItemList extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String answerCardId;
        private String classroomId;
        private String seq;
        private String classId;
        private String orgId;
        private String token;
    }

    @Step
    public JSONObject getStudentSubjectiveItemList(Bean bean) {
        return post(beanToMap(bean));
    }

    @Override
    public String route() {
        return "mizhu/web/answer/getStudentSubjectiveItemList";
    }
}
