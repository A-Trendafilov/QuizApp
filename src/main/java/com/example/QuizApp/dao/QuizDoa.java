package com.example.QuizApp.dao;

import com.example.QuizApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDoa extends JpaRepository<Quiz, Integer> {
}
