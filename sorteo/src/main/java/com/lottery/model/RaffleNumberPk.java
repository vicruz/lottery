package com.lottery.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class RaffleNumberPk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3774427199601898983L;

	@Column(name="RAFFLE_ID")
	private Long raffleId;
	
	@Column(name="RAFFLE_NUMBER")
	private Long raffleNumber;
	
	
	
}
