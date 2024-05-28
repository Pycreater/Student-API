package com.student.scholarship.Student_API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rollNumber;
    private String name;
    private int science;
    private int maths;
    private int english;
    private int computer;
    private String eligibility;

    public Student(Long rollNumber, String name, int science, int maths, int english, int computer, String eligibility) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.science = science;
        this.maths = maths;
        this.english = english;
        this.computer = computer;
        this.eligibility = eligibility;
    }
}
