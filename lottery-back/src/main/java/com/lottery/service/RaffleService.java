package com.lottery.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lottery.dto.RaffleDto;
import com.lottery.dto.RaffleNumberDto;
import com.lottery.exceptions.LotteryException;

public interface RaffleService {
	
	public List<RaffleDto> getAll();
	
	public List<RaffleDto> getAllActive();
	
	public List<RaffleDto> getAllPercentage();
	
	public RaffleDto getOne(Long id);
	
	public RaffleDto save(RaffleDto dto);
	
	public RaffleDto saveController(String value, MultipartFile image);
	
	public void updateNumber(Long raffleId, Long number, Long userId) throws LotteryException;
	
	public RaffleDto getComplete(Long id);
	
	public RaffleDto update(RaffleDto dto);
	
	public RaffleDto updateController(String value, MultipartFile image);
	
	public List<RaffleNumberDto> getNumbersByStatus(Long id, String status);
	
	public void saveImage(Long raffleId, MultipartFile file);
	
	public void updateRaffleStatus(Long raffleId, Long number);
	
}
