package com.example.moodify.services;

import com.example.moodify.dtos.PlaylistDto;
import com.example.moodify.entities.Playlist;
import com.example.moodify.entities.User;
import com.example.moodify.repositories.PlaylistRepository;
import com.example.moodify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PlaylistServiceImplementation implements PlaylistService {

    // You still need the UserRepository because you need to grab the user from the DB to add to their account.
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public void addPlaylist(PlaylistDto playlistDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        Playlist playlist = new Playlist(playlistDto);
        userOptional.ifPresent(playlist::setUser);
        playlistRepository.saveAndFlush(playlist);
    }
}
