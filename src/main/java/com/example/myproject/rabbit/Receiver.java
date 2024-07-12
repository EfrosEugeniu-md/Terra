package com.example.myproject.rabbit;

import java.util.concurrent.CountDownLatch;

import com.example.myproject.myBatis.Article;
import com.example.myproject.myBatis.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.util.SerializationUtils.deserialize;

@Component
public class Receiver {
    @Autowired
    ArticleMapper articleMapper;
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] body) {
        Article object = (Article) deserialize(body);
        articleMapper.insert(object);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}