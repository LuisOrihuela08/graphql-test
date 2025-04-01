package com.graphql.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphql.test.entities.Course;
import com.graphql.test.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public List<Course> findAllCourses(){
		return courseRepository.findAll();
	}
	
	public Course saveCourse(Course course) {
		return courseRepository.save(course);
	}
	
	public Optional<Course> findCourseById(Long id){
		return courseRepository.findById(id);
	}
	
	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
	}
	
	//Buscar curso por nombre
	public Optional<Course> findCourseByName(String name){
		return courseRepository.findByName(name);
	}
	
	//Buscar curso por profesor
	public List<Course> findCourseByTeacher(String teacher){
		return courseRepository.findByTeacher(teacher);
	}
}
