package com.example.moodify.services;

import com.example.moodify.dtos.PlaylistDto;
import com.example.moodify.entities.Genre;
import com.example.moodify.entities.Mood;
import com.example.moodify.entities.Playlist;
import com.example.moodify.entities.User;
import com.example.moodify.repositories.GenreRepository;
import com.example.moodify.repositories.MoodRepository;
import com.example.moodify.repositories.PlaylistRepository;
import com.example.moodify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImplementation implements PlaylistService {

    // You still need the UserRepository because you need to grab the user from the DB to add to their account.
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public List<PlaylistDto> getAllPlaylistsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            List<Playlist> playlistList = playlistRepository.findAllByUserEquals(userOptional.get());
            return playlistList.stream().map(playlist -> new PlaylistDto(playlist)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void addPlaylist(PlaylistDto playlistDto, Long userId, Long moodId, Long genreId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Mood> moodOptional = moodRepository.findById(moodId);
        Optional<Genre> genreOptional = genreRepository.findById(genreId);

        Playlist playlist = new Playlist(playlistDto);
        System.out.println(playlist);
        userOptional.ifPresent(playlist::setUser);
        moodOptional.ifPresent(playlist::setMood);
        genreOptional.ifPresent(playlist::setGenre);
        playlistRepository.saveAndFlush(playlist);

    }
}
