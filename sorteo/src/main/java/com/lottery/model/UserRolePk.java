package com.lottery.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserRolePk implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6843177450939517198L;

	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "ROLE_ID")
	private Long roleId;

}
