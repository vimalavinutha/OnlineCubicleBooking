package com.capgemini.seetbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.seetbooking.model.Room;
import com.capgemini.seetbooking.service.RoomService;

//com.capgemini.seetbooking.controller.RoomController.java

@RestController
@RequestMapping("/api/admin/office/room")
public class RoomController {
	@Autowired
	private RoomService roomService;

	@PostMapping("/create")
	public ResponseEntity<String> createOrUpdateRoom(@RequestBody Room room) {
		return new ResponseEntity<>(roomService.createOrUpdateRoom(room), HttpStatus.OK);
	}
}
