package com.booxj.gp.middleware.mongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * mongodb 高级操作
 * aggregate mapReduce lookup unwind group bucket match
 * @author: wangbo@myhexin.com
 * @create: 2020/3/26 10:12
 */
@Repository
public class HighOperation {

    @Autowired
    private MongoTemplate mongoTemplate;
}
