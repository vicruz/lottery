package com.lottery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.dto.RaffleDto;
import com.lottery.service.RaffleService;

@RestController
@RequestMapping("/raffle")
public class RaffleController {

	@Autowired
	private RaffleService raffleService;
	
	@GetMapping("/all")
	public List<RaffleDto> getAll(){
		return raffleService.getAll();
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@PostMapping
	public RaffleDto save(@RequestBody RaffleDto dto) {
		return raffleService.save(dto);
	}
	
}