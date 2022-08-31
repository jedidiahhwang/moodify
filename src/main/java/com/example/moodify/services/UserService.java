package com.example.moodify.services;

import com.example.moodify.dtos.UserDto;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    /*
            JPA does not have declarative transaction management. If you're using JPA outside of a DI container,
            you need to handle the transactions explicitly.

            With @Transactional, that handling is taken care of for you, so you only need to implement the
            business logic.

        */
    @Transactional
    // Handles the persistence context and database transaction all together.
    List<String> addUser(UserDto userDto);

    List<String> userLogin(UserDto userDto);
}
