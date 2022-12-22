package com.example.moodify.controllers;

import com.example.moodify.dtos.PlaylistDto;
import com.example.moodify.dtos.UserDto;
import com.example.moodify.services.PlaylistService;
import com.example.moodify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/getPlaylists/{userId}")
    private List<PlaylistDto> getPlaylistByUser(@PathVariable Long userId) {
        System.out.println("getPlaylistByUser endpoint hit");
        return playlistService.getAllPlaylistsByUserId(userId);
    }

    @PostMapping("/addPlaylist")
    public List<PlaylistDto> addPlaylist(@RequestBody PlaylistDto playlistDto, @RequestParam("userId") Long userId, @RequestParam("moodId") Long moodId, @RequestParam("genreId") Long genreId) {
        System.out.println("------userId-------");
        System.out.println(userId);
        System.out.println("------moodId-------");
        System.out.println(moodId);
        System.out.println("-----playlistDto------");
        System.out.println(playlistDto);
        playlistService.addPlaylist(playlistDto, userId, moodId, genreId);
        return playlistService.getAllPlaylistsByUserId(userId);
    }
}
