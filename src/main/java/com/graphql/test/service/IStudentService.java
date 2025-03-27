package com.graphql.test.service;

import java.util.List;

import com.graphql.test.entities.Student;

public interface IStudentService {

	Student findById(Long id);
	
	List<Student> findAll();
	
	void createStudent (Student student);
	
	void deleteById (Long id);
 }
