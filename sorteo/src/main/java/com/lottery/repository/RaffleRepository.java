package com.lottery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lottery.model.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long> {

}
