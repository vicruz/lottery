package com.lottery.service;

import java.util.List;

import com.lottery.dto.RaffleDto;
import com.lottery.exceptions.LotteryException;

public interface RaffleService {
	
	public List<RaffleDto> getAll();
	
	public RaffleDto getOne(Long id);
	
	public RaffleDto save(RaffleDto dto);
	
	public void updateNumber(Long raffleId, Long number, String email) throws LotteryException;
	
}
