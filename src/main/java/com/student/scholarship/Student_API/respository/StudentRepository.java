package com.student.scholarship.Student_API.respository;

import com.student.scholarship.Student_API.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByRollNumber(Long rollNumber);
}
