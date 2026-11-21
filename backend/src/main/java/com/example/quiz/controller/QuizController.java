package com.example.quiz.controller;

import com.example.quiz.dto.Dtos.*;
import com.example.quiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @GetMapping
    public List<QuizSummary> list() {
        return service.listQuizzes();
    }

    @GetMapping("/{id}")
    public QuizDetail get(@PathVariable Long id) {
        return service.getQuiz(id);
    }

    @PostMapping("/{id}/submit")
    public ResultDto submit(@PathVariable Long id, @Valid @RequestBody SubmitRequest request) {
        return service.submit(id, request);
    }

    @GetMapping("/{id}/leaderboard")
    public List<LeaderboardEntry> leaderboard(@PathVariable Long id) {
        return service.leaderboard(id);
    }
}
