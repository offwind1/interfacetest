package inter.mizhu.web.lesson;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import parameter.people.Jigou;
import util.SampleAssert;
import util.StudentExcelCreator;

@RequiredArgsConstructor(staticName = "of")
public class UploadFile extends BasicsInterface {

    @Step
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
