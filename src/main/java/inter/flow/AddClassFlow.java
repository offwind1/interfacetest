package inter.flow;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import inter.mizhu.web.grade.AddClass;
import inter.mizhu.web.grade.ClassList;
import inter.mizhu.web.grade.DelClass;
import inter.mizhu.web.grade.UserByStuId;
import inter.mizhu.web.lesson.JoinClassByFile;
import inter.mizhu.web.lesson.UploadFile;
import lombok.NonNull;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.testng.annotations.Test;
import parameter.GradeUtil;
import parameter.people.Jigou;
import util.ExcelCreator;
import util.SampleAssert;
import util.StudentExcelCreator;

public class AddClassFlow {

    /**
     * 创建班级，添加学生
     */
    @Test
    public void test() {
        // 新增班级
        String className = "班级" + RandomUtil.randomString(6);
        JSONObject addClass = AddClass.of().addClass(Jigou.getInstance(), className, "1");
        SampleAssert.assertCode200(addClass);
        String classId = addClass.getJSONObject("data").getStr("classId");
        //批量添加学生
        //上传文件
        StudentExcelCreator excelCreator = StudentExcelCreator.of();
        excelCreator = excelCreator.addStudent("新生娃%04d", "baby%04d", "111111", 5);
        JSONObject uploadFile = UploadFile.of().uploadFile(Jigou.getInstance().getToken(), excelCreator.build());
        SampleAssert.assertCode200(uploadFile);
        String fileName = uploadFile.getJSONObject("data").getStr("fileName");
        //通过文件，添加学生
        JSONObject jsonObject = JoinClassByFile.of().joinClassByFile(Jigou.getInstance().getToken(), fileName, classId);
        SampleAssert.assertCode200(jsonObject);

        //查看班级学生
        JSONObject userByStuId = UserByStuId.of().userByStuId(Jigou.getInstance().getToken(), classId);
        userByStuId.getJSONObject("data").getJSONArray("list").stream().forEach(i -> {
            JSONObject o = (JSONObject) i;
            assert o.getStr("account").contains("baby");
            assert o.getStr("userName").contains("新生娃");
            assert o.getStr("gradeName").equals("一年级");
            assert o.getStr("stuId").equals(classId);
        });
    }

    //TODO 上传超过200条的学生导入文件。检查 接口是否通过，学生数量是否正确
    @Test(description = "上传超过200条的学生导入文件。检查 接口是否通过，学生数量是否正确")
    public void test1() {

        String className = "班级" + RandomUtil.randomString(6);
        int studentCount = 200;

        //创建班级
        JSONObject addClass = AddClass.of().addClass(Jigou.getInstance(), className, "1");
        SampleAssert.assertCode200(addClass);
        String classId = addClass.getJSONObject("data").getStr("classId");

        //创建文档
        ExcelCreator excelCreator = StudentExcelCreator.of()
                .addStudent("机器人%04d", "robot%04d", "111111", studentCount);
        //上传文件
        JSONObject uploadFile = UploadFile.of().uploadFile(Jigou.getInstance().getToken(), excelCreator.build());
        SampleAssert.assertCode200(uploadFile);
        String fileName = uploadFile.getJSONObject("data").getStr("fileName");

        //通过文件，添加学生
        JSONObject jsonObject = JoinClassByFile.of().joinClassByFile(Jigou.getInstance().getToken(), fileName, classId);
        SampleAssert.assertCode200(jsonObject);

        //班级列表
        JSONObject classList = ClassList.of().classList(Jigou.getInstance());
        SampleAssert.assertCode200(classList);
        JSONObject clazz = getClass(classList, className);

        assert studentCount == clazz.getInt("studentCount");

        //删除班级
        JSONObject delClass = DelClass.of().delClass(Jigou.getInstance().getToken(), classId);
        SampleAssert.assertCode200(delClass);
    }

    private JSONObject getClass(JSONObject object, String className) {
        for (JSONObject o : object.getJSONObject("data").getJSONArray("list").jsonIter()) {
            if (o.getStr("className").equals(className)) {
                return o;
            }
        }
        throw new RuntimeException("未搜索到班级");
    }


}
