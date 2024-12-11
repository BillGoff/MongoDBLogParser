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
	public static String generateId(LogEntry logEntry) 
	{
		byte [] uuidBytes = concat (DateUtils.toString(logEntry.getLogEntryDate()).getBytes(StandardCharsets.UTF_8),
				logEntry.getMsgId().toString().getBytes(StandardCharsets.UTF_8),
				logEntry.getQueryHash().getBytes(StandardCharsets.UTF_8),
				logEntry.getRemote().getBytes(StandardCharsets.UTF_8),
				logEntry.getEnv().getBytes(StandardCharsets.UTF_8),
				logEntry.getNode().getBytes(StandardCharsets.UTF_8),
				logEntry.getMachine().getBytes(StandardCharsets.UTF_8));
			
		UUID uuid = UUID.nameUUIDFromBytes(uuidBytes);

		return (uuid.toString()); 
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
	
	public static byte[] concat(byte[]...arrays)
	{
	    // Determine the length of the result array
	    int totalLength = 0;
	    for (int i = 0; i < arrays.length; i++)
	    {
	        totalLength += arrays[i].length;
	    }

	    // create the result array
	    byte[] result = new byte[totalLength];

	    // copy the source arrays into the result array
	    int currentIndex = 0;
	    for (int i = 0; i < arrays.length; i++)
	    {
	        System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
	        currentIndex += arrays[i].length;
	    }

	    return result;
	}
}
