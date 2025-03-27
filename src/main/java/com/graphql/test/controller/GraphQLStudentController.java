package com.graphql.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphql.test.entities.Course;
import com.graphql.test.entities.Student;
import com.graphql.test.graphql.InputStudent;
import com.graphql.test.service.ICourseService;
import com.graphql.test.service.IStudentService;

@Controller
public class GraphQLStudentController {

	private static Logger logger = LoggerFactory.getLogger(GraphQLStudentController.class);
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private IStudentService studentService;
	
	@QueryMapping(name = "findStudentById")
	public Student findById(@Argument(name = "studentId") String id) {
		Long studentId = Long.parseLong(id);
		
		logger.info("Student encontrado con el id: {}", studentId);
		return studentService.findById(studentId);
	}
	
	@QueryMapping(name = "findAllStudents")
	public List<Student> findAll(){
		return studentService.findAll();
	}
	
	@MutationMapping
	public Student createStudent (@Argument InputStudent inputStudent) {
		
		Course course = courseService.findById(Long.parseLong(inputStudent.getCourseId()));
		
		Student student = new Student();
		student.setName(inputStudent.getName());
		student.setLastName(inputStudent.getLastName());
		student.setEdad(inputStudent.getEdad());
		student.setCourse(course);
		
		studentService.createStudent(student);
		
		logger.info("Student create : {}", student);
		return student;
	}
	
	@MutationMapping(name = "deleteStudentById")
	public String deleteById(@Argument(name = "studentId") String id) {
		studentService.deleteById(Long.parseLong(id));
		return "El estudiante con el id: " + id + " ha sido eliminado";
	}
}
