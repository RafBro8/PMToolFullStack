package com.alpha.controller;


import com.alpha.domain.Project;
import com.alpha.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<Project> createNewProject(@RequestBody Project project) {
        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
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