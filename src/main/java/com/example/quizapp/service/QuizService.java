package com.example.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;

@Service
public class QuizService {
	
	@Autowired
	QuizDao quizDao;
	
	
	@Autowired
	QuestionDao questionDao;//responsible to give questions
	
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
		
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		
		return new ResponseEntity<>("sucess",HttpStatus.CREATED);
		
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Optional<Quiz> quiz =  quizDao.findById(id);  //Optional means data database alli edru erbavdu illdale nu erbavdu
		List<Question> questionsFromDb = quiz.get().getQuestions(); //optional use madudre qet() use madbeku and this is of object type
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for(Question q : questionsFromDb) {
			QuestionWrapper qw =new QuestionWrapper(q.getId(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getQuestiontitle());
			questionsForUser.add(qw);
		}
		
		
		
		return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		Quiz quiz  = quizDao.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int right = 0;
		int i=0;
		for(Response response : responses) {
			if(response.getResponse().equals(questions.get(i).getRightanswer())) {
				right++;
			}
			i++;
		}
		return new ResponseEntity<>(right,HttpStatus.OK);
	}
	
	
}
