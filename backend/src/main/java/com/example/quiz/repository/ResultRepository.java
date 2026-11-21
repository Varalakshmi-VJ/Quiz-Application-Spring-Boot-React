package com.example.quiz.repository;

import com.example.quiz.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findTop10ByQuizIdOrderByScoreDescTakenAtAsc(Long quizId);
}
