package com.example.moodify.controllers;

import com.example.moodify.dtos.PlaylistDto;
import com.example.moodify.dtos.UserDto;
import com.example.moodify.services.PlaylistService;
import com.example.moodify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/addPlaylist")
    public void addPlaylist(@RequestBody PlaylistDto playlistDto, @RequestParam Long userId) {
        playlistService.addPlaylist(playlistDto, userId);
    }
}
