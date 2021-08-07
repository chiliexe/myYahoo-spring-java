package com.chiliexe.myyahoo.services;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.chiliexe.myyahoo.models.Question;
import com.chiliexe.myyahoo.repositories.QuestionRepository;
import com.chiliexe.myyahoo.utils.EmailSender;
import com.github.slugify.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository repository;

    @Autowired
    private EmailSender emailSender;
    // FIND ======
    public Question findBySlug(String slug)
    {
        var question = repository.findBySlug(slug)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return question;
    }

    public List<Question> findAllSummaryContent()
    {
        List<Question> questions = repository.findAll();

        questions.forEach(q -> {
            if(q.getDescription().length() > 200){
                q.setDescription(q.getDescription().substring(0, 199));
            }
        });
        return questions;
    }

    // CRUD ======
    public Question save(@Valid Question question)
    {
        question.setActive(true);
        question.setAccessKey(UUID.randomUUID().toString());
        question.setSlug(new Slugify().slugify(question.getTitle()));

        var savedModel = repository.save(question);
        emailSender.send(savedModel);

        return savedModel;
    }
    public Question update(Question question, Long id)
    {
        return null;
    }
    
}
