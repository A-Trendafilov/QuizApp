package com.example.QuizApp.service;

import com.example.QuizApp.dao.QuestionDao;
import com.example.QuizApp.dao.QuizDoa;
import com.example.QuizApp.model.Question;
import com.example.QuizApp.model.QuiestionWrapper;
import com.example.QuizApp.model.Quiz;
import com.example.QuizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDoa quizDoa;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDoa.save(quiz);
        return new ResponseEntity<>("Quiz successfully added.", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuiestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizDoa.findById(quizId);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuiestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionsFromDB) {
            QuiestionWrapper qw = new QuiestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
                    q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quiz = quizDoa.findById(id);
        List<Question> questions = quiz.get().getQuestions();
        int right = 0;
        int i = 0;
        for (Response response : responses) {
            if (response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
