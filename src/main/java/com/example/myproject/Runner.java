package com.example.myproject;

import java.util.concurrent.TimeUnit;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.springframework.util.SerializationUtils.serialize;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    @Autowired
    ArticleMapper articleMapper;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        for (
                int i = 1; i <= 9; i++
        ) {
            Article article1 = new Article("title" + i, "author" + i);

            byte[] data = serialize(article1);
            rabbitTemplate.convertAndSend(MyProjectApplication.topicExchangeName, "foo.bar.baz", data);
        }

        receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);

        articleMapper.getArticle().forEach(System.out::println);
    }
}
