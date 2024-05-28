package com.student.scholarship.Student_API.service;

import com.student.scholarship.Student_API.model.Student;
import com.student.scholarship.Student_API.respository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class CsvFileService {

    @Autowired
    private StudentRepository studentRepository;

    private int scienceCriteria = 85;
    private int mathsCriteria = 90;
    private int englishCriteria = 75;
    private int computerCriteria = 95;

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public Path processCsvFile(MultipartFile file) throws IOException {
        List<Student> students = parseCsvFile(file);
        updateEligibilityStatus(students);

        studentRepository.saveAll(students);

        Path outputPath = Files.createTempFile("updated_students", ".csv");
        writeUpdatedCsvFile(students, outputPath);

        return outputPath;
    }

    public List<Student> parseCsvFile(MultipartFile file) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 7) {
                    Long rollNumber = Long.parseLong(fields[0]);
                    String name = fields[1];
                    int science = Integer.parseInt(fields[2]);
                    int maths = Integer.parseInt(fields[3]);
                    int english = Integer.parseInt(fields[4]);
                    int computer = Integer.parseInt(fields[5]);
                    String eligibility = fields[6];

                    Student student = new Student(rollNumber, name, science, maths, english, computer, eligibility);
                    students.add(student);
                }
            }
        }
        return students;
    }

    public void updateEligibilityStatus(List<Student> students) {
        for (Student student : students) {
            executor.execute(() -> {
                int science = student.getScience();
                int maths = student.getMaths();
                int english = student.getEnglish();
                int computer = student.getComputer();

                if (science > scienceCriteria && maths > mathsCriteria && english > englishCriteria && computer > computerCriteria) {
                    student.setEligibility("YES");
                } else {
                    student.setEligibility("NO");
                }
            });
        }
    }

    public void writeUpdatedCsvFile(List<Student> students, Path outputPath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            for (Student student : students) {
                writer.write(String.format("%d,%s,%d,%d,%d,%d,%s%n",
                        student.getRollNumber(), student.getName(),
                        student.getScience(), student.getMaths(),
                        student.getEnglish(), student.getComputer(),
                        student.getEligibility()));
            }
        }
    }

    public void setEligibilityCriteria(int science, int maths, int english, int computer) {
        this.scienceCriteria = science;
        this.mathsCriteria = maths;
        this.englishCriteria = english;
        this.computerCriteria = computer;
    }
}
