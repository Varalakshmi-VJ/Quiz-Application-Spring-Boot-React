package com.example.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Request/response objects for the REST API.
 * Note: QuestionDto deliberately has no correct answer, so players cannot read it from the API.
 */
public final class Dtos {

    private Dtos() {
    }

    public record QuizSummary(Long id, String title, String topic, String description, int questionCount) {
    }

    public record QuestionDto(Long id, String text, List<String> options) {
    }

    public record QuizDetail(Long id, String title, String topic, String description,
                             List<QuestionDto> questions) {
    }

    public record SubmitRequest(
            @NotBlank(message = "Please enter your name")
            @Size(max = 50, message = "Name must be at most 50 characters")
            String playerName,
            @NotNull(message = "Answers are required")
            Map<Long, Integer> answers) {
    }

    public record ReviewItem(Long questionId, String text, List<String> options,
                             Integer selectedIndex, int correctIndex) {
    }

    public record ResultDto(Long quizId, String quizTitle, String playerName, int score, int total,
                            List<ReviewItem> review) {
    }

    public record LeaderboardEntry(String playerName, int score, int total, LocalDateTime takenAt) {
    }
}
