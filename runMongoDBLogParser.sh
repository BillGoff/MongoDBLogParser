#!/bin/sh

# This shell script is used to run MongoDBLogParser over all log files within 
# a specic directory.
# Author:  Bill Goff
# date: 12 Dec 2024

echo "Enter environment (prod, uat, canary, ...) the logs are for: "
read ENV

echo "Enter mongodb cluster node (shard01, main, snapReplica, ...) the logs are for:"
read NODE

echo "Enter machine (shard01_01, prod1, snap1, ...) the logs are for: "
read MACHINE

echo "Enter full path where the logs are: "
read LOGSPATH


CMD="java -jar ./target/MongoDBLogParser-0.0.1-SNAPSHOT.jar -e "$ENV" -m "$MACHINE" -n "$NODE" -f "
echo $CMD
echo "if this looks right confirm by entering yes:"
read ANSWER

if [ $ANSWER == 'yes' ] ; then
	echo "Starting ...."
	for file in $LOGSPATH/*; do 
		if [ -f "$file" ]; then 
			echo "Starting to Process $file"
			echo "$CMD $file"
			java -jar ./target/MongoDBLogParser-0.0.1-SNAPSHOT.jar -e "$ENV" -m "$MACHINE" -n "$NODE" -f "$file"
		fi 
	done
else
	echo 'you choose to abort'
	exit 2
fi

echo "finished with " $LOGSPATH