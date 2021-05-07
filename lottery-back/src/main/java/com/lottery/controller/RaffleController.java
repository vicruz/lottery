package com.lottery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
	consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	//public RaffleDto save(@RequestBody RaffleDto dto) {
	public RaffleDto save(@RequestParam("dto") String dto, @RequestPart("image") MultipartFile file) {
		return raffleService.saveController(dto, file);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/image/{id}")
	public void saveImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
		raffleService.saveImage(id, file);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping//(produces = MediaType.APPLICATION_JSON_VALUE,
			//consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	//public RaffleDto update(@RequestParam("dto") String dto, @RequestPart("image") MultipartFile file) {
	public RaffleDto update(@RequestBody RaffleDto dto) {
		return raffleService.update(dto);
		//return raffleService.updateController(dto, file);
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
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@GetMapping("/status/{raffleId}/{number}")
	public void updateRaffleStatus(@PathVariable Long raffleId, @PathVariable Long number) throws LotteryException{
		raffleService.updateRaffleStatus(raffleId, number);
	}
	
}
