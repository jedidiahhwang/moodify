package com.example.moodify.dtos;

import com.example.moodify.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto implements Serializable {
    private Long id;
    private String genre;
    private TrackDto trackDto;

    public GenreDto(Genre genreObj) {
        if(genreObj.getId() != null &&
            genreObj.getGenre() != null) {
                this.id = genreObj.getId();
                this.genre = genreObj.getGenre();
        }
    }
}
