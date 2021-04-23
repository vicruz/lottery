package com.lottery.dto;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RaffleNumberDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4153331287324521206L;
	
	private Long raffleId;
	private Long number;
	private BigInteger amount;
	private String status;
	private String email;
		
}
