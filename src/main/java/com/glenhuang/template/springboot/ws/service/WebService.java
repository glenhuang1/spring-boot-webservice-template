package com.glenhuang.template.springboot.ws.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface WebService {

    ModelAndView homeToSwagger() throws Exception;

    String hello() throws Exception;

    ResponseEntity getMySqlVersion() throws Exception;

    ResponseEntity getOracleVersion() throws Exception;

    ResponseEntity getRedis() throws Exception;

    ResponseEntity getMongodb() throws Exception;

    ResponseEntity getAPI() throws Exception;

    ResponseEntity getRabbitMQSend() throws Exception;

}
