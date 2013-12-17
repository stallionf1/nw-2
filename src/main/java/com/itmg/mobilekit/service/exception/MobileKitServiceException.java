package com.itmg.mobilekit.service.exception;

public class MobileKitServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 600534791125379671L;

	public MobileKitServiceException(String message) {
        super(message);
    }

    public MobileKitServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
	
}
