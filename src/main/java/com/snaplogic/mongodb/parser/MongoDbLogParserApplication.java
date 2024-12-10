package com.snaplogic.mongodb.parser;

import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.snaplogic.mongodb.parser.repos.LogEntriesRepository;
import com.snaplogic.mongodb.parser.service.LogFileReader;
import com.snaplogic.mongodb.parser.utils.DateUtils;


/**
 * 
 * @author bgoff
 * @since 14 Nov 2024
 */
@SpringBootApplication
@EnableMongoRepositories
public class MongoDbLogParserApplication implements CommandLineRunner
{	
	private static final Logger logger = LogManager.getLogger(MongoDbLogParserApplication.class);
	
	@Autowired
	LogEntriesRepository logEntriesRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(MongoDbLogParserApplication.class, args);
	}
	

	/**
	 * 
	 */
	@Override
	public void run(String... args) 
	{
		LogFileReader logreader = new LogFileReader(logEntriesRepo);

		Date startDate = DateUtils.rightNowDate();

		logger.debug("Running MongoDB Logfile Parser!");

		boolean failed = false;

		CommandLineParser cliParser = new DefaultParser();
		Options cliOptions = createCliOptions();
		boolean runningHelp = false;
		try 
		{
			CommandLine cli = cliParser.parse(cliOptions, args);
			if (cli.hasOption("help")) 
			{
				runningHelp = true;
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("help", cliOptions);
			} 
			else
			{
				String env = cli.getOptionValue("env");
				String fileName = cli.getOptionValue("file");
				String machine = cli.getOptionValue("machine");
				String node = cli.getOptionValue("node");
				
				
				System.out.println("processing " + machine + " mongodb log file (" + fileName + ")");
				logreader.parseLogFile(cli, "file", machine, env, node);
			}
		} 
		catch (Exception e) 
		{
			failed = true;
			logger.error("Failed to analyze log file!", e);
			e.printStackTrace();
		} 
		finally 
		{
			if (logger.isInfoEnabled()) 
			{
				StringBuilder msg = new StringBuilder(
						"It took " + DateUtils.computeDiff(startDate, DateUtils.rightNowDate()));
				if (failed)
					msg.append(" to fail, ");
				else
					msg.append(" to successfully, ");

				if (runningHelp)
					msg.append("to show the usage.");
				else
					msg.append("to analyze a log file.");
				logger.info(msg.toString());
			}
		}
	}
	
	/**
	 * (U) This method is used to create the valid options for command line usage.
	 * 
	 * @return Options for use when running via command line.
	 */
	private Options createCliOptions() {
		Options cliOptions = new Options();
		cliOptions.addOption(new Option("e", "env", true, "Environment log file is from (dev, stage, prod)"));
		cliOptions.addOption(new Option("f", "file", true, "Log file to read"));
		cliOptions.addOption(new Option("h", "help", false, "will print out the command line options."));
		cliOptions.addOption(new Option("m", "machine", true, "MongoDB Node name the log file came from."));
		cliOptions.addOption(new Option("n", "node", true, "Which node is this (SnapReplica, Main, Shard01, ...)"));
		return cliOptions;
	}
}
