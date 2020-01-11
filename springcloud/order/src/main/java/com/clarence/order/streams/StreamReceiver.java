package com.clarence.order.streams;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;


/**
 * 接收端
 */
@Component
@EnableBinding(StreamClient.class)
public class StreamReceiver {
    @StreamListener("myMessage")
    public void process(Object message) {
        System.out.println(message);
    }
}
