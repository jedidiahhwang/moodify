package com.example.moodify.services;

import com.example.moodify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation {
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


}
