package inter.kaca.school.clazz.manage.creat;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import config.Environment;
import inter.BasicsInterface;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;
import util.Body;
import util.SampleAssert;
import util.TeacherExcelCreator;

@Log4j
@RequiredArgsConstructor(staticName = "of")
public class Excel extends BasicsInterface {

    @Step
    public JSONObject excel(byte[] bytes, String schoolId, String year) {
        HttpRequest request = HttpRequest.post(getUrl());
        request = request
                .form("excelFile", bytes, "123.xlsx")
                .form("schoolId", schoolId)
                .form("year", year);
        return post(request);
    }

    @Test
    public void test() {

        TeacherExcelCreator excelCreator = TeacherExcelCreator.of()
                .addTeacher("9年1班",
                        "二年级",
                        "小龙",
                        "语文",
                        Environment.getValue("phone.registered")
                );

        JSONObject object = excel(excelCreator.build(), "8386", "2020");
        SampleAssert.assertResult0(object);
    }


    @Override
    public String route() {
        return "school/class/manage/creat/excel";
    }
}
