package com.gl.StudentRegistration.controller;

import java.security.Principal;
import java.util.List;

import com.gl.StudentRegistration.entity.Student;
import com.gl.StudentRegistration.service.StudentServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentServices studentservice;

	@RequestMapping("/list") //"/list"
	public String listStudents(Model theModel) {

		System.out.println("Request to list all students received");
		List<Student> allStudents = studentservice.findAll();
		theModel.addAttribute("Students", allStudents);

		return "list-students";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		Student individual = new Student();
		theModel.addAttribute("Student", individual);

		return "student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") int Id, Model theModel) {
		Student individual = studentservice.findById(Id);
		theModel.addAttribute("Student", individual);
		return "student-form";

	}

	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int Id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("department") String department,
			@RequestParam("country") String country) {

		System.out.println(Id);
		Student individual;
		if (Id != 0) {
			individual = studentservice.findById(Id);
			individual.setFirstName(firstName);
			individual.setLastName(lastName);
			individual.setDepartment(department);
			individual.setCountry(country);
		} else
			individual = new Student(firstName, lastName, department, country);

		studentservice.save(individual);

		return "redirect:/students/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("id") int Id) {
		studentservice.deleteById(Id);
		return "redirect:/students/list";
		

	}
	
	@RequestMapping(value = "/403")
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() 
			+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", 
			"You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}

}
