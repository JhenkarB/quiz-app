package com.example.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.model.*;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;
	
	
	//to get all questions
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
			}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);// mele error bandre empty array kodoke
	}

	//Get questions By category
	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
		return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}

	//add
	public ResponseEntity<String> addQuestion(Question question) {
		 questionDao.save(question);
		 return new ResponseEntity<>("success",HttpStatus.CREATED);
		
	}

	//delete
	public String deleteQuestion(Question question) {
		 questionDao.delete(question);
		 return "The question is deleted";
	}

}
