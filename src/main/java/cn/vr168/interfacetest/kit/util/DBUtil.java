package cn.vr168.interfacetest.kit.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import lombok.NonNull;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DBUtil {
    /**
     * Delete usr_user_info where phone = phone
     *
     * @param phone
     * @throws SQLException
     */
    public static void delUserWithPhone(@NonNull String phone) throws SQLException {
        delUser("phone", phone);
    }

    /**
     * Delete usr_user_info where account = account
     *
     * @param account
     * @throws SQLException
     */
    public static void delUserWithAccount(@NonNull String account) throws SQLException {
        delUser("account", account);
    }


    /**
     * Delete usr_user_info where key = value
     *
     * @param key
     * @param value
     * @throws SQLException
     */
    public static void delUser(@NonNull String key, @NonNull String value) throws SQLException {
        if (ObjectUtil.isEmpty(key) || ObjectUtil.isEmpty(value)) {
            throw new RuntimeException("参数为空");
        }
        Db.use().del(
                Entity.create("usr_user_info").set(key, value)//where条件
        );
    }

    /**
     * 搜索表 usr_user_info where key = value
     *
     * @param key   键
     * @param value 值
     * @return
     * @throws SQLException
     */
    public static List<Entity> selectUser(@NonNull String key, @NonNull String value) throws SQLException {
        List<Entity> list = Db.use().find(
                Entity.create("usr_user_info").set(key, value)
        );
        if (list.size() < 1) {
            throw new RuntimeException("未搜索到内容");
        }
        return list;
    }

    /**
     * 搜索表 usr_user_info where phone = phone
     *
     * @param phone 手机号
     * @return
     * @throws SQLException
     */
    public static Optional<Entity> selectUserWithPhone(@NonNull String phone) throws SQLException {
        List<Entity> list = selectUser("phone", phone);
        if (list.size() < 1) {
            throw new RuntimeException("未搜索到内容");
        }
        return Optional.of(list.get(0));
    }

    public static List<Entity> selectClassroomDiligent(String lessonId, String classroomId) throws SQLException {
        return Db.use().find(
                Entity.create("mzc_classroom_diligent")
                        .set("lesson_id", lessonId)
                        .set("classroom_id", classroomId));
    }

    public static void main(String[] args) throws SQLException {
//        System.out.println(DateTime.of("2020-03-20 15:30:00", "yyyy-MM-dd hh:mm:ss"));

        List<Entity> list = selectClassroomDiligent("96582a4623bb42c19c9552f0c54f9f8b", "de87591d5f0c497ba45ba8e2a4b1a9eb");

        for (Entity entity : list) {
            System.out.println(entity.getStr("user_id"));
        }
    }
}
