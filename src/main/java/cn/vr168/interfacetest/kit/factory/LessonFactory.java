package cn.vr168.interfacetest.kit.factory;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.List;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.User;

import java.util.ArrayList;

public class LessonFactory {
    private static Lesson lesson = null;

    public static Lesson takeOut() {
        if (lesson == null) {
            lesson = getRandomLesson();
        }
        return lesson;
    }

    public static Lesson creat(User user) {
        return Lesson.builder(user.getToken()).build();
    }

    public static Lesson creat() {
        return creat(Jigou.getInstance());
    }

    public static Lesson putIn(Lesson lesson) {
        return LessonFactory.lesson = lesson;
    }

    public static Lesson putIn(Lesson.LessonBuilder builder) {
        return lesson = builder.build();
    }

    public static Lesson getFirstLesson(User user) {
        JSONObject jsonObject = List.of().list(user.getToken());
        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("list");
        if (array.size() > 0) {
            return lesson = Lesson.from(user.getToken(), getLessonIdFromJson(array, 0));
        }
        return lesson = Lesson.builder(user.getToken()).build();
    }

    public static java.util.List<Lesson> lessonList() {
        return lessonList(Jigou.getInstance());
    }

    public static java.util.List<Lesson> lessonList(User user) {
        JSONObject jsonObject = List.of().list(List.Bean.builder()
                .currentPage("1")
                .pubType("9")
                .pageSize("10")
                .token(user.getToken())
                .org(user.getOrgId())
                .webItem("1")
                .build());

        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("list");

        java.util.List<Lesson> lessons = new ArrayList<>();
        for (JSONObject object : array.jsonIter()) {
            lessons.add(Lesson.from(user.getToken(), object.getStr("lessonId")));
        }
        return lessons;
    }

    public static Lesson getRandomLesson(User user) {
        JSONObject jsonObject = List.of().list(user.getToken());
        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("list");
        if (array.size() > 0) {
            return lesson = Lesson.from(user.getToken(), getLessonIdFromJson(array, RandomUtil.randomInt(array.size())));
        }
        return lesson = Lesson.builder(user.getToken()).build();
    }

    public static Lesson getRandomLesson() {
        return getRandomLesson(Jigou.getInstance());
    }

    private static String getLessonIdFromJson(JSONArray array, int index) {
        return array.getJSONObject(index).getStr("lessonId");
    }

}
