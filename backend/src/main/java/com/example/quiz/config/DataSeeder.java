package com.example.quiz.config;

import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.repository.QuizRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** Inserts sample quizzes the first time the app runs (only if the table is empty). */
@Component
public class DataSeeder implements CommandLineRunner {

    private final QuizRepository quizRepository;

    public DataSeeder(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public void run(String... args) {
        if (quizRepository.count() > 0) {
            return;
        }

        quizRepository.save(quiz("Java Basics", "Java",
                "Core Java fundamentals: keywords, types and collections.",
                q("Which keyword is used to inherit a class in Java?", 1,
                        "implements", "extends", "inherits", "super"),
                q("Which of these is NOT a primitive data type?", 2,
                        "int", "boolean", "String", "double"),
                q("Which collection does not allow duplicate elements?", 1,
                        "ArrayList", "HashSet", "LinkedList", "Vector"),
                q("What is the default value of an int instance variable?", 0,
                        "0", "null", "1", "undefined"),
                q("Which method is the entry point of a Java program?", 2,
                        "start()", "run()", "main()", "init()")));

        quizRepository.save(quiz("SQL Basics", "SQL",
                "Queries, joins and constraints in relational databases.",
                q("Which command is used to retrieve data from a table?", 0,
                        "SELECT", "GET", "FETCH", "OPEN"),
                q("Which clause is used to filter rows?", 2,
                        "ORDER BY", "GROUP BY", "WHERE", "LIMIT"),
                q("Which JOIN returns only the rows that match in both tables?", 1,
                        "LEFT JOIN", "INNER JOIN", "RIGHT JOIN", "CROSS JOIN"),
                q("Which constraint uniquely identifies each row in a table?", 2,
                        "FOREIGN KEY", "CHECK", "PRIMARY KEY", "DEFAULT"),
                q("Which function returns the number of rows?", 1,
                        "SUM()", "COUNT()", "AVG()", "MAX()")));

        quizRepository.save(quiz("Spring Boot Basics", "Spring Boot",
                "Annotations and building blocks of a Spring Boot application.",
                q("Which annotation marks the main class of a Spring Boot application?", 0,
                        "@SpringBootApplication", "@Configuration", "@EnableAutoConfiguration", "@ComponentScan"),
                q("Which annotation creates a REST controller that returns JSON?", 1,
                        "@Controller", "@RestController", "@Service", "@Repository"),
                q("Which annotation maps an HTTP GET request to a method?", 0,
                        "@GetMapping", "@PostMapping", "@PutMapping", "@DeleteMapping"),
                q("Which interface gives ready-made CRUD methods in Spring Data JPA?", 0,
                        "JpaRepository", "EntityManager", "DataSource", "RestTemplate"),
                q("Which file holds configuration properties by default?", 1,
                        "pom.xml", "application.properties", "web.xml", "settings.gradle")));
    }

    private Quiz quiz(String title, String topic, String description, Question... questions) {
        Quiz quiz = new Quiz(title, topic, description);
        for (Question question : questions) {
            quiz.addQuestion(question);
        }
        return quiz;
    }

    private Question q(String text, int correctIndex, String... options) {
        return new Question(text, correctIndex, List.of(options));
    }
}
