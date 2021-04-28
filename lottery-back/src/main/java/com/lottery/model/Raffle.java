package com.lottery.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Raffle implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2350209538455567125L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RAFFLE_ID")
	private Long raffleId;
	
	@Column(name = "RAFFLE_NAME")
	private String raffleName;
	
	@Column(name = "RAFFLE_DATE")
	@Temporal(TemporalType.DATE)
	private Date raffleDate;
	
	@Column(name = "RAFFLE_PERCENTAGE")
	private Double rafflePercentage;
	
	@Column(name = "PRODUCT_DESCRIPTION")
	private String productDescription;
	
	@Column(name = "RAFFLE_IMAGE")
	private byte[] raffleImage;
	
	@OneToMany(mappedBy="raffle", fetch = FetchType.LAZY)
	private List<RaffleNumber> raffleNumbers;
	

}
