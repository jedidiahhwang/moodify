package com.example.moodify.entities;

import com.example.moodify.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column
    private String username;

    @Column
    private String actualName;

    @Column
    private String email;

    @Column(columnDefinition = "text")
    private String imageUrl;

    @Column(columnDefinition = "text")
    private String accountUrl;

    /*
        REVIEW THESE ANNOTATION PROPERTIES
        mappedBy (required) - Field that owns the relationship. Required unless the relationship is unidirectional.
        fetch (optional) - How will you be fetching the data? Lazily or Eagerly (default is EAGER)?
            i. Eagerly will fetch all connected tables all at once (fetch all playlists)
            ii. Lazily will fetch when needed (fetch the playlist when requested for)
            SOURCE: https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
        cascade (optional) - Operations that must be cascaded to the target of the association.
            i. MERGE will merge the existing data in the table with the data in my object (sync to database). Think of saving (loosely).
            ii. PERSIST will create new records from the object in the database. Makes a transient instance persistent.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    /*
        Why a @OneToMany relationship? What's the difference with @ManyToOne

        Since each user probably won't have hundreds, even dozens of playlists, it might make more sense to establish
            a one-to-many relationship. The user is going to be the parent, while playlists are possessed by each user.
        However, these are two sides of the same coin, and you may access a user through a playlist. Make the access
            bidirectional instead of unilateral. Supposedly this prevents a lot of side effects of table generation
            according to https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/.
     */
    @JsonManagedReference
    private Set<Playlist> playlistSet = new HashSet<>();

    public User(UserDto userDto) {
        if(userDto.getUsername() != null &&
            userDto.getActualName() != null &&
            userDto.getEmail() != null &&
            userDto.getImageUrl() != null &&
            userDto.getAccountUrl() != null) {
                this.username = userDto.getUsername();
                this.actualName = userDto.getActualName();
                this.email = userDto.getEmail();
                this.imageUrl = userDto.getImageUrl();
                this.accountUrl = userDto.getAccountUrl();
        }
    }
}
