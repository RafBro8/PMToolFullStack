package com.alpha.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationErrorService {

    public ResponseEntity<?> mapValidationService(BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error: result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}



//IMPORTANT ABOUT JAVA GENERICS!!!
//let's say you have return type public ResponseEntity<?> but inside it you want to return some String value
//you can change it to Generic type - public ResponseEntity<?> and then return return new ResponseEntity<String> inside
// Example:
// @PostMapping("")
//    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {    ---- it was previously public ResponseEntity<Project>
//
//        if (result.hasErrors()) {
//            return new ResponseEntity<String>("Invalid Project Object", HttpStatus.BAD_REQUEST);
//        }
//        Project project1 = projectService.saveOrUpdateProject(project);
//        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
//    }
//}
//with Java Generics it looks like you can return different types inside same code block


//for validation - to return key/value format like - "field":"errorMessage"
//            Map<String, String> errorMap = new HashMap<>();
//            for (FieldError error: result.getFieldErrors()) {
//                errorMap.put(error.getField(), error.getDefaultMessage());
//            }
//            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
//result from Postman:
//{
//  "description": "Project Description is required",
//  "projectName": "Project name is required",
//  "projectIdentifier": "Project Identifier is required"
//}
