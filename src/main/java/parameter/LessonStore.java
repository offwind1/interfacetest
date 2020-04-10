package parameter;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import inter.mizhu.web.lesson.List;
import parameter.people.Jigou;
import parameter.people.User;

public class LessonStore {
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
        return LessonStore.lesson = lesson;
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
