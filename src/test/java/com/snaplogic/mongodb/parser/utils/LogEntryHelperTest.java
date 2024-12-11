package com.snaplogic.mongodb.parser.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

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
//	void testGenerateid()
//	{
//		String expectedUuid = "50a15c86-ee78-8f8f-f8c6-2f501279d87a";
//		
//		String logFileDateString = "2024-12-05T03:09:54.734+00:00";
//
//		String env = "prod";
//		String node = "shard01";
//		String machine = "shard01_01";
//		
//		try
//		{
//			String dateString  = "2024-12-04T22:09:54.734+00:00";
//			String dateString2 = "2024-12-04T22:09:54.994+00:00";
//			
//			Date date1 = DateUtils.toDate(dateString);
//			Date date2 = DateUtils.toDate(dateString2);
//			
//			System.out.println(DateUtils.toString(date1));
//			System.out.println(DateUtils.toString(date2));
//			
//			byte [] uuid1Bytes = concat (DateUtils.toString(date1).getBytes(StandardCharsets.UTF_8), 
//					env.getBytes(StandardCharsets.UTF_8),
//					node.getBytes(StandardCharsets.UTF_8),
//					machine.getBytes(StandardCharsets.UTF_8));
//			
//			UUID uuid1 = UUID.nameUUIDFromBytes(uuid1Bytes);
//			
//			byte [] uuid2Bytes = concat (DateUtils.toString(date2).getBytes(StandardCharsets.UTF_8), 
//					env.getBytes(StandardCharsets.UTF_8),
//					node.getBytes(StandardCharsets.UTF_8),
//					machine.getBytes(StandardCharsets.UTF_8));
//			
//			UUID uuid2 = UUID.nameUUIDFromBytes(uuid2Bytes);
//
//			System.out.println(uuid1.toString());
//			System.out.println(uuid2.toString());
//			
//			if(uuid1.equals(uuid2))
//				System.out.println("Nope");
//
//			
//
//			Date logEntryDate = DateUtils.toDate(logFileDateString);
//			
//			String actualUuid = LogEntryHelper.generateId(logEntryDate, env, node, machine);
//						
//			if(expectedUuid.equalsIgnoreCase(actualUuid))
//				System.out.println("We generated the expected UUID(" + expectedUuid + ")");
//			else
//			{	
//				StringBuilder sb = new StringBuilder("Did NOT generagte the expected UUID!\n");
//				sb.append("	expected: " + expectedUuid + "\n");
//				sb.append("	But got:  " + actualUuid + "\n");
//				System.out.println(sb.toString());
//			}
//			assertTrue(expectedUuid.equalsIgnoreCase(actualUuid));
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			fail("Failed to correctly generate the UUID!");
//		}
//	}
	
	public byte[] concat(byte[]...arrays)
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
