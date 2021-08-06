package com.chiliexe.myyahoo.controllers;

import javax.validation.Valid;

import com.chiliexe.myyahoo.models.Question;
import com.chiliexe.myyahoo.services.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    private QuestionService questionService;

    @GetMapping()
    public ModelAndView index()
    {
        return new ModelAndView("Home/index");
    }
    
    @GetMapping("criar/")
    private ModelAndView createQuestion(Question question) {
        return new ModelAndView("Home/create");
    }
    
    @PostMapping("criar/")
    public ModelAndView saveQuestion(@Valid Question question, BindingResult result)
    {
        if(result.hasErrors()) return createQuestion(question);
        var savedModel = questionService.save(question);
        var mv = new ModelAndView("redirect:/detalhe/" + savedModel.getSlug());

        return mv;
    }
}
