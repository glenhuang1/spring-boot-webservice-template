package com.glenhuang.template.springboot.ws.util;

import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;

@Component
public class RabbitMQReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) throws Exception {

        System.out.println("Received <" + message + ">");

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
