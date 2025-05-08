package com.aravinth.quizzapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aravinth.quizzapp.model.Quizz;

public interface QuizzDao extends JpaRepository<Quizz, Integer>{

}
