package com.gl.StudentRegistration.service;

import java.util.List;

import com.gl.StudentRegistration.entity.Student;
import com.gl.StudentRegistration.repository.StudentRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServicesImpl implements StudentServices {

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public List<Student> findAll() {
		List<Student> students = studentRepository.findAll();
		return students;
	}

	@Override
	public Student findById(int Id) {
		Student student = studentRepository.findById(Id).get();
		return student;
	}

	@Override
	public void save(Student individual) {
		studentRepository.save(individual);
		
	}

	@Override
	public void deleteById(int Id) {
		studentRepository.deleteById(Id);
		
	}

}
