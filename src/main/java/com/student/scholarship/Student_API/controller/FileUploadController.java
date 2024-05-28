package com.student.scholarship.Student_API.controller;

import com.student.scholarship.Student_API.service.CsvFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api")
@Api(value = "File Upload Controller", tags = {"File Upload"})
public class FileUploadController {

    @Autowired
    private CsvFileService csvFileService;

    @PostMapping("/upload-csv")
    @ApiOperation(value = "Upload CSV file", notes = "Upload a CSV file and process it to determine scholarship eligibility")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            Path updatedCsvPath = csvFileService.processCsvFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully. Updated file path: " + updatedCsvPath.toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
        }
    }

    @PostMapping("/set-criteria")
    @ApiOperation(value = "Set Eligibility Criteria", notes = "Set the criteria for determining scholarship eligibility")
    public ResponseEntity<String> setEligibilityCriteria(@RequestParam int science, @RequestParam int maths, @RequestParam int english, @RequestParam int computer) {
        csvFileService.setEligibilityCriteria(science, maths, english, computer);
        return ResponseEntity.ok("Eligibility criteria updated successfully.");
    }
}
