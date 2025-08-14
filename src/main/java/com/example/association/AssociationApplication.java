package com.example.association;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AssociationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssociationApplication.class, args);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches("admin123", "$2b$12$Luxe68w6O2nuQb8Jq9YD9uVB6MkBk8b3RJ2eBHh0HmdOBNBvQmBPe"));
    }

}

