package com.booxj.gp.middleware.mongodb.dao;

import com.booxj.gp.middleware.mongodb.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mongodb 基础操作CRUD，排序，分页
 *
 * @author: booxj
 * @create: 2020/3/26 9:24
 */
@Repository
public class BasicOperation {

    @Autowired
    private MongoTemplate mongoTemplate;


    public User findOne() {
        Query query = new Query();
        return mongoTemplate.findOne(query, User.class);
    }

    public boolean exists() {
        Query query = new Query();
        return mongoTemplate.exists(query, User.class);
    }

    public long count() {
        Query query = new Query();
        return mongoTemplate.count(query, User.class);
    }

    public void insert(User user) {
        mongoTemplate.insert(user);
    }

    public void insertAll(List<User> userList) {
        mongoTemplate.insertAll(userList);
    }

    // 若新增数据的主键已经存在，则会对当前已经存在的数据进行修改操作
    public void save(User user) {
        mongoTemplate.save(user);
    }

    public void upsert() {
        Query query = new Query();
        Update update = new Update();
        mongoTemplate.upsert(query, update, User.class);
    }

    public void updateFirst() {
        Query query = new Query();
        Update update = new Update();
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void updateMulti() {
        Query query = new Query();
        Update update = new Update();
        mongoTemplate.updateMulti(query, update, User.class);
    }

    public void remove(User user) {
        mongoTemplate.remove(user);
    }

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }
}
