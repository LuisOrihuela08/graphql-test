package com.graphql.test.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphql.test.entities.Student;
import com.graphql.test.persistent.IStudentDAO;
import com.graphql.test.service.IStudentService;

import jakarta.transaction.Transactional;


@Service
public class StudentServiceImpl implements IStudentService{

	@Autowired
	private IStudentDAO studentDAO;
	
	@Override
	public Student findById(Long id) {
		return studentDAO.findById(id).orElseThrow();
	}

	@Override
	public List<Student> findAll() {
		return (List<Student>) studentDAO.findAll();
	}

	@Override
	@Transactional
	public void createStudent(Student student) {
		studentDAO.save(student);
		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		studentDAO.deleteById(id);
		
	}

}
