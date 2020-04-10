package inter.flow.item;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import inter.mizhu.web.item.*;
import org.apache.poi.ss.formula.functions.T;
import org.testng.annotations.Test;
import parameter.Lesson;
import parameter.LessonStore;
import parameter.people.Jigou;
import parameter.people.Student;
import util.SampleAssert;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BasicItemFlow {


    /**
     * 新建收费项目
     * * 添加课程
     * * 添加满减设定
     * 修改收费项目
     * 根据用户id，获取咔嚓收款项目
     * 删除收费项目
     */
    @Test
    public void test() {
        int count = 4;
        List<String> lessons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lessons.add(LessonStore.getRandomLesson().getLessonId());
        }

        String itemId = creatItem(lessons);
        getByIdCheck(itemId, lessons, null);

        List<Tuple2<String, String>> prices = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Tuple2<String, String> tuple = new Tuple2<>(String.valueOf(i * 10), String.valueOf(i * 5));
            prices.add(tuple);
        }
        updateItem(itemId, lessons, prices);
        getByIdCheck(itemId, lessons, prices);

        Student student = new Student("baby0001", "111111");
        JSONObject kachaListByUser = GetKachaListByUser.of().getKachaListByUser(student.getToken());
        String itemId_ = kachaListByUser.getJSONObject("data").getJSONArray("list").getJSONObject(0).getStr("itemId");
        assert itemId.equals(itemId_);

        JSONObject delete = Delete.of().delete(Jigou.getInstance().getToken(), itemId);
        SampleAssert.assertResult0(delete);
    }

    private String creatItem(List<String> lessons) {
        String itemName = "咔嚓" + RandomUtil.randomString(6);
        JSONObject add = Add.of().add(Add.Bean.builder()
                .itemName(itemName)
                .loginTypes("2,3")
                ._lessons(lessons)
                .token(Jigou.getInstance().getToken())
                .build());
        SampleAssert.assertResult0(add);


        JSONObject list = inter.mizhu.web.item.List.of().list(Jigou.getInstance().getToken(), "1", "100");
        for (JSONObject o : list.getJSONObject("data").getJSONArray("OrgPayitemList").jsonIter()) {
            if (o.getStr("itemName").equals(itemName)) {
                return o.getStr("itemId");
            }
        }
        throw new RuntimeException("未搜索到");
    }

    private void updateItem(String itemId, List<String> lessons, List<Tuple2<String, String>> items) {
        String itemName = "咔嚓" + RandomUtil.randomString(6);

        JSONObject update = Update.of().update(Update.Bean.builder()
                .itemId(itemId)
                .itemName(itemName)
                .loginTypes("2,3")
                .token(Jigou.getInstance().getToken())
                ._lessons(lessons)
                ._prices(items)
                .build());
        SampleAssert.assertResult0(update);
    }

    private void getByIdCheck(String itemId, List<String> lessons, List<Tuple2<String, String>> items) {
        JSONObject byId = GetById.of().getById(Jigou.getInstance().getToken(), itemId);
        JSONArray liList = byId.getJSONObject("data").getJSONObject("OrgPayitem").getJSONArray("liList");
        JSONArray idList = byId.getJSONObject("data").getJSONObject("OrgPayitem").getJSONArray("idList");

        if (lessons != null) {
            Set<String> li_set = liList.stream().map(i -> {
                JSONObject object = (JSONObject) i;
                return object.getStr("lessonId");
            }).collect(Collectors.toSet());

            Set<String> lesson_set = new HashSet<>(lessons);
            assert li_set.equals(lesson_set);
        }

        if (items != null) {
            Set<Tuple2<String, String>> id_set = idList.stream().map(i -> {
                JSONObject object = (JSONObject) i;
                return new Tuple2<String, String>(object.getStr("meetPrice"), object.getStr("discountsPrice"));
            }).collect(Collectors.toSet());

            Set<Tuple2<String, String>> item_set = new HashSet<>(id_set);
            assert id_set.equals(item_set);
        }
    }
}
