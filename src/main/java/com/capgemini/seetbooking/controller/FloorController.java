package com.capgemini.seetbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.seetbooking.model.Floor;
import com.capgemini.seetbooking.service.FloorService;

//com.capgemini.seetbooking.controller.FloorController.java

@RestController
@RequestMapping("/api/admin/office/floor")
public class FloorController {
	@Autowired
	private FloorService floorService;

	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody Floor floor) {

		return new ResponseEntity<>(floorService.create(floor), HttpStatus.OK);
	}

}
