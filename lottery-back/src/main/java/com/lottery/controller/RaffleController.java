package com.lottery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.config.CurrentUser;
import com.lottery.dto.LocalUser;
import com.lottery.dto.RaffleDto;
import com.lottery.dto.RaffleNumberDto;
import com.lottery.exceptions.LotteryException;
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
	
	@GetMapping("/all/active")
	public List<RaffleDto> getAllActive(){
		return raffleService.getAllActive();
	}
	
	@GetMapping("/all/selled")
	public List<RaffleDto> getAllSelled(){
		return raffleService.getAllPercentage();
	}
	
	@GetMapping("/one/complete/{id}")
	public RaffleDto getOneComplete(@PathVariable Long id){
		return raffleService.getComplete(id);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping
	public RaffleDto save(@RequestBody RaffleDto dto) {
		return raffleService.save(dto);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping
	public RaffleDto update(@RequestBody RaffleDto dto) {
		return raffleService.update(dto);
	}
	
	@GetMapping("/list/{id}/{status}")
	public List<RaffleNumberDto> getByStatus(@PathVariable Long id, @PathVariable String status){
		return raffleService.getNumbersByStatus(id, status);
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@PutMapping("/assign/{raffleId}/{number}")
	public void updateNumber(@CurrentUser LocalUser user, @PathVariable Long raffleId, @PathVariable Long number) throws LotteryException{
		raffleService.updateNumber(raffleId, number, user.getUser().getId());
	}
	
}
