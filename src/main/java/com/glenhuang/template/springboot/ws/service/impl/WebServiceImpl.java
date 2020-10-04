package com.glenhuang.template.springboot.ws.service.impl;

import com.glenhuang.template.springboot.ws.entity.Users;
import com.glenhuang.template.springboot.ws.mapper.mysql.MySqlDaoMapper;
import com.glenhuang.template.springboot.ws.mapper.oracle.OracleDaoMapper;
import com.glenhuang.template.springboot.ws.service.WebService;
import com.glenhuang.template.springboot.ws.util.RedisUtil;
import com.mongodb.internal.build.MongoDriverVersion;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;

@Component
public class WebServiceImpl implements WebService {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    MySqlDaoMapper mySqlDaoMapper;

    @Autowired
    OracleDaoMapper oracleDaoMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${springdoc.swagger-ui.path}")
    private String swaggerUiPath;

    @Value("${common.app.url}")
    private String commonAppUrl;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * @return redirect to swagger-ui page
     * @throws Exception if the ws is not available
     */
    @Override
    public ModelAndView homeToSwagger() throws Exception {

        log.info("homeToSwagger() is called. ");

        return new ModelAndView(swaggerUiPath);
    }

    /**
     * @return basic info
     * @throws Exception if the ws is not available
     */
    @Override
    public String hello() throws Exception {

        log.info("hello() is called. ");

        log.info("abc-info: " + commonAppUrl + appName);
        log.debug("abc-debug: " + commonAppUrl + appName);
        log.warn("abc-warn: " + commonAppUrl + appName);
        log.error("abc-error: " + commonAppUrl + appName);

        return "abc abc, " + commonAppUrl + ", " + appName;

    }

    /**
     * @return basic info
     * @throws Exception if the ws is not available
     */
    @Override
    public ResponseEntity getMySqlVersion() throws Exception {

        log.info("getMySqlVersion() is called. ");

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("mysqlVersion", mySqlDaoMapper.getMySqlVersion());

        return ResponseEntity.ok(responseBody);

    }

    /**
     * @return basic info
     * @throws Exception if the ws is not available
     */
    @Override
    public ResponseEntity getOracleVersion() throws Exception {

        log.info("getOracleVersion() is called. ");

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("oracleVersion", oracleDaoMapper.getOracleVersion());

        return ResponseEntity.ok(responseBody);

    }

    /**
     * @return basic info
     * @throws Exception if the ws is not available
     */
    @Override
    public ResponseEntity getRedis() throws Exception {

        log.info("getRedis() is called. ");

        HashMap<String, Object> responseBody = new HashMap<>();
        redisUtil.set("test_key", "test_value");
        responseBody.put("redis-test_key", redisUtil.get("test_key"));

        return ResponseEntity.ok(responseBody);

    }

    /**
     * @return basic info
     * @throws Exception if the ws is not available
     */
    @Override
    public ResponseEntity getMongodb() throws Exception {

        log.info("getMongodb() is called. ");

        Query query = Query.query(Criteria.where("firstname").is("Jon"));
        Users user = mongoTemplate.findOne(query, Users.class);

        //find() return all the eligible users
//        List<Users> userList = mongoTemplate.find(query, Users.class, "test");

        //findAll() return all the users from this collection
        List<Users> userList = mongoTemplate.findAll(Users.class, "test");

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("user", user);
        responseBody.put("userList", userList);

        return ResponseEntity.ok(responseBody);

    }

    /**
     * @return basic info
     * @throws Exception if the ws is not available
     */
    @Override
    public ResponseEntity getAPI() throws Exception {

        log.info("getAPI() is called. ");

        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.APPLICATION_JSON_UTF8;
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> headerEntity = new HttpEntity<>(headers);

            String apiURL = "http://localhost:8080/springboot/getMySqlVersion";

//            ResponseEntity<String> apiResponse = restTemplate.getForEntity(apiURL, String.class);
            ResponseEntity<String> apiResponse2 = restTemplate.exchange(apiURL, HttpMethod.GET, headerEntity, String.class);

            HashMap<String, Object> responseBody = new HashMap<>();
            responseBody.put("apiResponse", apiResponse2.getBody());

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            log.error("Exception={}", e.getMessage());
            throw new Exception("Internal Server Error.");
        }

    }

    /**
     * @return basic info
     * @throws Exception if the ws is not available
     */
    @Override
    public ResponseEntity getRabbitMQSend() throws Exception {

        log.info("getRabbitMQSend() is called. ");

        for (int i =0; i< 100; i++) {
            String msg = "hello, no: " + i;
            System.out.println("Producer, " + msg);
            rabbitTemplate.convertAndSend("queue-test", msg);
        }

        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");

        return ResponseEntity.ok(responseBody);

    }

}
