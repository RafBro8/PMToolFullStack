package com.alpha;

import com.alpha.domain.Project;
import com.alpha.service.ProjectService;
import com.alpha.service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = validationErrorService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);

    }
}

//ResponseEntity allows us to have more control on our JSON Responses. Allows to control response statuses and JSON data response

//To Test createNewProject POST Method in Postman, need to go to Postman, set method to POST, put in URL http://localhost:8080/api/project
//then go to Body -> select raw -> select JSON format from Dropdown -> put in your JSON data like:

//{
//	"projectName": "TEST",
//	"projectIdentifier": "TEST_ID",
//	"description": "Testing POST for Project"
//
//}

//get Response like this:

//{
//  "id": 1,
//  "projectName": "TEST",
//  "projectIdentifier": "TEST_ID",
//  "description": "Testing POST for Project",
//  "startDate": null,
//  "endDate": null,
//  "createdAt": null,
//  "updatedAt": null
//}

//then check H2 database to verify - http://localhost:8080/h2-console
//SELECT * FROM PROJECT -> Run

//Should get:

//SELECT * FROM PROJECT;
//ID  	CREATED_AT  	            DESCRIPTION  	           END_DATE  	PROJECT_IDENTIFIER  	PROJECT_NAME  	START_DATE  	UPDATED_AT
//1	     2019-01-14 17:41:32.004	Testing POST for Project	null	    TEST_ID	                TEST	        null	        null
//(1 row, 4 ms)

//For Validation in Project.java file - if you want something better looking than 500 Error with ugly stack trace - use @Valid annotation
//in createNewProject method above - which will give you 400 Bad Request with much better descriptions
//To simplify the validation response use BindingResult result which will just show message like "Invalid Project Object" that you can specify


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

// @GetMapping("/{projectId") and (@PathVariable String projectId) need to match completely