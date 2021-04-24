package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lotteru.builders.RaffleBuilder;
import com.lotteru.builders.RaffleNumberBuilder;
import com.lottery.dto.RaffleDto;
import com.lottery.dto.RaffleNumberDto;
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

}
