package com.lotteru.builders;

import java.util.ArrayList;
import java.util.List;

import com.lottery.dto.RaffleDto;
import com.lottery.model.Raffle;

public class RaffleBuilder {

	public static Raffle dtoToEntity(RaffleDto dto) {
		Raffle raffle = new Raffle();
		
		raffle.setRaffleId(dto.getId());
		raffle.setRaffleName(dto.getName());
		raffle.setRaffleDate(dto.getDate());
		raffle.setRaffleImage(dto.getImage());
		raffle.setProductDescription(dto.getDescription());
		raffle.setRafflePercentage(dto.getPercentage());
		
		return raffle;
	}
	
	public static List<Raffle> dtosToEntities(List<RaffleDto> lstDto){
		List<Raffle> lst = new ArrayList<>();
		
		for(RaffleDto dto: lstDto) {
			lst.add(dtoToEntity(dto));
		}
		
		return lst;
	}
	
	public static RaffleDto entityToDto(Raffle raffle) {
		RaffleDto dto = new RaffleDto();
		
		dto.setId(raffle.getRaffleId());
		dto.setName(raffle.getRaffleName());
		dto.setDate(raffle.getRaffleDate());
		dto.setImage(raffle.getRaffleImage());
		dto.setDescription(raffle.getProductDescription());
		dto.setPercentage(raffle.getRafflePercentage());
		
		return dto;
	}
	
	public static List<RaffleDto> entitiesToDtos(List<Raffle> lst){
		List<RaffleDto> lstDto = new ArrayList<>();
		
		for(Raffle raffle: lst) {
			lstDto.add(entityToDto(raffle));
		}
		
		return lstDto;
	}
	
	public static Raffle dtoToEntityFull(RaffleDto dto) {
		Raffle raffle = new Raffle();
		
		raffle.setRaffleId(dto.getId());
		raffle.setRaffleName(dto.getName());
		raffle.setRaffleDate(dto.getDate());
		raffle.setRaffleImage(dto.getImage());
		raffle.setProductDescription(dto.getDescription());
		raffle.setRafflePercentage(dto.getPercentage());
		raffle.setRaffleNumbers(RaffleNumberBuilder.dtosToEntities(dto.getRaffleNumbers()));
		
		return raffle;
	}
	
}
