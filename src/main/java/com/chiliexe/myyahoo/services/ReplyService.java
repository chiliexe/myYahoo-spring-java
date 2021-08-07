package com.chiliexe.myyahoo.services;

import com.chiliexe.myyahoo.models.Question;
import com.chiliexe.myyahoo.models.Reply;
import com.chiliexe.myyahoo.repositories.ReplyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    
    @Autowired
    private ReplyRepository repository;

    public Reply save(Reply reply, Question question)
    {
        reply.setActive(true);
        reply.setQuestion(question);
        return repository.save(reply);
    }

}
