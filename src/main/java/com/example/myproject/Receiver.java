package com.example.myproject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
public class Receiver {
    @Autowired
    ArticleMapper articleMapper;
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] body) throws UnsupportedEncodingException {

        Article object = (Article) SerializationUtils.deserialize(body);
        articleMapper.insert(object);

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}