package com.booxj.gp.pattern.template;


import com.booxj.gp.pattern.template.dao.UserMapper;

public class UserMapperTest {

    private static String sql = "select * from user where age=?";

    public static void main(String[] args) throws Exception {
        UserMapper userMapper = new UserMapper();
        Object[] objects = new Object[]{11};
        System.out.println(userMapper.query(sql, objects));
    }
}
