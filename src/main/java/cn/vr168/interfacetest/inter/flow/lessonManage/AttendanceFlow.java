package cn.vr168.interfacetest.inter.flow.lessonManage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.api.classChart.Export;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.ClassroomCodeAddUser;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.ClassroomEnd;
import cn.vr168.interfacetest.inter.mizhu.api.classInfo.ClassroomStart;
import cn.vr168.interfacetest.inter.mizhu.web.grade.AddClass;
import cn.vr168.interfacetest.inter.mizhu.web.grade.DelClass;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.JoinClassByFile;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.UploadFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import cn.vr168.interfacetest.parameter.Lesson;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.parameter.people.Student;
import cn.vr168.interfacetest.parameter.people.User;
import cn.vr168.interfacetest.kit.util.SampleAssert;
import cn.vr168.interfacetest.kit.excelCreator.StudentExcelCreator;

import java.io.IOException;
import java.util.*;

/**
 * 考勤相关测试
 * 超简单版本
 */
public class AttendanceFlow {

    private User user = Jigou.getInstance();
    private Lesson lesson = Lesson.builder(user.getToken()).classroomCount(3).build();
    private String className = "班级" + RandomUtil.randomString(6);
    private String nameFormat = "新生娃%04d";
    private String accountFormat = "baby%04d";
    private String password = "111111";
    private int count = 5;
    private String classroomVideoId;
    private String teacherCloudeAccount;
    private String classId = creatClass();
    private String classroomCode;

    private void init() throws InterruptedException {
        addStudent();

        //创建课程
        lesson.setTeacher(user.getUserId());//设置教师
        lesson.applied(); // 审核
        lesson.addClass(classId); // 添加班级
    }

    private String creatClass() {
        JSONObject addClass = AddClass.of().addClass(user, className, "1");
        SampleAssert.assertCode200(addClass);
        return addClass.getJSONObject("data").getStr("classId");
    }

    private void addStudent() throws InterruptedException {
        //批量添加学生
        //上传文件
        StudentExcelCreator excelCreator = StudentExcelCreator.of();
        excelCreator = excelCreator.addStudent(nameFormat, accountFormat, password, count);
        JSONObject uploadFile = UploadFile.of().uploadFile(user.getToken(), excelCreator.build());
        SampleAssert.assertCode200(uploadFile);
        String fileName = uploadFile.getJSONObject("data").getStr("fileName");

        Thread.sleep(1000);
        //通过文件，添加学生
        JSONObject jsonObject = JoinClassByFile.of().joinClassByFile(user.getToken(), fileName, classId);
        SampleAssert.assertCode200(jsonObject);
    }

    private String startClass() {
        JSONObject classroomStart = ClassroomStart.of().classroomStart(user.getToken(), lesson.getClassRoom(0).getClassroomId());
        classroomVideoId = classroomStart.getJSONObject("data").getStr("classroomVideoId");
        teacherCloudeAccount = classroomStart.getJSONObject("data").getJSONObject("classroomInfo").getStr("teacherCloudeAccount");
        SampleAssert.assertMsg(classroomStart, "开课成功");
        return classroomStart.getJSONObject("data").getJSONObject("classroomInfo").getStr("classroomCode");
    }


    @AfterClass
    public void afterClass() {
        JSONObject object = DelClass.of().delClass(user.getToken(), classId);
        SampleAssert.assertCode200(object);
    }


    /**
     * 开课
     * 学生陆续进入
     * 结束课程
     * 导出考勤表
     * 验证考勤表
     */
    @Test
    public void test() {
        try {
            init();
            classroomCode = startClass();

            for (int i = 1; i <= count - 1; i++) {
                Student student = new Student(String.format(accountFormat, i), password);
                ClassroomCodeAddUser.of().classroomCodeAddUser(student, classroomCode);
            }

            Thread.sleep(2000);

            JSONObject end = ClassroomEnd.of().classroomEnd(Jigou.getInstance().getToken(), classroomVideoId, teacherCloudeAccount);
            SampleAssert.assertResult0(end);

            HttpResponse response = Export.of().export(user.getToken(), lesson.getClassRoom(0).getClassroomId());
            HSSFWorkbook workbook = new HSSFWorkbook(response.bodyStream());
//
//            ExcelCreator.saveToFile("D:/111.xlsx", workbook);

            checkSheetName(workbook);
            checkTitle(workbook.getSheetAt(0));
            checkClassStatistics(workbook.getSheetAt(0));

            checkClass(workbook.getSheetAt(1));
        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 检查 表名
     */
    private void checkSheetName(HSSFWorkbook workbook) {
        String lessonName = lesson.getLessonName();
        String dataTime = DateUtil.date().toString("yyyyMMdd");
        assert workbook.getSheetName(0).equals(dataTime + "-" + lessonName) : "SheetName" + workbook.getSheetName(0);
        assert workbook.getSheetName(1).equals(user.getName() + "-" + className + classId) :
                workbook.getSheetName(1) + "|" + user.getName() + "-" + className;
    }

    /**
     * 标头
     *
     * @param sheet
     */
    private void checkTitle(HSSFSheet sheet) {
        //课程名称：
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(1);
        assert cell.getStringCellValue().equals(lesson.getLessonName()) : "课程名称:" + cell.getStringCellValue();

        //课节名：
        cell = row.getCell(3);
        assert cell.getStringCellValue().equals(lesson.getClassRoom(0).getClassroomName()) :
                "课节名" + cell.getStringCellValue();
        //时间：
        //时长：
        //课堂代码：
        cell = row.getCell(9);
        assert cell.getStringCellValue().equals(classroomCode) : "课堂代码:" + cell.getStringCellValue();

        row = sheet.getRow(1);
        //应到人数：
        cell = row.getCell(1);
        assert cell.getNumericCellValue() == count : "应到人数:" + cell.getNumericCellValue();
        //实到人数：
        cell = row.getCell(3);
        assert cell.getNumericCellValue() == count - 1 : "实到人数:" + cell.getNumericCellValue();
        //缺勤：
        cell = row.getCell(5);
        assert cell.getNumericCellValue() == 1 : "缺勤：" + cell.getNumericCellValue();
        //迟到：
        cell = row.getCell(7);
        assert cell.getNumericCellValue() == 0 : "迟到：" + cell.getNumericCellValue();
        //早退
        cell = row.getCell(9);
        assert cell.getNumericCellValue() == count - 1 : "早退：" + cell.getNumericCellValue();
    }

    /**
     * 各班级统计表:
     */
    private void checkClassStatistics(HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(9);
        checkClassStudent(row);
    }

    private void checkClassStudent(HSSFRow row) {
        HSSFCell cell = row.getCell(0);
        //机构
        assert cell.getStringCellValue().equals(user.getOrgName()) : "机构：" + cell.getStringCellValue();
        //班级
        cell = row.getCell(1);
        assert cell.getStringCellValue().equals("一年级 " + className) : "班级：" + cell.getStringCellValue();
        //应到人数
        cell = row.getCell(2);
        assert cell.getNumericCellValue() == count : "应到人数：" + cell.getNumericCellValue();
        //实到人数
        cell = row.getCell(3);
        assert cell.getNumericCellValue() == count - 1 : "实到人数：" + cell.getNumericCellValue();
        //缺勤人数
        cell = row.getCell(4);
        assert cell.getNumericCellValue() == 1 : "缺勤人数：" + cell.getNumericCellValue();
        //迟到人数
        cell = row.getCell(5);
        assert cell.getNumericCellValue() == 0 : "迟到人数：" + cell.getNumericCellValue();
        //早退人数
        cell = row.getCell(6);
        assert cell.getNumericCellValue() == count - 1 : "早退人数：" + cell.getNumericCellValue();
    }

    /**
     * 检查班级数据
     *
     * @param sheet
     */
    private void checkClass(HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(3);
        checkClassStudent(row);

        Map<String, List<String>> map = new HashMap<>();

        for (int i = 5; i <= sheet.getLastRowNum(); i++) {
            List<String> list = new ArrayList<>();
            row = sheet.getRow(i);
            String name = row.getCell(1).getStringCellValue();
            String account = row.getCell(2).getStringCellValue();
            String time = row.getCell(4).getStringCellValue();
            String timeLong = row.getCell(5).getStringCellValue();
            String isCome = row.getCell(6).getStringCellValue();
            String isLast = row.getCell(8).getStringCellValue();
            String isFirst = row.getCell(9).getStringCellValue();

            list.add(name);
            list.add(time);
            list.add(timeLong);
            list.add(isCome);
            list.add(isLast);
            list.add(isFirst);

            map.put(account, list);
            System.out.println(account);
        }

        List<String> accounts = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.println(String.format(accountFormat, i));
            accounts.add(String.format(accountFormat, i));
        }

        assert map.keySet().equals(new HashSet<>(accounts)) : "班级账号列表不正确";

        for (int i = 1; i <= count; i++) {
            String account = String.format(accountFormat, i);
            List<String> list = map.get(account);
            if (i <= 4) {
                for (int j = 0; j < list.size(); j++) {
                    if (j == 4) {
                        assert ObjectUtil.isEmpty(list.get(j)) : "isNotEmpty " + list.get(j);
                    } else {
                        assert ObjectUtil.isNotEmpty(list.get(j)) : "isEmpty:" + j;
                    }
                }
            } else {
                for (int j = 0; j < list.size(); j++) {
                    if (j == 0) {
                        assert ObjectUtil.isNotEmpty(list.get(j)) : "isEmpty";
                    } else {
                        assert ObjectUtil.isEmpty(list.get(j)) : "isNotEmpty";
                    }
                }
            }
        }
    }

}
