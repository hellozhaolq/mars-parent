#!/bin/sh

cd ..
application_jar=`ls | grep *.jar`
application_port=$(cat config/config.ini|grep "application_port"|cut -d"=" -f2)

echo "======Stop the application:${application_jar}"

echo "Config:application_jar=${application_jar} application_port=${application_port}"

if [ -z "${application_jar}" -o -z "${application_port}" ];then
   echo "Arguments is missing.Exit!"
   exit
fi

echo "Step 1: stop the monitor"

monitor_pid=$(ps -ef | grep "daemon.sh ${application_jar}:${application_port}" | grep -v grep | awk '{ print $2 }')
if [ ! -z ${monitor_pid} ];then
   echo "The monitor thread is:${monitor_pid}"
   kill -9 ${monitor_pid}

   echo "Success kill the daemon.sh thread."
fi

echo "Step 2: stop the application:${application_jar}"

count=1

while [ true ]
do
    echo "The ${count} times trying to stop the application."

    application_pid=`ps -ef | grep "${application_jar}" | grep "${application_port}"| grep -v grep | awk '{ print $2 }'`
    if [ -z ${application_pid} ];then
	    	echo "The application:${application_jar}:${applicaation_port} is not running!"
		    echo "Success stopping the application."
  		    exit
    elif [ ${count} -lt 3 ];then
            echo "kill ${application_pid}.The ${count} time."
			kill ${application_pid}
    else
            echo "kill -9 ${application_pid}"
			kill -9 ${application_pid}
	fi

	count=`expr ${count} + 1`
	sleep 5
done

application_pid=`ps -ef | grep "${application_jar}" | grep "${application_port}"| grep -v grep | awk '{ print $2 }'`
if [ -z ${application_pid} ];then
    echo "Success to stop the application:${application_jar}"
fi


