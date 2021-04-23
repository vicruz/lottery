package com.lottery.enums;

public enum RaffleStatus {

	LIBRE("libre"), VENDIDO("vendido"), APARTADO("apartado");
	 
    private String status;
 
    public String getStatus() {
        return status;
    }
 
    RaffleStatus(final String status) {
        this.status = status;
    }
}
