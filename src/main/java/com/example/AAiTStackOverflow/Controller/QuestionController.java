
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@NoArgsConstructor

public class QuestionController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/questions")
    public String viewQuestions(Model model, Authentication authentication){
        if (authentication != null){
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            Account author = userRepo.findByEmail( userPrincipal.getUsername());
            model.addAttribute("author", author);
       }

        List<Question> listQuestions = questionRepository.findAll();
        model.addAttribute("listQuestions", listQuestions);
        return "questionsList";
    }
    @GetMapping("/askQuestion")
    public String askQuestionPage(Model model, Authentication authentication){
        if (authentication != null){
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            Account author = userRepo.findByEmail( userPrincipal.getUsername());
            model.addAttribute("author", author);
        }
        Question question = new Question();
        model.addAttribute("question", question);
        return "questionAdd";
    }

    @PostMapping("/process_question")
    public String processQuestion(@Valid @ModelAttribute("question") Question question,
                                  Errors errors, Model model, Authentication authentication){
        if (errors.hasErrors()) {
            return "questionAdd";
        }
        if (authentication == null){
          return "/login";
        }
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Account author = userRepo.findByEmail( userPrincipal.getUsername());
        model.addAttribute("author", author);
        question.setAuthor(author);

        questionRepository.save(question);
        return "questionAddSuccess";
    }

    @GetMapping("/questionAnswer/{id}")
    public String questionAnswer(@PathVariable Long id, Model model, Authentication authentication ){

        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);

        List<Answer> answerList = answerRepository.findAll();
        model.addAttribute("answerList", answerList);

        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String userEmail = userPrincipal.getUsername();
        Account author = userRepo.findByEmail(userEmail);
        String role = author.getRole().toString();
        model.addAttribute("role", role);
        model.addAttribute("author", author);
        return "questionAnswer";
    }
    @GetMapping("/question/delete/{id}")
    public String deleteStudent(@PathVariable Long id){
        Question question = questionRepository.findById(id).get();
        if (answerRepository.existsById(question.getId())){
            answerRepository.deleteById(question.getId());
        }
        questionRepository.deleteById(id);
        return "redirect:/questions";
    }

    @GetMapping("/questionUpdate/{id}")
    public String updateQuestionForm(@PathVariable Long id, Model model,
                                     Authentication authentication ){
        if (authentication != null){
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            Account author = userRepo.findByEmail( userPrincipal.getUsername());
            model.addAttribute("author", author);
        }

        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);
        return "questionUpdate";
    }
    
    @PostMapping("/questionUpdate/{id}")
    public String updateQuestion(@PathVariable Long id, Authentication authentication, Model model,
                                 @Valid @ModelAttribute("question") Question question,Errors errors) {
        if (errors.hasErrors()) {
            return "questionUpdate";
        }
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        String userEmail = userPrincipal.getUsername();
        Account author = userRepo.findByEmail(userEmail);

        model.addAttribute("author", author);

        Question oldQuestion = questionRepository.findById(id).get();

        oldQuestion.setId(id);
        oldQuestion.setTitle(question.getTitle());
        oldQuestion.setDescription(question.getDescription());
        oldQuestion.setAuthor(author);
        questionRepository.save(oldQuestion);

        return "questionUpdateSuccess";
    }


}
