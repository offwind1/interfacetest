package cn.vr168.interfacetest.parameter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.vr168.interfacetest.inter.mizhu.web.lesson.*;
import cn.vr168.interfacetest.inter.mizhu.web.classroom.Delete;
import cn.vr168.interfacetest.inter.mizhumanage.web.lesson.LessonReply;
import cn.vr168.interfacetest.parameter.people.Admin;
import cn.vr168.interfacetest.parameter.people.Jigou;
import cn.vr168.interfacetest.kit.util.GradeUtil;
import lombok.Getter;
import cn.vr168.interfacetest.kit.util.Body;
import cn.vr168.interfacetest.kit.util.SampleAssert;

import java.util.List;
import java.util.stream.Collectors;

public class Lesson {
    @Getter
    private String lessonId;
    private String token;
    private JSONObject data;

    public Lesson(String token, String lessonId) {
        this.token = token;
        this.lessonId = lessonId;
        updateLessonInfo();
    }


    /*
    ### setter
     */

    /**
     * 设置主讲老师
     *
     * @param userId
     */
    public void setTeacher(String userId) {
        for (Classroom classRoom : getClassrooms()) {
            classRoom.setTeacher(userId);
        }
        updateLessonInfo();
    }

    /*
    ### adder
     */

    /**
     * 添加班级
     */
    public void addClass(String stuId) {
        SampleAssert.assertCode200(StudentByClassId.of().studentByClassId(token, lessonId, stuId));
    }


    /*
    ### getter
     */

    /**
     * 获取课堂名称
     *
     * @return
     */
    public String getLessonName() {
        return data.getJSONObject("data").getJSONObject("lessonInfo").getStr("lessonName");
    }

    /**
     * 获取班级列表
     *
     * @return
     */
    public List<String> getClassList() {
        JSONObject object = GroupByClass.of().groupByClass(token, lessonId);
        return object.getJSONObject("data").getJSONArray("list").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            return o.getStr("stuId");
        }).collect(Collectors.toList());
    }

    /**
     * 获取课时
     *
     * @param index 下标
     * @return
     */
    public Classroom getClassRoom(int index) {
        if (index > data.getJSONObject("data").getJSONArray("classroomList").size()) {
            return null;
        }
        return new Classroom(token, data.getJSONObject("data").getJSONArray("classroomList").getJSONObject(index));
    }

    /**
     * 获取课时列表
     *
     * @return
     */
    public List<Classroom> getClassrooms() {
        return data.getJSONObject("data").getJSONArray("classroomList").stream().map(i -> {
            return new Classroom(token, (JSONObject) i);
        }).collect(Collectors.toList());
    }

    /**
     * 获取课时数量
     *
     * @return
     */
    public Integer getClassroomCount() {
        return getLessonInfo().getInt("classroomCount");
    }

    /*
    ### action
     */

    private JSONArray getClassroomList() {
        return data.getJSONObject("data").getJSONArray("classroomList");
    }

    private JSONObject getLessonInfo() {
        return data.getJSONObject("data").getJSONObject("lessonInfo");
    }

    /**
     * 删除课时
     *
     * @param index
     */
    public void deleteClassroom(Integer index) {
        Delete.of().delete(token, getClassRoom(index).getClassroomId());
        data.getJSONObject("data").getJSONArray("classroomList").remove(index);
    }

    public void deleteClassrooms(int fromIndex, int toIndex) {
        String classroomIds = getClassrooms().subList(fromIndex, toIndex).stream().map(Classroom::getClassroomId)
                .collect(Collectors.joining(","));
        System.out.println("classroomIds：" + classroomIds);
        SampleAssert.assertCode200(Delete.of().delete(token, classroomIds));
        updateLessonInfo();
    }

    /**
     * 通过班级ID添加学生
     *
     * @param stuId 班级id
     */
    public void addStudentByStuId(String stuId) {
        JSONObject object = StudentByClassId.of().studentByClassId(token, lessonId, stuId);
        SampleAssert.assertCode200(object);
    }


    /**
     * 提交审核
     */
    public void apply() {
        SampleAssert.assertCode200(Apply.of().apply(token, lessonId));
    }

    /**
     * 审核通过
     */
    public void applied() {
        apply();
        SampleAssert.assertCode200(LessonReply.of().lessonReplyPass(Admin.getInstance().getToken(), lessonId));
    }

    /**
     * 编辑课程
     *
     * @return 返回带有当前属性的builder
     */
    public LessonBuilder edit() {
        return new LessonBuilder(token, data);
    }

    /**
     * 获取builder
     *
     * @param token
     * @return
     */
    public static LessonBuilder builder(String token) {
        return new LessonBuilder(token);
    }

    public static LessonBuilder builder() {
        return new LessonBuilder(Jigou.getInstance().getToken());
    }

    /**
     * 通过lessonId创建lesson
     *
     * @param token
     * @param lessonId
     * @return
     */
    public static Lesson from(String token, String lessonId) {
        return new Lesson(token, lessonId);
    }

    /**
     * 更新课堂数据
     */
    private void updateLessonInfo() {
        JSONObject jsonObject = GetLessonInfoById.of().getLessonInfoById(token, lessonId);
        SampleAssert.assertCode200(jsonObject);
        data = jsonObject;
    }

    /**
     * 建造者
     */
    public static class LessonBuilder {
        private String gradeId = "7"; //年级Id
        private String lessonTypeId = "1"; //学科
        private DateTime startTime = DateUtil.date(); //开课时间
        private String lessonName = "新建课程" + RandomUtil.randomString(6); //课程名称
        private int classroomCount = 1;    //课节数
        private int classTime = 60;    //每节课时长
        private int classroomPrice = 0; //每节课时费用
        private String free = "1"; // 0免费 1收费
        private String buyType = "0"; // 1 赠一得一
        private String custRelease = "1";   //课程库
        private String token;
        private JSONObject data = null;

        /**
         * 默认数据的建造者
         * 新增课程时使用
         *
         * @param token
         */
        public LessonBuilder(String token) {
            this.token = token;
        }

        /**
         * 用当前的课程数据创建的建造者
         * 编辑课程时使用
         *
         * @param token
         * @param data
         */
        public LessonBuilder(String token, JSONObject data) {
            this.token = token;
            this.data = data;

            JSONObject object = data.getJSONObject("data").getJSONObject("lessonInfo");
            this.gradeId = object.getStr("gradeIds");
            this.lessonTypeId = object.getStr("lessonTypeId");
            this.startTime = DateTime.of(object.getStr("startTime"), "yyyy-MM-dd hh:mm:ss");
            this.lessonName = object.getStr("lessonName");
            this.classroomCount = object.getInt("classroomCount");
            this.classTime = object.getInt("classTime");
            this.classroomPrice = object.getInt("classroomPrice");
            this.buyType = object.getStr("buyType");
            this.custRelease = object.getStr("custRelease");
        }

        public LessonBuilder gradeId(String gradeId) {
            this.gradeId = gradeId;
            return this;
        }

        public LessonBuilder lessonTypeId(String lessonTypeId) {
            this.lessonTypeId = lessonTypeId;
            return this;
        }

        public LessonBuilder startTime(DateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public LessonBuilder startTime(String startTime) {
            this.startTime = DateTime.of(startTime, "yyyy-MM-dd hh:mm:ss");
            return this;
        }

        public LessonBuilder lessonName(String lessonName) {
            this.lessonName = lessonName;
            return this;
        }

        public LessonBuilder classroomCount(String classroomCount) {
            this.classroomCount = Integer.parseInt(classroomCount);
            return this;
        }

        public LessonBuilder classroomCount(int classroomCount) {
            this.classroomCount = classroomCount;
            return this;
        }

        public LessonBuilder classTime(int classTime) {
            this.classTime = classTime;
            return this;
        }

        public LessonBuilder classroomPrice(int classroomPrice) {
            this.classroomPrice = classroomPrice;
            return this;
        }

        public LessonBuilder free(String free) {
            this.free = free;
            return this;
        }

        public LessonBuilder buyType(String buyType) {
            this.buyType = buyType;
            return this;
        }

        public LessonBuilder token(String token) {
            this.token = token;
            return this;
        }

        public LessonBuilder custRelease(String custRelease) {
            this.custRelease = custRelease;
            return this;
        }

        private int calculateDiscount() {
            return ((classTime / 6) + classroomPrice) * classroomCount;
        }

        /**
         * 构建
         *
         * @return
         */
        public Lesson build() {
            JSONObject object;
            String lessonId;
            if (data == null) {
                object = add();
                SampleAssert.assertCode200(object);
                lessonId = object.getJSONObject("data").getStr("lessonId");
            } else {
                object = edit();
                SampleAssert.assertCode200(object);
                lessonId = data.getJSONObject("data")
                        .getJSONObject("lessonInfo")
                        .getStr("lessonId");
            }

            return new Lesson(token, lessonId);
        }

        /**
         * 调用编辑课程接口
         *
         * @return
         */
        private JSONObject edit() {
            String lessonId = data.getJSONObject("data")
                    .getJSONObject("lessonInfo")
                    .getStr("lessonId");
            JSONArray array = new JSONArray();
            int size = data.getJSONObject("data").getJSONArray("classroomList").size();

            for (int i = 0; i < classroomCount; i++) {
                JSONObject object = new JSONObject();
                object.put("interaction", 4);
                object.put("startTime", DateUtil.offsetDay(startTime, i).toString());

                if (i < size) {
                    object.put("classroomId", data.getJSONObject("data")
                            .getJSONArray("classroomList")
                            .getJSONObject(i).getStr("classroomId"));
                } else {
                    object.put("classroomId", 1);
                }
                array.add(object);
            }

            return Edit.of().edit(Edit.Bean.builder()
                    .token(token)
                    .lessonId(lessonId)
                    .lessonTypeId(lessonTypeId)
                    .gradeIds(gradeId)
                    .gradeNames(GradeUtil.getGradeNames(gradeId))
                    .startTime(startTime.toString())
                    .endTime(DateUtil.offsetDay(startTime, classroomCount).toString())
                    .tryLook("0")
                    .apply("0")
                    .lessonTerm("1")
                    .lessonName(lessonName)
                    .faceImg("https://imagess.mizholdings.com/ad/ad2.png")
                    .classroomCount(String.valueOf(classroomCount))
                    .classTime(String.valueOf(classTime))
                    .classroomPrice(String.valueOf(classroomPrice))
                    .discount(String.valueOf(calculateDiscount()))
                    .free(free)
                    .lessRemark("<p>8橙云课，提高学习效率8成！现在就加入此课程吧！</p>")
                    .lessonTag("")
                    .studentCount("200")
                    .price("0")
                    .buyType(buyType)
                    .custRelease(custRelease)
                    .classInfo(array.toString())
                    .build());
        }

        /**
         * 调用新增课程接口
         *
         * @return
         */
        private JSONObject add() {
            JSONArray array = new JSONArray();
            for (int i = 0; i < classroomCount; i++) {
                JSONObject object = new JSONObject();
                object.put("interaction", 4);
                object.put("startTime", DateUtil.offsetDay(startTime, i).toString());
                object.put("classroomId", 1);
                array.add(object);
            }

            JSONObject jsonObject = Add.of().add(Body.create()
                    .add("token", token)
                    .add("lessonTypeId", lessonTypeId)
                    .add("gradeIds", gradeId)
                    .add("gradeNames", GradeUtil.getGradeNames(gradeId))
                    .add("startTime", startTime.toString())
                    .add("endTime", DateUtil.offsetDay(startTime, classroomCount).toString())
                    .add("tryLook", "0")
                    .add("apply", "0")
                    .add("lessonTerm", "1")
                    .add("lessonName", lessonName)
                    .add("faceImg", "https://imagess.mizholdings.com/ad/ad2.png")
                    .add("classroomCount", classroomCount)
                    .add("classTime", classTime)
                    .add("classroomPrice", classroomPrice)
                    .add("discount", calculateDiscount())
                    .add("free", free)
                    .add("lessRemark", "<p>8橙云课，提高学习效率8成！现在就加入此课程吧！</p>")
                    .add("lessonTag", "")
                    .add("studentCount", "200")
                    .add("price", "0")
                    .add("buyType", buyType)
                    .add("custRelease", custRelease)
                    .add("classInfo", array.toString())
                    .build());
            return jsonObject;
        }
    }
}
