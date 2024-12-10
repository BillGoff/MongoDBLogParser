package com.snaplogic.mongodb.parser.repos;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.snaplogic.mongodb.parser.dtos.LogEntry;

public interface LogEntriesRepository extends MongoRepository<LogEntry, String> 
{
	
}
