package com.snaplogic.mongodb.parser.repos;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.snaplogic.mongodb.parser.dtos.LogEntry;

/**
 * LogEntry MongoDB Repository used to hold the LogEntries.
 * @author bgoff
 * @since 11 Dec 2024
 */
public interface LogEntriesRepository extends MongoRepository<LogEntry, String> 
{
	
}
