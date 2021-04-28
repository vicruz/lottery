package com.lottery.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lottery.dto.RaffleDto;
import com.lottery.model.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long> {

	@Query("select new com.lottery.dto.RaffleDto(rf.raffleId, rf.raffleName, rf.raffleDate, "
			+ "rf.rafflePercentage, rf.productDescription, rf.raffleImage, count(1)) "
			+ "from Raffle rf "
			+ "join rf.raffleNumbers rfn "
			+ "where rfn.status = :status "
			+ "group by rf.raffleId, rf.raffleName, rf.raffleDate," 
			+ "	rf.rafflePercentage, rf.productDescription")
	List<RaffleDto> getWithSelledPercentage(String status);

	List<Raffle> findByRaffleDateAfter(Date startDate);
	
}
