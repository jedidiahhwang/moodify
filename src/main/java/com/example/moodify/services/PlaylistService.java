package com.example.moodify.services;

import com.example.moodify.dtos.PlaylistDto;

public interface PlaylistService {
    default void addPlaylist(PlaylistDto playlistDto, Long userId) {

    }
}
