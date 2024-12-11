package com.snaplogic.mongodb.parser.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snaplogic.mongodb.parser.MongoDbLogParserApplication;
import com.snaplogic.mongodb.parser.dtos.LogEntry;
import com.snaplogic.mongodb.parser.exceptions.MongoDbLogParserException;

/**
 * This class was created to hold helper functions used to generate Log Entries.
 * @author bgoff
 * @since 11 Dec 2024
 */
public class LogEntryHelper {

	private static final Logger logger = LogManager.getLogger(MongoDbLogParserApplication.class);

	private static final String valueToLookFor = "collection:";
	
	/**
	 * This method is used to generate the MongoDB _id field.  This field must be unique.  I also wanted it to be 
	 * reproducible, to make sure the same records are not loaded multiple times.
	 * @param logEntryDate Date of the log entry.
	 * @param env String value of the environment.  Typically prod, canary, uat, ...
	 * @param node String value of the MongoDB cluster node.  Typically something like shard01
	 * @param machine String value of the actual MongoDB machine.  if the node is shard01, this would be something like shard01_01
	 * @return String the unique id for this record.
	 * @throws MongoDbLogParserException in the event we are unable to produce a UUID from the supplied data.
	 */
	public static String generateId(Date logEntryDate, String env, String node, String machine) throws MongoDbLogParserException
	{
		Objects.requireNonNull(logEntryDate, "Log Entry date is required to generate a uuid!");
		Objects.requireNonNull(env, "The environment is required to generate a uuid!");
		Objects.requireNonNull(node, "The MongoDB node is required to generate a uuid!");
		Objects.requireNonNull(machine, "The MongoDB machine is required to generate a uuid!");

		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(logEntryDate.toString().getBytes(StandardCharsets.UTF_8));
			md.update(env.getBytes(StandardCharsets.UTF_8));
			md.update(node.getBytes(StandardCharsets.UTF_8));
			md.update(machine.getBytes(StandardCharsets.UTF_8));
			
			byte[] digest = md.digest();

			// Convert the first 16 bytes of the digest to a UUID
			long mostSigBits = 0;
			long leastSigBits = 0;
			
			for (int i = 0; i < 8; i++)
				mostSigBits = (mostSigBits << 8) | (digest[i] & 0xff);
         
			for (int i = 8; i < 16; i++)
				leastSigBits = (leastSigBits << 8) | (digest[i] & 0xff);
			
			UUID uuid = new UUID(mostSigBits, leastSigBits);
			return (uuid.toString());

        } 
		catch (NoSuchAlgorithmException e) 
		{
			logger.error("An error occurred while attempting to generate the UUID from the supplied data: \n" +
					"	LogEntryDate: " + DateUtils.toString(logEntryDate) + "\n" +
					"	env: " + env + "\n" +
					"	node: " + node + "\n" +
					"	machine: " + machine + "\n");
			
			throw new MongoDbLogParserException("Failed to generate UUID id", e);
		}
	}
	
	/**
	 * This method is used in the case of an error in the log entry.  It is used to pull the collection's name from 
	 * the actual error.
	 * @param error String the error that occurred.
	 * @return String the collection's name that appears in the error.
	 */
	public static String parseCollectionFromError(String error)
	{
		String parts [] = error.split(" ");
		
		int i = 0;
		
		while (i < parts.length)
		{
			if(parts[i].equalsIgnoreCase(valueToLookFor))
				break;
			i++;
		}
		if(i < parts.length)
			return parts[++i];
		return "";
	}
}
