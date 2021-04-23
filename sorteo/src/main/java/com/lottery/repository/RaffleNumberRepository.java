package com.lottery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lottery.model.RaffleNumber;
import com.lottery.model.RaffleNumberPk;

@Repository
public interface RaffleNumberRepository extends JpaRepository<RaffleNumber, RaffleNumberPk> {

}
