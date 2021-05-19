package com.lotteru.builders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

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
		raffle.setContentType(dto.getContentType());
		raffle.setRaffleStatus(dto.getStatus());
		raffle.setRaffleWinner(dto.getWinner());
		
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
		//dto.setImage(raffle.getRaffleImage());
		dto.setDescription(raffle.getProductDescription());
		dto.setPercentage(raffle.getRafflePercentage());
		dto.setContentType(raffle.getContentType());
		dto.setStatus(raffle.getRaffleStatus());
		dto.setWinner(raffle.getRaffleWinner());
		
		
		if(raffle.getRaffleImage()!=null && raffle.getRaffleImage().length > 0) {
			Inflater inflater = new Inflater();
			inflater.setInput(raffle.getRaffleImage());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(raffle.getRaffleImage().length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				dto.setImage(outputStream.toByteArray());
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}			
		}
		
		
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
		raffle.setRaffleWinner(dto.getWinner());
		raffle.setRaffleNumbers(RaffleNumberBuilder.dtosToEntities(dto.getRaffleNumbers()));
		
		return raffle;
	}
	
}
