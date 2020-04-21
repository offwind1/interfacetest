package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhumanage.web.classroom.GetOnlineRecord;
import cn.vr168.interfacetest.inter.mizhumanage.web.qiniu.GetHistoryInfo;
import cn.vr168.interfacetest.inter.mizhumanage.web.qiniu.GetHistoryRecords;
import cn.vr168.interfacetest.kit.factory.LessonFactory;
import cn.vr168.interfacetest.kit.page.LessonPage;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import org.testng.annotations.Test;

public class LiveAndClassRecordTestFlow {
    private Lesson lesson = LessonFactory.creat();

    {
        lesson.applied();
        LessonPage.ClassStartInfo classStartInfo = LessonPage.startClass(lesson);
        try {
            Thread.sleep(3000);
            LessonPage.endClass(classStartInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void liveRecord() {
        JSONObject records = GetHistoryRecords.of().getHistoryRecords(Jigou.getInstance().getToken(),
                lesson.getClassRoom(0).getClassroomId());
        SampleAssert.assertMsg(records, "查询成功!");
        SampleAssert.assertCode200(records);

        JSONObject info = GetHistoryInfo.of().getHistoryInfo(Jigou.getInstance().getToken(),
                lesson.getClassRoom(0).getClassroomId());
        SampleAssert.assertCode200(info);
        SampleAssert.assertMsg(info, "查询成功!");
    }

    @Test
    public void classRecord() {
        JSONObject record = GetOnlineRecord.of().getOnlineRecord(Jigou.getInstance().getToken(),
                lesson.getClassRoom(0).getClassroomId());
        SampleAssert.assertCode200(record);
    }


}
