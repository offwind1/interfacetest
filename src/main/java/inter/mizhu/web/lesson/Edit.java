package inter.mizhu.web.lesson;

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
        private String gradeIds;
        private String gradeNames;
        private String lessonTypeId;
        private String startTime;
        private String endTime;
        private String tryLook;
        private String apply;
        private String lessonTerm;
        private String lessonName;
        private String faceImg;
        private String classroomCount;
        private String classTime;
        private String classroomPrice;
        private String discount;
        private String free;
        private String lessRemark;
        private String lessonTag;
        private String studentCount;
        private String classInfo;
        private String price;
        private String buyType;
        private String custRelease;
        private String token;
    }


    @Step
    public JSONObject edit(Bean bean) {
        return post(beanToMap(bean));
    }


    @Override
    public String route() {
        return "mizhu/web/lesson/edit";
    }
}
