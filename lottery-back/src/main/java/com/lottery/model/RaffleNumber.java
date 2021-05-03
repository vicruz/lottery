package com.lottery.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class RaffleNumber implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6035619524824060434L;

	@EmbeddedId
	private RaffleNumberPk id;
	
	@Column(name="RAFFLE_AMOUNT")
	private BigInteger amount;
	
	@Column(name="RAFFLE_STATUS")
    private String status;
	
	@Column(name="USER_ID", insertable = false, updatable = false)
	private Long userId;
	
	@OneToOne
	@JoinColumn(name="USER_ID", referencedColumnName = "USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "RAFFLE_ID", insertable = false, updatable = false)
	private Raffle raffle;

}
