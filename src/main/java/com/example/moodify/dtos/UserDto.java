package com.example.moodify.dtos;

/*
    This file is used to create a DTO or data transfer object. Why do we need these?
        - Entities should not be sent outside/within your application because of potential vulnerabilities.
            i. Remember, entities are for persisting data in your database and directly interact with it, basically your tables.
        - DTOs are lightweight copies of entities to pass around.
*/

import com.example.moodify.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Serializable - Allows this class to be converted to a bytestream and sent outside the application or stored in a log file.
public class UserDto implements Serializable {
    // Create some fields representing the entity we created.
    private Long id;
    private String username;
    private String actualName;
    private String email;
    private String imageUrl;
    private String accountUrl;
    private Set<PlaylistDto> playlistDtoSet = new HashSet<>(); // Set of Playlist data transfer objects.

    // Create a custom constructor that makes a DTO from the associated class. Also check that fields have been filled out correctly.
    private UserDto(User userObj) {
        // Sanity checking - Making sure that all the data provided matches our fields.
        // General control flow - Using the entity's getter/setters, if the field is not null, make this DTO's fields equal to the userObjs.
        if(userObj.getId() != null &&
            userObj.getUsername() != null &&
            userObj.getActualName() != null &&
            userObj.getEmail() != null &&
            userObj.getImageUrl() != null &&
            userObj.getAccountUrl() != null) {
                this.id = userObj.getId();
                this.username = userObj.getUsername();
                this.actualName = userObj.getActualName();
                this.email = userObj.getEmail();
                this.imageUrl = userObj.getImageUrl();
                this.accountUrl = userObj.getAccountUrl();
        }
        // Don't provide the set. You will get infinite recursion by recursively adding playlists over and over.
    }
}
