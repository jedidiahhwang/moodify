package com.example.moodify.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class Config {
    /*
        Per https://stackoverflow.com/questions/3295496/what-is-a-javabean-exactly...

        A Java bean is simply a standard of a class. These class standards have...
            - Private getters and setters (all properties).
            - Public no-argument constructor (as seen below).
            - Implements Serializable.
                i. Allows class to be converted to bytestream.

        The following bean now contains passwordEncoder
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
