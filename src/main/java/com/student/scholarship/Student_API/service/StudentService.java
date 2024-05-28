package com.student.scholarship.Student_API.service;

import com.student.scholarship.Student_API.model.Student;
import com.student.scholarship.Student_API.respository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String getEligibilityStatusByRollNumber(Long rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber);
        return student != null ? student.getEligibility() : "NA";
    }
}
