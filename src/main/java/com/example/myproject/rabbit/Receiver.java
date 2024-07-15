package com.example.myproject.rabbit;

import java.util.concurrent.CountDownLatch;

import com.example.myproject.myBatis.Article;
import com.example.myproject.myBatis.ArticleMapper;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.util.SerializationUtils.deserialize;

@Component
public class Receiver {

    private final ArticleMapper articleMapper;
    private final CountDownLatch latch = new CountDownLatch(1);

    public Receiver(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring-boot", durable = "false"),
            exchange = @Exchange(value = "spring-boot-exchange", ignoreDeclarationExceptions = "true"),
            key = "foo.bar.#")
    )
    public void receiveMessage(byte[] body) {
        final Article object = (Article) deserialize(body);
        articleMapper.insert(object);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}