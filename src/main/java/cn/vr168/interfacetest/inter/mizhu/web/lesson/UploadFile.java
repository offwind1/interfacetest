package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.util.SampleAssert;
import cn.vr168.interfacetest.util.StudentExcelCreator;

@RequiredArgsConstructor(staticName = "of")
public class UploadFile extends BasicsInterface {

    @Step("上传文件")
    public JSONObject uploadFile(String token, byte[] bytes) {
        HttpRequest request = HttpRequest.post(getUrl());
        request = request.form("upfile", bytes, "111.xlsx")
                .form("token", token);
        return post(request);
    }

    @Test
    public void test() {
        StudentExcelCreator excelCreator = StudentExcelCreator.of();
        excelCreator = excelCreator.addStudent("新生娃%04d", "baby%04d", "111111", 5);
        JSONObject jsonObject = uploadFile(Jigou.getInstance().getToken(), excelCreator.build());
        SampleAssert.assertCode200(jsonObject);
    }

    @Override
    public String route() {
        return "mizhu/web/lesson/uploadFile";
    }
}
