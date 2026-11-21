package com.example.quiz.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quizId;

    @Column(nullable = false, length = 100)
    private String quizTitle;

    @Column(nullable = false, length = 50)
    private String playerName;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private LocalDateTime takenAt;

    public QuizResult() {
    }

    public QuizResult(Long quizId, String quizTitle, String playerName, int score, int total) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.playerName = playerName;
        this.score = score;
        this.total = total;
        this.takenAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getQuizId() {
        return quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int getTotal() {
        return total;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }
}
