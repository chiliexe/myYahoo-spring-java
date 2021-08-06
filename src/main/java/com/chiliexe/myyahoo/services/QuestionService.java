package com.chiliexe.myyahoo.services;

import java.util.UUID;

import javax.validation.Valid;

import com.chiliexe.myyahoo.models.Question;
import com.chiliexe.myyahoo.repositories.QuestionRepository;
import com.github.slugify.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository repository;

    
    // FIND ======
    public Question findBySlug(String slug)
    {
        var question = repository.findBySlug(slug)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return question;
    }

    // CRUD ======
    public Question save(@Valid Question question)
    {
        question.setActive(true);
        question.setAccessKey(UUID.randomUUID().toString());
        question.setSlug(new Slugify().slugify(question.getTitle()));
        return repository.save(question);
    }
    public Question update(Question question, Long id)
    {
        return null;
    }
    
}
