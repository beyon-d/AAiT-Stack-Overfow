package com.example.AAiTStackOverflow.Repository;

import com.example.AAiTStackOverflow.Domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.id = ?1")

    public Optional<Question> findById(Long Id);

}


