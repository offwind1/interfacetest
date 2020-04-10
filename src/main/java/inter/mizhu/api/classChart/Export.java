package inter.mizhu.api.classChart;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.IOException;

@RequiredArgsConstructor(staticName = "of")
public class Export extends BasicsInterface {

    @Step
    public HttpResponse export(String token, String classroomId) {
        HttpRequest request = HttpRequest.get(getUrl());
        return get(request.form("token", token).form("classroomId", classroomId));
    }

    @Test
    public void test() throws IOException {
        HttpResponse response = export("66A9CFEE-2E6E-4A14-8C6E-C4229BAF709D", "10564c2f45cf452f933efaa17ad46350");
//        System.out.println(response.bodyBytes());
//        HSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook(response.bodyStream());
        System.out.println(workbook.getSheetName(1));
    }

    @Override
    public String route() {
        return "mizhu/api/classChart/export";
    }
}
