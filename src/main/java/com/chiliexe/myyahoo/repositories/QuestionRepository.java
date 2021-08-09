package com.chiliexe.myyahoo.repositories;

import java.util.Optional;

import com.chiliexe.myyahoo.models.Question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findBySlug(String slug);
    Question findByEmailAndAccessKey(String email, String key);
}
