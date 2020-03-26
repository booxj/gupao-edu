package com.booxj.gp.middleware.mongodb.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author: wangbo@myhexin.com
 * @create: 2020/3/26 10:17
 */
@Data
@Document("t_user")
public class User {

    @Id
    @Field("uid")
    private long id;

    @Field("name")
    private String name;

    @Field("age")
    private String age;

    @Field("favorites")
    private List<String> favorites;

    @Field("work_now")
    private Company workNow;

    @Field("work_history")
    private List<Company> workHistory;

}
