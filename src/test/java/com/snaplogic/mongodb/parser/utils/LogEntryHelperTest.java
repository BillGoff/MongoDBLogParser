package com.snaplogic.mongodb.parser.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import org.junit.jupiter.api.Test;


class LogEntryHelperTest {

	/**
	 * This method is used to test the parsing of the collection name form the error message of the log entry.
	 */
	@Test
	void testParseCollectionFromError() 
	{
		String errmsg = "E11000 duplicate key error collection: sldb.cc_leader index: _id_ dup key: { _id: \"5b08a9dabc9ffa002770a89d\" }";
		String expectedCollection = "sldb.cc_leader";
		try
		{
			String actualCollection = LogEntryHelper.parseCollectionFromError(errmsg);
			
			if(expectedCollection.equalsIgnoreCase(actualCollection))
				System.out.println("We actually found the correct colletion (" + expectedCollection + ")");
			else
			{	
				StringBuilder sb = new StringBuilder("Did NOT get the expected collection from (" + errmsg + ")\n");
				sb.append("	Expected " + expectedCollection + "\n");
				sb.append("	But got " + actualCollection + "\n");
				System.out.println(sb.toString());
			}
			assertTrue(expectedCollection.equalsIgnoreCase(actualCollection));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Failed to parse collection from error string!");
		}
	}
	
	/**
	 * This test case tests the generation of the expected UUID.  This ID is what we use as the "_id" field in the
	 * Mongo Database.
	 */
	@Test
	void testGenerateid()
	{
		String expectedUuid = "50a15c86-ee78-8f8f-f8c6-2f501279d87a";
		
		String logFileDateString = "2024-12-05T03:09:54.734+00:00";
		String env = "prod";
		String node = "shard01";
		String machine = "shard01_01";
		
		try
		{
			Date logEntryDate = DateUtils.toDate(logFileDateString);
			
			String actualUuid = LogEntryHelper.generateId(logEntryDate, env, node, machine);
						
			if(expectedUuid.equalsIgnoreCase(actualUuid))
				System.out.println("We generated the expected UUID(" + expectedUuid + ")");
			else
			{	
				StringBuilder sb = new StringBuilder("Did NOT generagte the expected UUID!\n");
				sb.append("	expected: " + expectedUuid + "\n");
				sb.append("	But got:  " + actualUuid + "\n");
				System.out.println(sb.toString());
			}
			assertTrue(expectedUuid.equalsIgnoreCase(actualUuid));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Failed to correctly generate the UUID!");
		}
	}

}
