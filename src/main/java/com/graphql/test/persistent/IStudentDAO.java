package com.graphql.test.persistent;

import org.springframework.data.repository.CrudRepository;

import com.graphql.test.entities.Student;

public interface IStudentDAO extends CrudRepository<Student, Long>{

}
