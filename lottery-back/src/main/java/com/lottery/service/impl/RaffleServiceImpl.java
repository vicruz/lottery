package com.lottery.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.Deflater;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.lottery.service.EmailService;
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
	
	@Autowired
	private EmailService emailService;
	
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
	public void updateNumber(Long raffleId, Long number, Long userId) throws LotteryException {
		RaffleNumberPk pk = new RaffleNumberPk();
		pk.setRaffleId(raffleId);
		pk.setRaffleNumber(number);
		
		Raffle raffle = raffleRepository.getOne(raffleId);
		
		Optional<RaffleNumber> optional = raffleNumberRepository.findById(pk);
		if(optional.isEmpty())
			throw new LotteryException("Numero de rifa inexistente");

		User user = userRepository.getOne(userId);
		if(user == null)
			throw new LotteryException("Usuario inexistente");
		
		RaffleNumber raffleNumber = optional.get();
		raffleNumber.setStatus(RaffleStatus.APARTADO.getStatus());
		raffleNumber.setUser(user);
		raffleNumberRepository.saveAndFlush(raffleNumber);
		
		emailService.sendMimeMessageJoinRaffle(user.getEmail(), "Sorteo " + raffle.getRaffleName(), user.getDisplayName(), 
				raffle.getRaffleName(), number.intValue(), ""+raffleNumber.getAmount());
	}

	@Override
	public List<RaffleDto> getAllPercentage() {
		return raffleRepository.getWithSelledPercentage(RaffleStatus.VENDIDO.getStatus());
	}

	@Override
	public List<RaffleDto> getAllActive() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		List<Raffle> lstRaffle = raffleRepository.findByRaffleDateAfter(cal.getTime());
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

	@Override
	@Transactional
	public RaffleDto update(RaffleDto dto) {
		//Update Raffle
		Raffle raffle = raffleRepository.getOne(dto.getId());
		raffle.setRaffleName(dto.getName());
		raffle.setRaffleDate(dto.getDate());
		//raffle.setRaffleImage(dto.getImage());
		raffle.setProductDescription(dto.getDescription());
		raffle.setRafflePercentage(dto.getPercentage());

		//Update RaffleNumbers
		List<RaffleNumber> lst = raffleNumberRepository.findByIdRaffleId(dto.getId());
		RaffleNumberDto rnDto;
		int idx = 0;
		
		for(RaffleNumber rn : lst) {
			rnDto = dto.getRaffleNumbers().get(idx);
			if(!rn.getStatus().equalsIgnoreCase(rnDto.getStatus())) {
				rn.setStatus(rnDto.getStatus());
			}
			
			if(rn.getAmount().compareTo(rnDto.getAmount())!=0) {
				rn.setAmount(rnDto.getAmount());
			}
			
			if(rn.getUserId()!=null && 
					(rnDto.getEmail()==null || rnDto.getEmail().equals(""))) {
				rn.setUser(null);
			}
			
			idx++;
		}
		
		raffleNumberRepository.saveAll(lst);
		
		return dto;
	}

	@Override
	public List<RaffleNumberDto> getNumbersByStatus(Long id, String status) {
		List<RaffleNumber> lstNumber;
		
		lstNumber = raffleNumberRepository.findByIdRaffleIdAndStatus(id, status);
		if(lstNumber.isEmpty())
			return new ArrayList<>();
		
		return RaffleNumberBuilder.entitiesToDtos(lstNumber);
	}

	@Override
	public RaffleDto saveController(String value, MultipartFile image) {
		
		RaffleDto dto = new RaffleDto();
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			dto = objectMapper.readValue(value, RaffleDto.class);

			dto.setContentType(image.getContentType());				
			dto.setImage(convertToBytes(image.getBytes()));
			
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
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public RaffleDto updateController(String value, MultipartFile image) {
		
		RaffleDto dto = new RaffleDto();
		Deflater deflater = new Deflater();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			dto = objectMapper.readValue(value, RaffleDto.class);
			
			//Update Raffle
			Raffle raffle = raffleRepository.getOne(dto.getId());
			raffle.setRaffleName(dto.getName());
			raffle.setRaffleDate(dto.getDate());
			raffle.setRaffleImage(dto.getImage());
			raffle.setProductDescription(dto.getDescription());
			raffle.setRafflePercentage(dto.getPercentage());
			
			if(image!=null) {
				deflater.setInput(image.getBytes());
				deflater.finish();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.getBytes().length);
				byte[] buffer = new byte[1024];
				while (!deflater.finished()) {
					int count = deflater.deflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
				
				dto.setContentType(image.getContentType());				
				dto.setImage(outputStream.toByteArray());
				raffle.setRaffleImage(dto.getImage());
				raffle.setContentType(image.getContentType());
			}
			
			//Update RaffleNumbers
			List<RaffleNumber> lst = raffleNumberRepository.findByIdRaffleId(dto.getId());
			RaffleNumberDto rnDto;
			int idx = 0;
			
			for(RaffleNumber rn : lst) {
				rnDto = dto.getRaffleNumbers().get(idx);
				if(!rn.getStatus().equalsIgnoreCase(rnDto.getStatus())) {
					rn.setStatus(rnDto.getStatus());
				}
				
				if(rn.getAmount().compareTo(rnDto.getAmount())!=0) {
					rn.setAmount(rnDto.getAmount());
				}
				
				if(rn.getUserId()!=null && 
						(rnDto.getEmail()==null || rnDto.getEmail().equals(""))) {
					rn.setUser(null);
				}
				
				idx++;
			}
			
			raffleNumberRepository.saveAll(lst);
		}catch(IOException ex) {
			
		}
		
				
		return dto;
	}

	@Override
	public void saveImage(Long raffleId, MultipartFile file) {
		
		if(file == null) return;
		
		Raffle raffle = raffleRepository.getOne(raffleId);
		raffle.setContentType(file.getContentType());
		try {
			raffle.setRaffleImage(convertToBytes(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private byte[] convertToBytes(byte[] data){

		Deflater deflater = new Deflater();
		ByteArrayOutputStream outputStream = null;
		
		try {
			if(data!=null && data.length>0) {
				deflater.setInput(data);
				deflater.finish();
				outputStream = new ByteArrayOutputStream(data.length);
				byte[] buffer = new byte[1024];
				while (!deflater.finished()) {
					int count = deflater.deflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
				
			}
			
			return outputStream.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@Override
	public void updateRaffleStatus(Long raffleId, Long number) {
		Raffle raffle = raffleRepository.getOne(raffleId);
		raffle.setRaffleStatus(RaffleStatus.FINALIZADO.getStatus());
		raffle.setRaffleWinner(number);
		
		List<RaffleNumberDto> lst = raffleNumberRepository.getByRaffleId(raffleId);
		Set<String> lstBbc = new HashSet<>();
		String mailWinner = "";
		
		for(RaffleNumberDto dto: lst) {
			if(dto.getStatus().equals(RaffleStatus.VENDIDO.getStatus())) {
				if(dto.getNumber().equals(number)) {
					mailWinner = dto.getEmail();
				}else {
					lstBbc.add(dto.getEmail());
				}				
			}
		}
		
		//remove mailWinner from list (this is when winner buys more than 1 number)
		lstBbc.remove(mailWinner);
		lstBbc.remove(Strings.EMPTY);
		
		raffleRepository.save(raffle);
		/*
		emailService.sendMessageWinner(mailWinner, "Ganador del sorteo " +  raffle.getRaffleName(), raffle.getRaffleName());
		emailService.sendMessageFinished("no-reply@lottery.com", "Sorteo " +  raffle.getRaffleName() + " finalizado", 
				raffle.getRaffleName(), String.join(",", lstBbc));
		*/
		String arr[] = new String[lstBbc.size()];
		lstBbc.toArray(arr);
		emailService.sendMimeMessageWinner(mailWinner, "Ganador del sorteo " +  raffle.getRaffleName(), raffle.getRaffleName());
		if(!lstBbc.isEmpty())
			emailService.sendMimeMessageFinished("no-reply@lottery.com", "Sorteo " +  raffle.getRaffleName() + " finalizado", 
					raffle.getRaffleName(), arr);
	}
	
}
