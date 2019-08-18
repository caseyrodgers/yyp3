package com.yyp.server.exception;

public class YypException extends Exception {

	public YypException(String msg) {
		super(msg);
	}
	
	public YypException(String msg, Throwable t) {
		super(msg, t);
	}

}
