package com.chiliexe.myyahoo.controllers;

import javax.validation.Valid;

import com.chiliexe.myyahoo.models.Question;
import com.chiliexe.myyahoo.models.QuestionDto;
import com.chiliexe.myyahoo.models.Reply;
import com.chiliexe.myyahoo.services.QuestionService;
import com.chiliexe.myyahoo.services.ReplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ReplyService replyService;

    @GetMapping()
    public ModelAndView index()
    {

        var mv = new ModelAndView("Home/index");
        var questions = questionService.findAllSummaryContent();

        mv.addObject("questions", questions);
        return mv;
    }

    @GetMapping("detalhe/{slug}")
    public ModelAndView bySlug(@PathVariable String slug, Reply reply)
    {
        ModelAndView mv = new ModelAndView("Home/detail");
        mv.addObject("question", questionService.findBySlug(slug));
        return mv;
    }
    
    @PostMapping("detalhe/{slug}/comentar")
    public ModelAndView saveReply(@Valid Reply reply, @PathVariable String slug, BindingResult result)
    {
        Question question = questionService.findBySlug(slug);
        replyService.save(reply, question);
        return new ModelAndView("redirect:/detalhe/" + question.getSlug() + "#respostas");
    }

    // EDIT =========================
    @GetMapping("detalhe/editar")
    public ModelAndView checkEdit(Model model)
    {
        model.addAttribute("dto", new QuestionDto());
        return new ModelAndView("Home/checkEdit");
    }

    @PostMapping("detalhe/editar")
    public ModelAndView checkEditPost(@Valid QuestionDto dto, RedirectAttributes attr)
    {
        var question = questionService.findByEmailAndKey(dto);
        var mv = new ModelAndView();
        if(question == null){

            attr.addFlashAttribute("msg", true);
            mv.setViewName("Home/checkEdit");
            return mv;
        }

        mv.setViewName("Home/edit");
        mv.addObject("question", question);
        return mv;
    }

    @PostMapping("detalhe/editar/{slug}")
    public ModelAndView editQuestion(@Valid Question question, @PathVariable String slug)
    {
        var findQuestion = questionService.findBySlug(slug);
        Question updated = questionService.update(question, findQuestion.getId());
        return new ModelAndView("redirect:/detalhe/" + updated.getSlug());
    }


    @GetMapping("criar")
    public ModelAndView createQuestion(Question question) {
        return new ModelAndView("Home/create");
    }
    
    @PostMapping("criar")
    public ModelAndView saveQuestion(@Valid Question question, BindingResult result)
    {
        if(result.hasErrors()) return createQuestion(question);
        var savedModel = questionService.save(question);
        var mv = new ModelAndView("redirect:/detalhe/" + savedModel.getSlug());
        return mv;
    }
}
