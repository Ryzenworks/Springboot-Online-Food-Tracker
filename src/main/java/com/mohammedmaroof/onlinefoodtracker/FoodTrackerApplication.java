package com.mohammedmaroof.onlinefoodtracker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement //This prompts the spring boot to find @Transactional Annotation
@SpringBootApplication
public class FoodTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodTrackerApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager imp(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }


}