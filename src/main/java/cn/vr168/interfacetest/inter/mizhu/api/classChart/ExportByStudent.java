package cn.vr168.interfacetest.inter.mizhu.api.classChart;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class ExportByStudent extends BasicsInterface {
    
    @Step
    public HttpResponse export(String token, String lessonId) {
        HttpRequest request = HttpRequest.get(getUrl());
        return get(request.form("token", token)
                .form("lessonId", lessonId));
    }

    @Override
    public String route() {
        return "mizhu/api/classChart/exportByStudent";
    }
}
