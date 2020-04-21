package cn.vr168.interfacetest.inter.mizhu.web.lesson;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.BasicsInterface;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import cn.vr168.interfacetest.kit.util.Body;

@RequiredArgsConstructor(staticName = "of")
public class JoinClassByFile extends BasicsInterface {


    @Step
    public JSONObject joinClassByFile(String token, String fileName, String stuId) {
        return post(Body.create()
                .add("fileName", fileName)
                .add("stuId", stuId)
                .add("token", token)
//                .add("tokenKC", "14e8e484-1ec2-4e88-b3a9-13e453958a3f")
                .build());
    }

    @Override
    public String route() {
        return "mizhu/web/lesson/joinClassByFile";
    }
}
