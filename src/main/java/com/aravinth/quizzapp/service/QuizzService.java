package com.aravinth.quizzapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aravinth.quizzapp.dao.QuestionDao;
import com.aravinth.quizzapp.dao.QuizzDao;
import com.aravinth.quizzapp.model.Question;
import com.aravinth.quizzapp.model.QuestionWrapper;
import com.aravinth.quizzapp.model.Quizz;
import com.aravinth.quizzapp.model.Response;

@Service
public class QuizzService {
	
	@Autowired
	QuizzDao quizzDao;
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuizz(String category, int numQ, String title) {
		
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
		
		Quizz quizz = new Quizz();
		quizz.setTitle(title);
		quizz.setQuestions(questions);
		quizzDao.save(quizz);
		
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
		
		}

	public ResponseEntity<List<QuestionWrapper>> getQuizzQuestions(Integer id) {
		
		Optional<Quizz> quizz = quizzDao.findById(id);
		List<Question> questionsFromDB = quizz.get().getQuestions();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for(Question q : questionsFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
			questionsForUser.add(qw);
		}
		
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
		
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		Quizz quizz = quizzDao.findById(id).get();
		List<Question> questions = quizz.getQuestions();
		
		int right = 0;
		int i = 0;
		for(Response response : responses) {
			if(response.getResponse().equals(questions.get(i).getRightAnswer()))
				right++;
			
			i++;
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
		
	}
	

	

