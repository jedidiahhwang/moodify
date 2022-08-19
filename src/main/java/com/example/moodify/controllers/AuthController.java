package com.example.moodify.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

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


    @GetMapping("/login")
    /*
        Spring needs to be explicitly told to format responses as JSON.

        The reason we want to use the @ResponseBody annotation is it will automatically serialize the
        the response into JSON and passed into the HttpResponse object (our protocol we're using for our
        web applications). This makes accessing the data from the frontend much easier.
    */
    @ResponseBody
    public String spotifyLogin() {
        // From the wrapper. Formats the URI that will prompt the user to authorize with their Spotify account.
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                // Refer to Spotify docs for the following scopes (cna interact with them).
                .scope("user-read-private, user-read-email, user-top-read")
                // Provides the dialog box from Spotify.
                .show_dialog(true)
                // Now build this URI.
                .build();

        // The URI has an execute() method that will return the formatted URI to our uri variable.
        final URI uri = authorizationCodeUriRequest.execute();
        // Make sure it's the right datatype.
        return uri.toString();
    }
}
