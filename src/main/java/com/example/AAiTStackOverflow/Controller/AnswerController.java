package com.example.AAiTStackOverflow.Controller;

import com.example.AAiTStackOverflow.Domain.Account;
import com.example.AAiTStackOverflow.Domain.Answer;
import com.example.AAiTStackOverflow.Domain.Question;
import com.example.AAiTStackOverflow.Repository.AnswerRepository;
import com.example.AAiTStackOverflow.Repository.QuestionRepository;
import com.example.AAiTStackOverflow.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@NoArgsConstructor

public class AnswerController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/answerAdd/{id}")
    public String answerAddForm(@PathVariable Long id, Model model, Authentication authentication){
        if (authentication != null){
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            Account author = userRepo.findByEmail( userPrincipal.getUsername());
            model.addAttribute("author", author);
        }
        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);

        model.addAttribute("answer", new Answer());
        return "answerAdd";
    }
    @PostMapping("/processAnswer/{id}")
    public String saveAnswer(@Valid @ModelAttribute("answer") Answer answer,  Errors errors,
                             @PathVariable Long id, Model model, Authentication authentication ){

        if (errors.hasErrors()) {
            return "answerAdd";
        }
        if (authentication == null){
            return "/login";
        }
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Account author = userRepo.findByEmail( userPrincipal.getUsername());

        model.addAttribute("author", author);

        answer.setAuthor(author);

        Question question = questionRepository.findById(id).get();
        answer.setQuestion(question);
        answerRepository.save(answer);

        return "answerAddSuccess";
    }
    @GetMapping("/answer/delete/{id}")
    public String deleteAnswer(@PathVariable Long id){
        answerRepository.deleteById(id);
        return "redirect:/questions";
    }

    @GetMapping("/answerUpdate/{id}")
    public String updateAnswerForm(@PathVariable Long id, Model model,
                                     Authentication authentication ){
        if (authentication != null){
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            Account author = userRepo.findByEmail( userPrincipal.getUsername());
            model.addAttribute("author", author);
        }

        Answer answer = answerRepository.findById(id).get();
        Question question = answer.getQuestion();
        model.addAttribute("question", question);

        model.addAttribute("answer", answer);
        return "answerUpdate";
    }

    @PostMapping("/answerUpdate/{id}")
    public String updateAnswer(@PathVariable Long id, Authentication authentication, Model model,
                                 @Valid @ModelAttribute("answer") Answer answer,Errors errors) {
        if (errors.hasErrors()) {
            return "questionUpdate";
        }
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String userEmail = userPrincipal.getUsername();
        Account author = userRepo.findByEmail(userEmail);
        model.addAttribute("author", author);

        Answer oldAnswer = answerRepository.findById(id).get();
        oldAnswer.setAuthor(author);

        oldAnswer.setId(id);
        oldAnswer.setQuestion(oldAnswer.getQuestion());
        oldAnswer.setContent(answer.getContent());


        answerRepository.save(oldAnswer);

        return "answerUpdateSuccess";
    }


}
