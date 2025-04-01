package com.graphql.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graphql.test.entities.Course;

public interface CourseRepository extends JpaRepository <Course, Long>{

	//Optional para mapear si existe o no
	Optional<Course> findByName (String name);
	
	//Aca le agrego un List, porque puede haber el hecho de que un profesor enseñe mas de un curso
	List<Course> findByTeacher (String teacher);
}
