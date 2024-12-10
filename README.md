# MongoDBLogParser
Command Line Arguement MongoDB Log Parser

This application is used to parse and load MongoDB Log files into a MongoDB database.  It can then be quried using MongoDBLogEvaluator.

## Prerequisites
- Open JDK 17
- Apache Maven 3.6.3 or greater installed 
- MongoDB (which can be changed in the applications.properties file).
- System Environment Variable for both the user and password to access the Mongodb.

### To create the user add something like the following to your environment.
<pre>
  export FALCON_MONGODB_EVAL_USER="bgoff" 
</pre>

### To create the passord for the user to access MongoDB where the logs will be stored, add something like the following, replace <password> with your password.
<pre>
  export FALCON_MONGODB_EVAL_PW="password"
</pre>

## To run application to get help, run the following.
<pre>
  java -jar ~/git/MongoDBLogParser/target/MongoDBLogParser-<version>.jar -h
</pre>
It will produce the following output.
<pre>
  usage: help
    -e,--env <arg>       Environment log file is from (dev, stage, prod)
    -f,--file <arg>      Log file to read
    -h,--help <arg>      will print out the command line options.
    -m,--machine <arg>   MongoDB Node name the log file came from.
    -n,--node <arg>      Which node is this (SnapReplica, Main, Shard01, ...)
</pre>

To run the application and load the LogEntry(s) into the MongoDB.
<pre>
  java -jar ~/git/MongoDBLogParser/target/MongoDBLogParser-<version>.jar -e prod -m main -n prod01 -f mongodb.log
</pre>
