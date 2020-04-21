package cn.vr168.interfacetest.inter.mizhu.web.course;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.excelCreator.LessonNeedClassroomExcelCreator;
import cn.vr168.interfacetest.kit.util.SampleAssert;

@RequiredArgsConstructor(staticName = "of")
public class FileExits extends BasicsInterface {

    @Step
    public JSONObject fileExits(String token, String md5) {
        return post(Body.create()
                .add("token", token)
                .add("md5", md5)
                .build());
    }

    @Test
    public void test() {
        String md5 = MD5.create().digestHex(LessonNeedClassroomExcelCreator.of().addLesson(LessonNeedClassroomExcelCreator.Bean.builder()
                .lessonName("测试课程")
                .startDate(DateUtil.date())
                .teacherPhone("18767126032")
                .build(), 5).build());
        JSONObject jsonObject = fileExits(Jigou.getInstance().getToken(), md5);
        SampleAssert.assertCode200(jsonObject);
    }
    
    @Override
    public String route() {
        return "mizhu/web/course/fileExits";
    }
}
