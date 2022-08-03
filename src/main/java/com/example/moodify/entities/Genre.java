package com.example.moodify.entities;

import javax.persistence.*;

@Entity
@Table(name = "Genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "genre")
    private Track track;
}
