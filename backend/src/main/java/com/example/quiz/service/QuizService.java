package com.example.quiz.service;

import com.example.quiz.dto.Dtos.*;
import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.model.QuizResult;
import com.example.quiz.repository.QuizRepository;
import com.example.quiz.repository.ResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final ResultRepository resultRepository;

    public QuizService(QuizRepository quizRepository, ResultRepository resultRepository) {
        this.quizRepository = quizRepository;
        this.resultRepository = resultRepository;
    }

    @Transactional(readOnly = true)
    public List<QuizSummary> listQuizzes() {
        return quizRepository.findAll().stream()
                .map(q -> new QuizSummary(q.getId(), q.getTitle(), q.getTopic(),
                        q.getDescription(), q.getQuestions().size()))
                .toList();
    }

    @Transactional(readOnly = true)
    public QuizDetail getQuiz(Long id) {
        Quiz quiz = findQuiz(id);
        List<QuestionDto> questions = quiz.getQuestions().stream()
                .map(q -> new QuestionDto(q.getId(), q.getText(), List.copyOf(q.getOptions())))
                .toList();
        return new QuizDetail(quiz.getId(), quiz.getTitle(), quiz.getTopic(),
                quiz.getDescription(), questions);
    }

    @Transactional
    public ResultDto submit(Long quizId, SubmitRequest request) {
        Quiz quiz = findQuiz(quizId);
        List<ReviewItem> review = new ArrayList<>();
        int score = 0;

        for (Question q : quiz.getQuestions()) {
            Integer selected = request.answers().get(q.getId());
            if (selected != null && selected == q.getCorrectIndex()) {
                score++;
            }
            review.add(new ReviewItem(q.getId(), q.getText(), List.copyOf(q.getOptions()),
                    selected, q.getCorrectIndex()));
        }

        int total = quiz.getQuestions().size();
        String name = request.playerName().trim();
        resultRepository.save(new QuizResult(quiz.getId(), quiz.getTitle(), name, score, total));

        return new ResultDto(quiz.getId(), quiz.getTitle(), name, score, total, review);
    }

    @Transactional(readOnly = true)
    public List<LeaderboardEntry> leaderboard(Long quizId) {
        findQuiz(quizId);
        return resultRepository.findTop10ByQuizIdOrderByScoreDescTakenAtAsc(quizId).stream()
                .map(r -> new LeaderboardEntry(r.getPlayerName(), r.getScore(), r.getTotal(), r.getTakenAt()))
                .toList();
    }

    private Quiz findQuiz(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
    }
}
