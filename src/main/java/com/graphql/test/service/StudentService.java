package com.graphql.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphql.test.entities.Student;
import com.graphql.test.repository.StudentRepository;



@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	
	public List<Student> findAllStudents(){
		return studentRepository.findAll();
	}
	
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}
	
	public Optional<Student> findStudentById(Long id){
		return studentRepository.findById(id);
	}
	
	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}
	
	//Buscar Estudiante por nombre
	public List<Student> findStudentByLastNombre(String lastName) {
		return studentRepository.findByLastName(lastName);
	}
	
	
}
