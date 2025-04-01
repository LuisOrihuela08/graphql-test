package com.graphql.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graphql.test.entities.Student;

public interface StudentRepository extends JpaRepository <Student, Long>{

	List<Student> findByLastName (String lastName);
}
