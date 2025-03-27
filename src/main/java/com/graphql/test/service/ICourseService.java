package com.graphql.test.service;

import java.util.List;

import com.graphql.test.entities.Course;

public interface ICourseService {

	Course findById(Long id);
	
	List<Course> findAll();
	
	void createCourse (Course course);
	
	void deleteById (Long id);
}
