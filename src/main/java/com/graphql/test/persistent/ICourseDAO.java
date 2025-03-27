package com.graphql.test.persistent;

import org.springframework.data.repository.CrudRepository;

import com.graphql.test.entities.Course;

public interface ICourseDAO extends CrudRepository<Course, Long>{

}
