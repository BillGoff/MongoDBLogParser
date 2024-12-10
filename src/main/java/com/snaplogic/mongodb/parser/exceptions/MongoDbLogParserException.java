package com.snaplogic.mongodb.parser.exceptions;

/**
 * This exception class is the upper level exception class for the
 * MongoDbLogParser application.
 * 
 * @author bgoff
 * @since 14 Nov 2024
 */
public class MongoDbLogParserException extends Exception {

	private static final long serialVersionUID = -9220338316468109337L;

	/**
	 * (U) Constructs a new MongoDbLogParserException with null as its details.
	 */
	public MongoDbLogParserException() {
	}

	/**
	 * (U) Constructs a new MongoDbLogParserException with the specified detail
	 * message.
	 *
	 * @param message String value to set the message to.
	 */
	public MongoDbLogParserException(String message) {
		super(message);
	}

	/**
	 * (U) Constructs a new MongoDbLogParserException with the specified detail
	 * message and cause.
	 *
	 * @param message String value to set the message to.
	 * @param cause   Throwable class to set the cause to.
	 */
	public MongoDbLogParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * (U) Constructs a new MongoDbLogParserException wit the specified detail
	 * message, cause, suppression flag set to either enabled or disabled, and the
	 * writable stack trace flag set to either enable or disabled.
	 *
	 * @param message             String value to set the message to.
	 * @param cause               Throwable class to set the cause to.
	 * @param enableSuppression   boolean used to set the enabled suppression flag
	 *                            to.
	 * @param writeableStackTrace boolean used to set the write able stack trace
	 *                            flag to.
	 */
	public MongoDbLogParserException(String message, Throwable cause, boolean enableSuppression,
			boolean writeableStackTrace) {
		super(message, cause, enableSuppression, writeableStackTrace);
	}

	/**
	 * (U) Constructs a new MongoDbLogParserException with the cause set.
	 * 
	 * @param cause Throwable class to set the cause to.
	 */
	public MongoDbLogParserException(Throwable cause) {
		super(cause);
	}
}
