package com.example.moodify.dtos;

import com.example.moodify.entities.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDto implements Serializable {
    private Long id;
    private String playlistName;
    private String mood;
    private String imageUrl;
    private String playlistUrl;
    private UserDto userDto;
//    private Set<TrackDto> trackDtoSet = new HashSet<>(); // Set of Track data transfer objects.

    public PlaylistDto(Playlist playlistObj) {
//        if(playlistObj.getId() != null &&
//            playlistObj.getPlaylistName() != null &&
//            playlistObj.getMood() != null) {
//                this.id = playlistObj.getId();
//                this.playlistName = playlistObj.getPlaylistName();
//                this.mood = playlistObj.getMood();
//        }

        if (playlistObj.getId() != null) {
            this.id = playlistObj.getId();
        }
        if(playlistObj.getPlaylistName() != null) {
            this.playlistName = playlistObj.getPlaylistName();
        }
        if(playlistObj.getMood() != null) {
            this.mood = playlistObj.getMood();
        }
        if(playlistObj.getPlaylistUrl() != null) {
            this.playlistUrl = playlistObj.getPlaylistUrl();
        }
    }
}
