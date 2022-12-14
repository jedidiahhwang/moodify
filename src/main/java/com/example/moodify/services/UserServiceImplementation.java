package com.example.moodify.services;

import com.example.moodify.dtos.UserDto;
import com.example.moodify.entities.User;
import com.example.moodify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    /*
        What is some business logic that you'd see for users of this application?

        1) Users can be created.
        2) Users can login.
        3) Users can select moods and genres.
        4) Users can make playlists.
        5) Users can add music to their playlists.
    */

    // Review @Autowired more in the future.
    @Autowired // DI from UserRepository, automatically finds corresponding dependency.
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
        JPA does not have declarative transaction management. If you're using JPA outside of a DI container,
        you need to handle the transactions explicitly.

        With @Transactional, that handling is taken care of for you, so you only need to implement the
        business logic.

    */
    @Override
    @Transactional // Handles the persistence context and database transaction all together.
    public List<String> addUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        System.out.println("-------userDto-------");
        System.out.println(userDto);
        System.out.println("-------user-------");
        System.out.println(user);
        // saveAndFlush() is from Spring Data JPA and will "save and flush" your data to the DB.
        // Refer to the following link: https://stackoverflow.com/questions/21203875/difference-between-save-and-saveandflush-in-spring-data-jpa
        userRepository.saveAndFlush(user);

        response.add(user.getActualName());
        response.add(user.getUsername());
        response.add(user.getEmail());
        response.add(user.getAccountUrl());
        response.add(user.getImageUrl());
        response.add(Long.toString(user.getId()));
        response.add("User added successfully");
        return response;
    }


    @Override
    public List<String> userLogin(UserDto userDto) {
        List<String> response = new ArrayList<>();

        // Optionals help us avoid NullPointerExceptions. Optionals give the compiler a choice to accept it whether it's empty or not.
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if(userOptional.isPresent()) {
//            response.add("User logging in");
            User user = userOptional.get();
            response.add(user.getActualName());
            response.add(user.getUsername());
            response.add(user.getEmail());
            response.add(user.getAccountUrl());
            response.add(user.getImageUrl());
            response.add(Long.toString(user.getId()));
            response.add("User logged in successfully");
        } else {
            response.add("User not found");
        }
        return response;
    }
}
