package com.lottery.enums;

public enum RaffleStatus {

	LIBRE("Libre"), VENDIDO("Vendido"), APARTADO("Apartado"), ACTIVO("Activo"), FINALIZADO("Finalizado");
	 
    private String status;
 
    public String getStatus() {
        return status;
    }
 
    RaffleStatus(final String status) {
        this.status = status;
    }
}
