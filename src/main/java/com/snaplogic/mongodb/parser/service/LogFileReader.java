package com.snaplogic.mongodb.parser.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snaplogic.mongodb.parser.dtos.LogEntry;
import com.snaplogic.mongodb.parser.exceptions.MongoDbLogParserException;
import com.snaplogic.mongodb.parser.repos.LogEntriesRepository;

/**
 * This class is used to read the log file. It produces a Map with the unique
 * query hash as a key and a QueryMetadata object that contains details of the
 * entry.
 * 
 * @author bgoff
 * @since 7 August 2023
 */
@Component
public class LogFileReader {
	private static final Logger logger = LogManager.getLogger("MongoDBLogParser");	

	private LogEntriesRepository logEntriesRepo;
	
	public LogFileReader(LogEntriesRepository logEntriesRepo)
	{
		this.logEntriesRepo = logEntriesRepo;
	}
	
	/**
	 * (U) This method is used to do a deep dive parse into all the log entries within the log file.
	 * @param cli    CommandLine option that tells us are parsing a file.
	 * @param option String value that tells us the name of the file we are parsing.
	 * @param machine String value of the machine this log file is for.	
	 * @return List of all the LogEntries (queries/inserts/updates) pulled from the log file.
	 * @throws MongoDbLogParserException if we run into an issue parsing the log file.
	 */
	public void parseLogFile(CommandLine cli, String option, String machine, String env, String node)
			throws MongoDbLogParserException 
	{
		
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		int counter = 0;
		
		File logFile = getFile(cli, option);
				
		try {
			LogEntry logEntry = null;
			ObjectMapper mapper = new ObjectMapper();

			try (BufferedReader br = new BufferedReader(new FileReader(logFile))) 
			{
				String line;
				while ((line = br.readLine()) != null) 
				{
					try 
					{	
						if ((line != null) && (line.length() > 0))
						{
							logEntry = mapper.readValue(line, LogEntry.class);
							if (logEntry.getQueryHash() != null) 
							{
								logEntry.setMachine(machine);
								logEntry.setEnv(env);
								logEntry.setNode(node);
								logEntries.add(logEntry);
							}
						}
						if(logEntries.size() == 10)
						{
							logEntriesRepo.saveAll(logEntries);
							counter = counter + logEntries.size();
							logEntries = new ArrayList<LogEntry>();
						}
					}
					catch(Exception e)
					{
						logger.error("Failed to parse line: " + line, e);
					}
				}
				//Don't forget to save the last entries.
				if(logEntries.size() > 0)
				{
					logEntriesRepo.saveAll(logEntries);
					counter = counter + logEntries.size();
				}
			}
		} 
		catch (IOException e) 
		{
			logger.error("Unable to parse log file!", e);
			throw new MongoDbLogParserException("Unable to parse log file!");
		}
		finally
		{
			if(logger.isInfoEnabled())
				logger.info("We have just added " + counter + " log entries to the database.");
		}
	}
	
	/**
	 * (U) This private static method is used to get a handle to the file supplied via the Command Line Interface 
	 * options.
	 * @param cli Command Line Interface 
	 * @param option String value of the option that identifies the file we are attempting to read from.
	 * @return File a handle to the file we are attempting to read from.
	 * @throws MongoDbLogParserException if we are unable to read from the file.
	 */
	private static File getFile(CommandLine cli, String option) throws MongoDbLogParserException {
		File file = null;
		
		if (cli.hasOption(option)) 
		{
			String fileName = cli.getOptionValue(option);

			if (logger.isDebugEnabled())
				logger.debug("Attempting to read log file (" + fileName + ")");

			if ((fileName != null) && (fileName.trim().length() > 0)) 
			{
				file = new File(fileName);
				if (!file.exists()) 
					throw new MongoDbLogParserException("File(" + fileName + ") does NOT exist!");
				if (!file.canRead())							
					throw new MongoDbLogParserException("Unable to read MongoDB log file.  Check your permissions " +
							"for file(" + fileName + ").");
			}
			else
				throw new MongoDbLogParserException("No file name priveded.");
		} else
			throw new MongoDbLogParserException("You must provide a file for us to read from!");
		return file;
	}
	
	/**
	 * (U) This method is used to parse a line into a LogEntry.  For debug and testing purposes this is broken out.
	 * @param line String to parse into a log entry object.
	 * @return LogEntry parsed from the json String passed in.
	 * @throws JsonMappingException in the event we are unable to parse the String into the Object.
	 * @throws JsonProcessingException in the event we are unable to parse the String into the Object.
	 */
	public static LogEntry parseLogEntry(String line) //throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();

		LogEntry logEntry = null;
		try {
			if((line != null) && (line.length() > 0))
				logEntry = mapper.readValue(line, LogEntry.class);
		}
		catch(Exception e)
		{
			logger.error("Unable to parse log file at : " + line, e);
		}
		
		return logEntry;
	}
}
