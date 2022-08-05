package com.example.moodify.entities;

import com.example.moodify.dtos.TrackDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Tracks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String trackname;

    @Column(columnDefinition = "text")
    private String spotifyId;

    @ManyToMany(mappedBy = "trackslist", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Playlist> playlists = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genreId", referencedColumnName = "id")
    private Genre genre;

    public Track(TrackDto trackDto) {
        if(trackDto.getId() != null &&
                trackDto.getTrackname() != null &&
                trackDto.getSpotifyId() != null) {
            this.id = trackDto.getId();
            this.trackname = trackDto.getTrackname();
            this.spotifyId = trackDto.getSpotifyId();
        }
    }
}
