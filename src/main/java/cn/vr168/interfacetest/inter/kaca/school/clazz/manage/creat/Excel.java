package cn.vr168.interfacetest.inter.kaca.school.clazz.manage.creat;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.config.Environment;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.kit.excelCreator.TeacherExcelCreator;

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
