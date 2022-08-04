package com.example.moodify.dtos;

import com.example.moodify.entities.Genre;
import com.example.moodify.entities.Track;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDto implements Serializable {
    private Long id;
    private String trackname;
    private String spotifyId;
    private PlaylistDto playlistDto;
    private GenreDto genreDto;

    public TrackDto(Track trackObj) {
        if(trackObj.getId() != null &&
            trackObj.getTrackname() != null &&
            trackObj.getSpotifyId() != null) {
                this.id = trackObj.getId();
                this.trackname = trackObj.getTrackname();
                this.spotifyId = trackObj.getSpotifyId();
        }
    }
}
