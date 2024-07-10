package com.example.QuizApp.service;

import com.example.QuizApp.dao.QuestionDao;
import com.example.QuizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Question successfully added.", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResponseEntity<>("Failed to add question.", HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<String> deleteQuestion(int questionId) {
        try {
            questionDao.deleteById(questionId);
            return new ResponseEntity<>("Question successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to delete question.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Question successfully updated.", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResponseEntity<>("Failed to update question.", HttpStatus.BAD_REQUEST);
    }
}