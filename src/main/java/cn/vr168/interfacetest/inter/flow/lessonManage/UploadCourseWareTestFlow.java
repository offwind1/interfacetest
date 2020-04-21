package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.ResourcesUtil;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.OptionList;
import cn.vr168.interfacetest.inter.mizhu.api.course.UploadFile2;
import cn.vr168.interfacetest.inter.mizhu.web.qiniu.GetUptoken;
import cn.vr168.interfacetest.kit.factory.ClassFactory;
import cn.vr168.interfacetest.parameter.Clazz;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 上传课件相关测试
 */
public class UploadCourseWareTestFlow {

    /**
     * 课件
     * 上传文件 自动匹配到对应的类型中
     * <p>
     * 允许在线观看:
     * jpg,png,gif,PDF,mov,mp4,flv,mp3
     * <p>
     * 不允许在线观看：
     * rar,zip,doc,docx,ppt,pptx,xls,xlsx
     */

    private Lesson lesson = Lesson.builder().lessonName("课件测试" + RandomUtil.randomString(6)).build();
    private Clazz clazz = ClassFactory.findClass("答题卡专用");
    private String token = Jigou.getInstance().getToken();

    {
        lesson.addClass(clazz.getStuId());
        lesson.applied();
    }

    private String[][] strings = {
            {"动图.gif", "7"},
            {"压缩包.rar", "14"},
            {"压缩包.zip", "14"},
            {"图片.jpg", "7"},
            {"图片.png", "7"},
            {"声音.mp3", "12"},
            {"文档.doc", "9"},
            {"文档.docx", "9"},
            {"演示文稿.ppt", "9"},
            {"演示文稿.pptx", "9"},
            {"表格.xls", "9"},
            {"表格.xlsx", "9"},
            {"视频.flv", "3"},
            {"视频.mov", "3"},
            {"视频.mp4", "3"},
            {"文档.pdf", "13"},
    };


    @Test(description = "")
    public void test() {
        List<String> list = new ArrayList<>();
        for (String[] file : strings) {
            JSONObject uptoken = GetUptoken.of().getUptoken(token, file[0], file[1]);
            String qiniu_token = uptoken.getJSONObject("data").getStr("uptoken");
            upload(qiniu_token, file[0]);

            JSONObject object = UploadFile2.of().uploadFile2(UploadFile2.Bean.builder()
                    .lessonId(lesson.getLessonId())
                    .classroomId(lesson.getClassRoom(0).getClassroomId())
                    .coursewareType(file[1])
                    .sourceUrl("http://images.mizholdings.com/" + file[0])
                    .coursewareName(file[0])
                    .token(token)
                    .build());

            String coursewareId = object.getJSONObject("data").getStr("coursewareId");
            list.add(coursewareId);
        }

//        JSONObject coursewareInfo = CoursewareInfo.of().coursewareInfo(token, lesson.getClassRoom(0).getClassroomId());
        JSONObject optionList = OptionList.of().optionList(token, lesson.getClassRoom(0).getClassroomId());
        Set<String> set = optionList.getJSONObject("data").getJSONArray("optionList").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("coursewareId");
        }).collect(Collectors.toSet());

        assert set.equals(new HashSet<>(list)) : "课件id不正确";

    }


    private void upload(String qiniu_token, String key) {
        byte[] array = ResourcesUtil.getCourse(key);

        HttpRequest request = HttpRequest.post("https://upload-z2.qiniup.com/");
        request = request.form("token", qiniu_token)
                .form("key", key)
                .form("x:filename", key)
                .form("x:size", array.length)
                .form("file", array, key);

        HttpResponse response = request.execute();
        System.out.println(response.body());
    }


}
