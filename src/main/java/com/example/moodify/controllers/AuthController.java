package com.example.moodify.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@RestController
@RequestMapping("/api/spotify")
public class AuthController {
    // Spotify Web Api Repository: https://github.com/spotify-web-api-java/spotify-web-api-java
    // Spotify Authentication Tutorial: https://bret-gibson.medium.com/spotify-api-authentication-with-spring-boot-and-react-23a68f4e79bb

    // For simplicity's sake, just keeping clientId and clientSecret public. Will transfer in the future.
    private static final String clientId = "af30ddd8276e43cba9dcd2581010f381";
    private static final String clientSecret = "c0fceccc1b3f48d682e4b0d8fb0b0261";

    /*
        The following code is implemented using the Spotify Web Api tutorial.
            - SpotifyHttpManager is the package/library where all the methods come from.
            - makeUri will build an actual URI for Java to use.
            - Builder() actually builds the Spotify API request using the provided properties.
    */
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/spotify/get-user-code");
    private String code = ""; // Temporary for the access token.

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
}
