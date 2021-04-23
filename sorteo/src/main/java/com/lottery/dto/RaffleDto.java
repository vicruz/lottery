package com.lottery.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RaffleDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5887381956698827286L;
	private Long id;
	private String name;	
	private Date date;
	private Double percentage;
	private String description;
	private byte[] image;
	private List<RaffleNumberDto> raffleNumbers;


}
