package com.lottery.exceptions;

public class LotteryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6280152477728574414L;

	public LotteryException(String message) {
		super(message);
	}
	
	public LotteryException(String message, Throwable cause) {
        super(message, cause);
	}
}
