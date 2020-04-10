package inter.mizhu.web.course;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import config.Environment;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.Body;
import util.LessonNeedClassroomExcelCreator;
import util.SampleAssert;

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
