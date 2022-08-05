package com.example.moodify.entities;

import com.example.moodify.dtos.GenreDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Genres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String genre;

    @OneToOne(mappedBy = "genre")
    private Track track;

    public Genre(GenreDto genreDto) {
        if(genreDto.getGenre() != null) {
            this.genre = genreDto.getGenre();
        }
    }
}
