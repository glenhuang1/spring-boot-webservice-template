package com.glenhuang.template.springboot.ws.controller;

import com.glenhuang.template.springboot.ws.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @Autowired
    WebService webService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView homeToSwagger() throws Exception {
        return webService.homeToSwagger();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String hello() throws Exception {
        return webService.hello();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getMySqlVersion")
    public ResponseEntity getMySqlVersion() throws Exception {
        return webService.getMySqlVersion();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getOracleVersion")
    public ResponseEntity getOracleVersion() throws Exception {
        return webService.getOracleVersion();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getRedis")
    public ResponseEntity getRedis() throws Exception {
        return webService.getRedis();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getMongodb")
    public ResponseEntity getMongodb() throws Exception {
        return webService.getMongodb();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAPI")
    public ResponseEntity getAPI() throws Exception {
        return webService.getAPI();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getRabbitMQSend")
    public ResponseEntity getRabbitMQSend() throws Exception {
        return webService.getRabbitMQSend();
    }

}
