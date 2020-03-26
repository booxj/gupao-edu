package com.booxj.gp.middleware.mongodb.dao;

import com.booxj.gp.middleware.mongodb.dto.Company;
import com.booxj.gp.middleware.mongodb.dto.User;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: wangbo@myhexin.com
 * @create: 2020/3/26 10:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationTest {

    @Autowired
    private BasicOperation basicOperation;

    @Autowired
    private HighOperation highOperation;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void initBD() {
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            companies.add(new Company((long) i, "公司名称" + i, "公司地址" + i));
        }

//        mongoTemplate.insertAll(companies);

        User user = new User();
        user.setId(1);
        user.setName("booxj");
        user.setAge("18");
        user.setFavorites(Arrays.asList("吃", "喝", "嫖", "赌"));
        user.setWorkHistory(companies);

        mongoTemplate.save(user);

//        user.setWorkNow(companies.get(9));
//        user.setWorkHistory(companies);

//        user.setUid(2);
//        mongoTemplate.insert(user);
//        user.setUid(3);
//        user.setName("狐朋狗友2");
//        mongoTemplate.insert(user);
//        user.setUid(4);
//        user.setName("狐朋狗友3");
//        mongoTemplate.insert(user);
    }


    @Test
    public void in操作() {

        TypedAggregation aggregation = Aggregation.newAggregation(User.class,
                Aggregation.match(Criteria.where("_id").all(Arrays.asList(2, 3, 4))));

        Query query = new Query();
        query.addCriteria((Criteria.where("_id").in(Arrays.asList(2, 3, 4))));
        mongoTemplate.find(query, User.class);
//        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(aggregation, "t_user", BasicDBObject.class);
    }

    @Test
    public void list增删改() {
        Company company = new Company((long) 100, "公司名称100", "公司地址100");

        //增
        Query query1 = Query.query(Criteria.where("id").is(1).and("work_history._id").is(company.getId()));
        Update update1 = new Update();
        update1.addToSet("work_history", company);
        mongoTemplate.upsert(query1, update1, User.class);

        // 改
        Query query2 = Query.query(Criteria.where("_id").is(1).and("work_history._id").is(company.getId()));
        Update update2 = new Update();
        update2.set("work_history.$.name", "xoxoxoxxo");
        mongoTemplate.updateFirst(query2, update2, User.class);

        // 删
//        Query query3 = Query.query(Criteria.where("id").is(1).and("work_history._id").is(company.getId()));
//        Update update3 = new Update();
//        update3.unset("work_history.$");
//        mongoTemplate.updateFirst(query2, update2, User.class);

    }


    /**
     * db.getCollection('t_user').explain("executionStats").aggregate([
     * {$match:{"_id":1}},
     * {$unwind:"$work_history"},
     * {$match:{"work_history._id":{$lt:1000000}}},
     * {$sort:{"work_history._id":-1}},
     * {$limit:10}
     * ])
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void list查询操作() {
        TypedAggregation aggregation = Aggregation.newAggregation(User.class,
                Aggregation.match(Criteria.where("id").is(1)),
                Aggregation.unwind("workHistory"),
                Aggregation.match(Criteria.where("workHistory.id").lt(66)),
                Aggregation.sort(Sort.Direction.DESC, "workHistory.id"),
                Aggregation.limit(10)
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, Document.class);

        List<Company> companyList = new ArrayList<>();
        results.forEach(r -> {
            long id = r.get("work_history",Document.class).getLong("_id");
            String  name = r.get("work_history",Document.class).getString("name");
            String address = r.get("work_history",Document.class).getString("address");
            Company company = new Company(id,name,address);
            companyList.add(company);
        });

        System.out.println(companyList);
    }

}
