package com.example.moodify.repositories;

import com.example.moodify.entities.Playlist;
import com.example.moodify.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByUserEquals(User user);
}
