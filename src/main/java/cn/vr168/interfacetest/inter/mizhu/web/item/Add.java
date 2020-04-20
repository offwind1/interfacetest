package cn.vr168.interfacetest.inter.mizhu.web.item;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import groovy.lang.Tuple2;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 添加收款项目
 */
@RequiredArgsConstructor(staticName = "of")
public class Add extends BasicsInterface {

    @Data
    @Builder
    public static class Bean {
        private String itemName;
        private String loginTypes;
        private String token;
        private List<String> _lessons;
        private List<Tuple2<String, String>> _prices;

        public String getLessonIdJson() {
            JSONArray array = new JSONArray();
            if (_lessons != null) {
                for (int i = 0; i < _lessons.size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put("lessonId", _lessons.get(i));
                    object.put("required", 1);
                    object.put("seq", i);
                    array.add(object);
                }
            }
            return array.toString();
        }

        public String getItemDiscountJson() {
            JSONArray array = new JSONArray();
            if (_prices != null) {
                for (int i = 0; i < _prices.size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put("meetPrice", _prices.get(i).getFirst());
                    object.put("discountsPrice", _prices.get(i).getSecond());
                    array.add(object);
                }
            }
            return array.toString();
        }
    }

    @Step
    public JSONObject add(Bean bean) {
        Map<String, Object> map = beanToMap(bean);
        map.put("lessonIdJson", bean.getLessonIdJson());
        map.put("itemDiscountJson", bean.getItemDiscountJson());
        map.put("introduce", "<p>尚禾教育，尚享新智能，禾苗润必舒。</p>");
        return post(map);
    }

    @Override
    public String route() {
        return "mizhu/web/item/add";
    }
}
