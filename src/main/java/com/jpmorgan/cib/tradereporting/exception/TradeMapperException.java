package com.jpmorgan.cib.tradereporting.exception;

public class TradeMapperException extends Exception{
	public TradeMapperException(String message){
		super(message);
	}
	
	
	public TradeMapperException(Throwable cause){
		super(cause);
	}
	
	public TradeMapperException(String message,Throwable cause){
		super(message,cause);
	}
}
