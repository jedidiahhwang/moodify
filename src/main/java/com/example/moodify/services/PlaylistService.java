package com.example.moodify.services;

import com.example.moodify.dtos.PlaylistDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    List<PlaylistDto> getAllPlaylistsByUserId(Long userId);

    void addPlaylist(PlaylistDto playlistDto, Long userId);

//    Optional<PlaylistDto> getPlaylistById(Long playlistId);
}
