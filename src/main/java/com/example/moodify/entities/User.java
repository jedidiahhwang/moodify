package com.example.moodify.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // The following few annotations are inherited from Spring JPA.
    @Id // This member will uniquely identify (PRIMARY KEY) the entity in the database.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Determines how the unique ID will be generated. Designates one column to hold unique ID.
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @OneToMany
    /*
        Why a @OneToMany relationship? What's the difference with @ManyToOne

        Since each user probably won't have hundreds, even dozens of playlists, it might make more sense to establish
            a one-to-many relationship. The user is going to be the parent, while playlists are possessed by each user.
        However, these are two sides of the same coin, and you may access a user through a playlist. Make the access
            bidirectional instead of unilateral. Supposedly this prevents a lot of side effects of table generation
            according to https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/.
     */
    @JsonBackReference // Prevents infinite recursion when passing JSON through RESTful API. Read into this.
    private Playlist playlist;
}
