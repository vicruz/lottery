package com.lottery.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USER_ROLE")
@Getter
@Setter
public class UserRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7497405821558710676L;
	
	@EmbeddedId
	private UserRolePk id;
	

}
