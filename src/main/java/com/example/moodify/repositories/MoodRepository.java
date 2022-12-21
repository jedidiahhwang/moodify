package com.example.moodify.repositories;

import com.example.moodify.entities.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood, Long> {
}
