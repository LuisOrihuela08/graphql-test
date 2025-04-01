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
import com.graphql.test.graphql.InputCourse;
import com.graphql.test.service.CourseService;

@Controller
public class GraphQLCourseController {

	private static Logger logger = LoggerFactory.getLogger(GraphQLCourseController.class);
	
	@Autowired
	private CourseService courseService;
	
	@QueryMapping(name = "findCourseById")
	public Course findById(@Argument(name = "courseId") String id) {
		
		try {
			Long courseId = Long.parseLong(id);	
			
			Optional<Course> courseOptional = courseService.findCourseById(courseId);
			
			if (courseOptional.isEmpty()) {
				 throw new RuntimeException("Curso no encontrado");
			}
			
			Course courseEncontrado = courseOptional.get();
			
			logger.info("Curso encontrado : {}", courseEncontrado);
			return courseEncontrado;
			
		} catch (Exception e) {
			logger.error("Error al encontrado el curso con el id: {}", id);
			throw new RuntimeException("Error al encontrar el Curso :" + e.getMessage());
		}
	}
	
	//Buscar curso por nombre
	@QueryMapping(name = "findCourseByName")
	public Course findByName(@Argument(name = "name") String name) {
		
		try {
			
			Optional<Course> courseOptional = courseService.findCourseByName(name);
			
			if (courseOptional.isEmpty()) {
				logger.error("Curso no encontrado con el nombre: {}", name);
				throw new RuntimeException("Curso no encontrado con ese nombre " + name);
			}
			
			logger.info("Curso encontado: {}", courseOptional);
			return courseOptional.get();
			
		} catch (Exception e) {
			logger.error("ERROR AL ENCONTRAR EL CURSO ", e);
			throw new RuntimeException("ERROR AL ENCONTRAR EL CURSO " + e.getMessage());
		}
	}
	
	@QueryMapping(name = "findAllCourses")
	public List<Course> findAll(){
		
		try {
			
			List<Course> listCourses = courseService.findAllCourses();
			
			logger.info("Listado de Cursos OK");
			return listCourses;
			
		} catch (Exception e) {
			logger.error("Error al listar los cursos", e);
			throw new RuntimeException("Error al listar los cursos" + e.getMessage());
		}
		
		
	}
	
	@MutationMapping
	public Course createCourse(@Argument InputCourse inputCourse) {
		
		try {
			
			Course course = new Course();
			course.setName(inputCourse.getName());
			course.setCategory(inputCourse.getCategory());
			course.setTeacher(inputCourse.getTeacher());
			
			courseService.saveCourse(course);
			
			logger.info("Course creado con éxito: {}", course);
			return course;
			
			
		} catch (Exception e) {
			logger.error("Error al crear el curso", e);
			throw new RuntimeException("Error al crear el curso" + e.getMessage());
		}
				
	}
	
	@MutationMapping(name = "deleteCourseById")
	public String deleteById(@Argument(name = "courseId") String id) {
		
		try {
			
			Optional<Course> courseOptional = courseService.findCourseById(Long.parseLong(id));
			
			if (courseOptional.isEmpty()) {
				logger.error("Error, curso no encontrado con el id: {}", id);
				return "ERROR, Curso no encontrado con el id: " + id;
			}
			
			courseService.deleteCourse(Long.parseLong(id));
			
			logger.info("Course eliminado exitosamente");
			return "Curso con el id: " + id + " ha sido eliminado";
			
		} catch (Exception e) {
			logger.error("Error al eliminar el course");
			return "Error al eliminar el course";
		}
		
		
		
	}
}
