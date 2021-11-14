package com.lottery.builders;

import java.util.ArrayList;
import java.util.List;

import com.lottery.dto.RaffleNumberDto;
import com.lottery.model.RaffleNumber;
import com.lottery.model.RaffleNumberPk;

public class RaffleNumberBuilder {

	public static RaffleNumberDto entityToDto(RaffleNumber entity) {
		RaffleNumberDto dto = new RaffleNumberDto();
		
		dto.setAmount(entity.getAmount());
		dto.setNumber(entity.getId().getRaffleNumber());
		dto.setRaffleId(entity.getId().getRaffleId());
		dto.setStatus(entity.getStatus());
		
		return dto;
	}
	
	public static List<RaffleNumberDto> entitiesToDtos(List<RaffleNumber> lst){
		List<RaffleNumberDto> lstDto = new ArrayList<>();
		
		for(RaffleNumber entity : lst ) {
			lstDto.add(entityToDto(entity));
		}
		
		return lstDto;
	}
	
	public static RaffleNumber dtoToEntity(RaffleNumberDto dto) {
		RaffleNumber raffle = new RaffleNumber();
		RaffleNumberPk pk = new RaffleNumberPk();
		
		pk.setRaffleId(dto.getRaffleId());
		pk.setRaffleNumber(dto.getNumber());
		raffle.setId(pk);
		raffle.setAmount(dto.getAmount());
		raffle.setStatus(dto.getStatus());
		
		return raffle;
	}
	
	public static List<RaffleNumber> dtosToEntities(List<RaffleNumberDto> lstDto){
		List<RaffleNumber> lst = new ArrayList<>();
		for(RaffleNumberDto dto: lstDto) {
			lst.add(dtoToEntity(dto));
		}
		return lst;
	}
	
	
	
}
