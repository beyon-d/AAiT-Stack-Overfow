package com.example.AAiTStackOverflow.Repository;

import com.example.AAiTStackOverflow.Domain.Answer;
import com.example.AAiTStackOverflow.Domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.id = ?1")

    public Optional<Answer> findById(Long Id);
    
}
