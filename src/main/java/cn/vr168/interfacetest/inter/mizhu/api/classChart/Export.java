package cn.vr168.interfacetest.inter.mizhu.api.classChart;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.Test;

import java.io.IOException;

@RequiredArgsConstructor(staticName = "of")
public class Export extends BasicsInterface {

    @Step
    public HttpResponse export(String token, String classroomId) {
        HttpRequest request = HttpRequest.get(getUrl());
        return get(request.form("token", token).form("classroomId", classroomId));
    }

    @Override
    public String route() {
        return "mizhu/api/classChart/export";
    }
}
