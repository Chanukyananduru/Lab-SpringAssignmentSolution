package com.gl.StudentRegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gl.StudentRegistration.entity.Student;

public interface StudentRepository extends JpaRepository <Student, Integer> {

}
 