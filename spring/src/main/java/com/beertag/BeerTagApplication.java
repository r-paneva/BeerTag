package com.beertag;


import com.beertag.models.*;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeerTagApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerTagApplication.class, args);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Beer.class)
                .addAnnotatedClass(RatingVote.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Style.class)
                .addAnnotatedClass(Tag.class)
                .buildSessionFactory();
    }
}
