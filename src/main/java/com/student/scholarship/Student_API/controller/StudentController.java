package com.student.scholarship.Student_API.controller;

import com.student.scholarship.Student_API.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@Api(value = "Student Controller", tags = {"Student"})
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{rollNumber}/eligibility")
    @ApiOperation(value = "Get Eligibility Status", notes = "Get the eligibility status of a student by roll number")
    public ResponseEntity<String> getEligibilityStatus(@PathVariable Long rollNumber) {
        String eligibilityStatus = studentService.getEligibilityStatusByRollNumber(rollNumber);
        return ResponseEntity.ok(eligibilityStatus);
    }
}
