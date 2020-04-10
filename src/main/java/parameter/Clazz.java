package parameter;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import inter.mizhu.web.grade.AddClass;
import inter.mizhu.web.grade.ClassList;
import inter.mizhu.web.grade.DelClass;
import inter.mizhu.web.grade.UserByStuId;
import inter.mizhu.web.lesson.JoinClassByFile;
import inter.mizhu.web.lesson.UploadFile;
import lombok.Data;
import parameter.people.Jigou;
import util.SampleAssert;
import util.StudentExcelCreator;

import java.util.List;

@Data
public class Clazz {
    private String stuId;
    private String className;
    private String gradeId;

    public Clazz(String className, String gradeId) {
        this.className = className;
        this.gradeId = gradeId;
        JSONObject addClass = AddClass.of().addClass(Jigou.getInstance(),
                className, "1");
        SampleAssert.assertCode200(addClass);
        this.stuId = addClass.getJSONObject("data").getStr("classId");
    }

    public Clazz(JSONObject object) {
        this.className = object.getStr("className");
        this.stuId = object.getStr("stuId");
        this.gradeId = object.getStr("gradeId");
    }

    /**
     * 通过模板导入学生到班级
     *
     * @param nameFormat    姓名模板
     * @param accountFormat 账号模板
     * @param count         账号数量
     */
    public void addStudentByExcel(String nameFormat, String accountFormat, int count) {
        StudentExcelCreator excelCreator = StudentExcelCreator.of();
        excelCreator = excelCreator.addStudent(nameFormat, accountFormat, "111111", count);
        JSONObject uploadFile = UploadFile.of().uploadFile(Jigou.getInstance().getToken(), excelCreator.build());
        SampleAssert.assertCode200(uploadFile);
        String fileName = uploadFile.getJSONObject("data").getStr("fileName");
        //通过文件，添加学生
        JSONObject jsonObject = JoinClassByFile.of().joinClassByFile(Jigou.getInstance().getToken(), fileName, stuId);
        SampleAssert.assertCode200(jsonObject);
    }

    /**
     * 获取学生
     *
     * @param count 数量
     * @return JSONArray
     */
    public JSONArray getStudents(int count) {
        JSONObject object = UserByStuId.of().userByStuId(Jigou.getInstance().getToken(), stuId, count);
        SampleAssert.assertCode200(object);
        return object.getJSONObject("data").getJSONArray("list");
    }

    /**
     * 获取学生 最大100
     *
     * @return JSONArray
     */
    public JSONArray getStudents() {
        return getStudents(100);
    }

    /**
     * 获取学生数量
     *
     * @return
     */
    public int studentCount() {
        JSONObject object = ClassList.of().classList(Jigou.getInstance());
        for (JSONObject o : object.getJSONObject("data").getJSONArray("list").jsonIter()) {
            if (o.getStr("className").equals(className)) {
                return o.getInt("studentCount");
            }
        }
        return 0;
    }

    /**
     * 删除班级
     */
    public void delClass() {
        JSONObject object = DelClass.of().delClass(Jigou.getInstance().getToken(), stuId);
        SampleAssert.assertCode200(object);
    }

}
