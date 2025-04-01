package com.graphql.test.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.graphql.test.entities.Course;
import com.graphql.test.entities.Student;
import com.graphql.test.graphql.InputStudent;
import com.graphql.test.service.CourseService;
import com.graphql.test.service.StudentService;

@Controller
public class GraphQLStudentController {

	private static Logger logger = LoggerFactory.getLogger(GraphQLStudentController.class);

	@Autowired
	private CourseService courseService;

	@Autowired
	private StudentService studentService;

	@QueryMapping(name = "findStudentById")
	public Student findById(@Argument(name = "studentId") String id) {
		
		try {
			Long studentId = Long.parseLong(id);
			
			Optional<Student> studentOptional = studentService.findStudentById(studentId);
			
			if (studentOptional.isEmpty()) {
				 throw new RuntimeException("Estudiante no encontrado con el id: " + studentId);
			}
			
			Student studentEncontrado = studentOptional.get();
			
			logger.info("Estudiante encontrado : {}", studentEncontrado);
			return studentEncontrado;
			
		} catch (Exception e) {
			logger.error("ERROR AL ENCONTRADO EL ESTUDIANTE {}", e);
			throw new RuntimeException("Error al encontrar el estudiante :" + e.getMessage());
		}
				
	}
	
	//Buscar un estudiante por su apellido
	//Aca no le agrego la anotacion Query por tanto en el schema como el metodo aca, tienen
	//el mismo nombre
	@QueryMapping(name = "findStudentByLastName")
	public List<Student> findByLastName(@Argument (name = "lastName") String lastName) {
		
		try {
			
			logger.info("Buscando estudiantes con apellido: {}", lastName);
			
			List<Student> listStudents = studentService.findStudentByLastNombre(lastName);
			
			if (listStudents.isEmpty()) {
				logger.error("No existe estudiante(s) con el apellido: {}" + lastName);
				throw new RuntimeException("No existe estudiante(s) con el apellido: {}" + lastName);
			}
			
			logger.info("Estudiante(s) encontrado: {}", listStudents);
			return listStudents;		
			
		} catch (Exception e) {
			logger.error("Error al encontrar al estudiante por su apellido", e);
			throw new RuntimeException("ERROR AL ENCONTRADO EL ESTUDIANTE"+ e.getMessage());
		}
	}
	
	

	@QueryMapping(name = "findAllStudents")
	public List<Student> findAll() {

		try {

			List<Student> listStudent = studentService.findAllStudents();

			logger.info("Listado de Estudiantes OK");
			return listStudent;

		} catch (Exception e) {
			logger.error("Error al listar los estudiantes", e);
			throw new RuntimeException("Error al listar los estudiantes: " + e.getMessage());

		}
	}

	@MutationMapping
	public Student createStudent(@Argument InputStudent inputStudent) {

		try {

			Optional<Course> courseOptional = courseService.findCourseById(Long.parseLong(inputStudent.getCourseId()));

			if (courseOptional.isEmpty()) {
				 throw new RuntimeException("Curso no encontrado");
			}

			Course courseEncontrado = courseOptional.get();

			//Creando un Estudiante
			Student student = new Student();
			student.setName(inputStudent.getName());
			student.setLastName(inputStudent.getLastName());
			student.setEdad(inputStudent.getEdad());
			student.setCourse(courseEncontrado);

			studentService.saveStudent(student);

			logger.info("Estudiante creado : {}", student);
			return student;

		} catch (Exception e) {
			logger.error("¡¡ Error al crear el estudiante !!", e);
			throw new RuntimeException("Error al registrar el estudiante: " + e.getMessage());
		}

	}

	@MutationMapping(name = "deleteStudentById")
	public String deleteById(@Argument(name = "studentId") String id) {

		try {
			
			Optional<Student> studentOptional = studentService.findStudentById(Long.parseLong(id));
			
			if (studentOptional.isEmpty()) {
				logger.error("Estudiante no encontrado con el id: {}", id);
				return "Error al eliminar, estudiante no encontrado con el id: " + id;
			}

			studentService.deleteStudent(Long.parseLong(id));
			logger.info("Student eliminado con el id: {}", id);
			return "El estudiante con el id: " + id + " ha sido eliminado";

		} catch (Exception e) {
			logger.error("Error al eliminar el Estudiante");
			return "ERROR AL ELIMINAR AL ESTUDIANTE";
		}

	}
}
