package com.example.moodify.controllers;

import com.example.moodify.dtos.UserDto;
import com.example.moodify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.special.SearchResult;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/spotify")
public class AuthController {
    // Spotify Web Api Repository: https://github.com/spotify-web-api-java/spotify-web-api-java
    // Spotify Authentication Tutorial: https://bret-gibson.medium.com/spotify-api-authentication-with-spring-boot-and-react-23a68f4e79bb

    // For simplicity's sake, just keeping clientId and clientSecret public. Will transfer in the future.
    private static final String clientId = "af30ddd8276e43cba9dcd2581010f381";
    private static final String clientSecret = "c0fceccc1b3f48d682e4b0d8fb0b0261";

    @Autowired
    private UserService userService;

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


    @GetMapping("login")
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

    @GetMapping(value = "get-user-code")
    public String getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
        code = userCode;
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage.
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        response.sendRedirect("http://localhost:3000/current-user");
        return spotifyApi.getAccessToken();
    }

    @GetMapping(value = "user-top-artists")
    public Artist[] getUserTopArtists() {
         final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                 .time_range("medium_term")
                 .limit(10)
                 .offset(5)
                 .build();

         try {
             final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

             return artistPaging.getItems();
         }  catch (Exception e) {
             System.out.println("Something went wrong!\n" + e.getMessage());
         }
         return new Artist[0];
     }


    @GetMapping(value = "current-user-profile")
    public List<String> getCurrentUserProfile() {
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();

        UserDto userDto = new UserDto();

        try {
            final User user = getCurrentUsersProfileRequest.execute();
            Image[] images = user.getImages();
            userDto.setUsername(user.getId());
            userDto.setActualName(user.getDisplayName());
            userDto.setEmail(user.getEmail());
            userDto.setImageUrl(images[0].getUrl());
            userDto.setAccountUrl(user.getHref());
            return userService.addUser(userDto);
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @GetMapping(value = "get-spotify-playlists")
    public SearchResult getArtist(@RequestParam("mood") String mood, @RequestParam("genre") String genre) {
        System.out.println(mood + genre);
        final SearchItemRequest searchItemRequest = spotifyApi.searchItem(genre, "playlist").build();

        try {
         final SearchResult searchResult = searchItemRequest.execute();
         return searchResult;
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
         System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}
