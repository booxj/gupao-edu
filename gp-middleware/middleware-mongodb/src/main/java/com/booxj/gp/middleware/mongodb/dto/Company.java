package com.booxj.gp.middleware.mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: wangbo@myhexin.com
 * @create: 2020/3/26 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @Field("id")
    private Long id;

    @Field("name")
    private String name;

    @Field("address")
    private String address;


}
