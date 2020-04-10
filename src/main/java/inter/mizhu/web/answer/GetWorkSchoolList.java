package inter.mizhu.web.answer;

import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


/**
 * 提交作业的学校列表
 */
@RequiredArgsConstructor(staticName = "of")
public class GetWorkSchoolList extends BasicsInterface {

    @Builder
    @Data
    public static class Bean {
        private String answerCardId;
        private String classroomId;
        private String orgId;
        private String token;
    }

    @Step
    public JSONObject getWorkSchoolList(Bean bean) {
        return post(beanToMap(bean));
    }


    @Override
    public String route() {
        return "mizhu/web/answer/getWorkSchoolList";
    }
}
