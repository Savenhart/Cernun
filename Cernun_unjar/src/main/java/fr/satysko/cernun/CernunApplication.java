package fr.satysko.cernun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CernunApplication {
    public static void main(String[] args) {
        SpringApplication.run(CernunApplication.class, args);
        System.out.println("coucou");
    }
}
