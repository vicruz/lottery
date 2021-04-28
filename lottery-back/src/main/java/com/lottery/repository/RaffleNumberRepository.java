package com.lottery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lottery.dto.RaffleNumberDto;
import com.lottery.model.RaffleNumber;
import com.lottery.model.RaffleNumberPk;

@Repository
public interface RaffleNumberRepository extends JpaRepository<RaffleNumber, RaffleNumberPk> {

	List<RaffleNumber> findByIdRaffleIdAndStatus(Long raffleId, String status);
	
	List<RaffleNumber> findByIdRaffleId(Long raffleId);
	
	@Query("select new com.lottery.dto.RaffleNumberDto(rf.id.raffleId, rf.id.raffleNumber, rf.amount, "
			+ "rf.status, usr.email) "
			+ "from RaffleNumber rf "
			+ "left join rf.user usr "
			+ "where rf.id.raffleId = :raffleId")
	List<RaffleNumberDto> getByRaffleId(Long raffleId);
	
}
