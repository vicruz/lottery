package com.lottery.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
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
	private String contentType;
	private Long selledPercentage;
	private Long freePercentage;
	private Long selectedPercentage;
	private List<RaffleNumberDto> raffleNumbers;

	public RaffleDto(Long id, String name, Date date, Double percentage, String description, byte[] image, Long selledPercentage) {
		this.id = id;
		this.name = name;	
		this.date = date;
		this.percentage = percentage;
		this.description = description;
		this.image = image;
		this.selledPercentage = selledPercentage;
	}
	

}
