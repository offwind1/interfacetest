package inter.mizhu.api.answer;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;

import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class SaveAnswerCard extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String token;
        private String lessonId;
        private String classroomId;
        private String answerCardId;
        private JSONArray _array;
    }


    @Step
    public JSONObject saveAnswerCard(Bean bean) {
        Map<String, Object> map = beanToMap(bean);
        map.put("answerListJson", bean._array.toString());
        return post(map);
    }

    @Test
    public void t() {
        System.out.println(String.valueOf((char) (65)));
    }

    @Override
    public String route() {
        return "mizhu/api/answer/saveAnswerCard";
    }
}
