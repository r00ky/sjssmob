package com.apcmob.exception;

/**
 * 
 * 
 * @author Jinoos Lee
 * @since 2013. 4. 19.
 * @version $Revision$
 */

public class LoginFailedException extends Exception {
	private static final long serialVersionUID = -4343132749564517262L;

	private String loginId = "";

	public LoginFailedException(String message) {
		super(message);
	}

	public LoginFailedException(String message, Throwable e) {
		super(message, e);
	}

}
