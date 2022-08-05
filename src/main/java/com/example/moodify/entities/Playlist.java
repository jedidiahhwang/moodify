package com.example.moodify.entities;

import com.example.moodify.dtos.PlaylistDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "UserPlaylist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    // The following few annotations are inherited from Spring JPA.
    @Id // This member will uniquely identify (PRIMARY KEY) the entity in the database.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Determines how the unique ID will be generated. Designates one column to hold unique ID.
    private Long id;

    @Column(columnDefinition = "varchar(50)", unique = true) // Playlist names are capped at 50 characters and must be unique.
    private String playlistName;

    @Column(columnDefinition = "varchar(25)") // Don't currently know mood lengths, 25 seems decent for now.
    private String mood;

    @ManyToOne // Relationship from playlists to users.
    @JsonBackReference // Prevents infinite recursion when delivering JSON through a RESTful endpoint.
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "PlaylistJoinLike",
            joinColumns = {@JoinColumn(name = "playlistId")},
            inverseJoinColumns = {@JoinColumn(name = "trackId")}
    )
    private Set<Track> trackslist = new HashSet<>();

    public Playlist(PlaylistDto playlistDto) {
        if(playlistDto.getPlaylistName() != null &&
            playlistDto.getMood() != null) {
                this.playlistName = playlistDto.getPlaylistName();
                this.mood = playlistDto.getMood();
        }
    }
}
