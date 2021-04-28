package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lotteru.builders.RaffleBuilder;
import com.lotteru.builders.RaffleNumberBuilder;
import com.lottery.dto.RaffleDto;
import com.lottery.dto.RaffleNumberDto;
import com.lottery.enums.RaffleStatus;
import com.lottery.exceptions.LotteryException;
import com.lottery.model.Raffle;
import com.lottery.model.RaffleNumber;
import com.lottery.model.RaffleNumberPk;
import com.lottery.model.User;
import com.lottery.repository.RaffleNumberRepository;
import com.lottery.repository.RaffleRepository;
import com.lottery.repository.UserRepository;
import com.lottery.service.RaffleService;

@Service
@Transactional
public class RaffleServiceImpl implements RaffleService {

	@Autowired
	private RaffleRepository raffleRepository;
	
	@Autowired
	private RaffleNumberRepository raffleNumberRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<RaffleDto> getAll() {
		List<Raffle> lstRaffle = raffleRepository.findAll();
		//List<RaffleNumber> lstNumberSelled = raffleNumberRepository.getByRaffleIdAndStatus();
		
		
		return RaffleBuilder.entitiesToDtos(lstRaffle);
	}

	@Override
	public RaffleDto getOne(Long id) {
		Optional<Raffle> optional = raffleRepository.findById(id);
		if(optional.isEmpty())
			return new RaffleDto();
		
		return RaffleBuilder.entityToDto(optional.get());
		
	}

	@Override
	public RaffleDto save(RaffleDto dto) {
		Raffle raffle = raffleRepository.save(RaffleBuilder.dtoToEntity(dto));
		dto.setId(raffle.getRaffleId());
		RaffleNumber rnTmp;
		List<RaffleNumber> lstRn = new ArrayList<>();
		
		for(RaffleNumberDto rnDto: dto.getRaffleNumbers()) {
			rnTmp = RaffleNumberBuilder.dtoToEntity(rnDto);
			rnTmp.getId().setRaffleId(raffle.getRaffleId());
			lstRn.add(rnTmp);
		}
		
		raffleNumberRepository.saveAll(lstRn);
		
		return dto;
	}

	@Override
	public void updateNumber(Long raffleId, Long number, String email) throws LotteryException {
		RaffleNumberPk pk = new RaffleNumberPk();
		pk.setRaffleId(raffleId);
		pk.setRaffleNumber(number);
		
		Optional<RaffleNumber> optional = raffleNumberRepository.findById(pk);
		if(optional.isEmpty())
			throw new LotteryException("Numero de rifa inexistente");

		User user = userRepository.findByEmail(email);
		if(user == null)
			throw new LotteryException("Usuario inexistente");
		
		RaffleNumber raffleNumber = optional.get();
		raffleNumber.setUser(user);
		raffleNumberRepository.saveAndFlush(raffleNumber);	
	}

	@Override
	public List<RaffleDto> getAllPercentage() {
		return raffleRepository.getWithSelledPercentage(RaffleStatus.VENDIDO.getStatus());
	}

	@Override
	public List<RaffleDto> getAllActive() {
		List<Raffle> lstRaffle = raffleRepository.findByRaffleDateAfter(Calendar.getInstance().getTime());
		List<RaffleDto> lstDtos = RaffleBuilder.entitiesToDtos(lstRaffle);
		List<RaffleNumber> lstNumber;
		for(RaffleDto dto : lstDtos) {
			lstNumber = raffleNumberRepository.findByIdRaffleIdAndStatus(dto.getId(), RaffleStatus.VENDIDO.getStatus());
			dto.setSelledPercentage(Long.valueOf(lstNumber.size()));
		}
		
		return lstDtos;
	}

	@Override
	public RaffleDto getComplete(Long id) {
		Raffle raffle = raffleRepository.getOne(id);
		RaffleDto dto = RaffleBuilder.entityToDto(raffle);

		dto.setRaffleNumbers(raffleNumberRepository.getByRaffleId(dto.getId()));
		
		dto.setSelledPercentage(Long.valueOf(dto.getRaffleNumbers().stream()
				.filter(rf -> rf.getStatus().equals(RaffleStatus.VENDIDO.getStatus()))
				.collect(Collectors.toList()).size()));
		dto.setFreePercentage(Long.valueOf(dto.getRaffleNumbers().stream()
				.filter(rf -> rf.getStatus().equals(RaffleStatus.LIBRE.getStatus()))
				.collect(Collectors.toList()).size()));
		dto.setSelectedPercentage(Long.valueOf(dto.getRaffleNumbers().stream()
				.filter(rf -> rf.getStatus().equals(RaffleStatus.APARTADO.getStatus()))
				.collect(Collectors.toList()).size()));
			
		return dto;
	}

}
