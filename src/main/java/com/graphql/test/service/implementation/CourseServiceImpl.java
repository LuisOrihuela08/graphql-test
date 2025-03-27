package com.graphql.test.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphql.test.entities.Course;
import com.graphql.test.persistent.ICourseDAO;
import com.graphql.test.service.ICourseService;

import jakarta.transaction.Transactional;

@Service
public class CourseServiceImpl implements ICourseService{

	@Autowired
	private ICourseDAO courseDAO;
	
	@Override
	public Course findById(Long id) {
		return courseDAO.findById(id).orElseThrow();
	}

	@Override
	public List<Course> findAll() {
		return (List<Course>) courseDAO.findAll();
	}

	@Override
	@Transactional
	public void createCourse(Course course) {
		courseDAO.save(course);
		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		courseDAO.deleteById(id);
		
	}

}
