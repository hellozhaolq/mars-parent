#!/bin/sh
cd ..
application_jar=`ls | grep *.jar`

xms_size=`cat config/config.ini|grep "xms_size"|cut -d"=" -f2`
xmx_size=`cat config/config.ini|grep "xmx_size"|cut -d"=" -f2`
application_port=`cat config/config.ini|grep "application_port"|cut -d"=" -f2`
env_type=`echo ${env_type}`


if [ -z ${xms_size} ];then
   echo "No config xms_size.Set the default value:512m."
   xms_size=512m
fi


if [ -z ${xmx_size} ];then
   echo "No config xmx_size.Set the default value:1024m."
   xmx_size=1024m
fi

xms="-Xms"${xms_size}
xmx="-Xmx"${xmx_size}

if [ -z ${application_port} ];then
     echo "Config error: application_port should be configured in config/config.ini."
     exit
fi

if [ -z ${env_type} ];then
     echo "Config error:env_type should be configured in enviroment valiables!"
     exit
fi 

if [ -z ${application_jar} ];then
     echo "Config error:no executable jar file to run!"
     exit
fi

echo "=====Start the application."
echo "Config:application_jar=${application_jar},port=${application_port}, xms=${xms}, xmx=${xmx}, env_type=${env_type}"

cd bin

echo "Step 1: try to stop the application:${application_jar} if it's running."
echo "---------------------------------------------------------------------------------"
sh shutdown.sh
echo "---------------------------------------------------------------------------------"

cd ..
echo "Step 2: start the aplication:${application_jar}"
pwd

application_pid=`ps -ef | grep "${application_jar}" | grep "${application_port}"| grep -v grep | awk '{ print $2 }'`
if [ ! -z ${application_pid} ];then
      echo "Application:${application_jar} is still running!Exit!"	
      exit
fi

if [ ${env_type} == "prod" ];then
    echo "nohup java -jar ${xms} ${xmx} -javaagent:/usr/local/wiseapm-java-agent/wiseapm-javaagent.jar -Dluban_clusterName=EXP_ENGINE -Dluban_clusterId=2539787 -Dspring.profiles.active=prod -Dserver.port=${application_port} -Dsoa_innernet_domain=http://oauth2.zhaolq.com $application_jar >/dev/null 2>&1 &"
    nohup java -jar ${xms} ${xmx} -javaagent:/usr/local/wiseapm-java-agent/wiseapm-javaagent.jar -Dluban_clusterName=EXP_ENGINE -Dluban_clusterId=2539787 -Dspring.profiles.active=prod -Dserver.port=${application_port} -Dsoa_innernet_domain=http://oauth2.zhaolq.com $application_jar >/dev/null 2>&1 &
else
    echo "nohup java -jar ${xms} ${xmx} -Dspring.profiles.active=${env_type} -Dserver.port=${application_port} -Dsoa_innernet_domain=http://oauth2.zhaolq.com $application_jar >/dev/null 2>&1 &"
    nohup java -jar ${xms} ${xmx} -Dspring.profiles.active=${env_type} -Dserver.port=${application_port} -Dsoa_innernet_domain=http://oauth2-beta.zhaolq.com $application_jar >/dev/null 2>&1 &
fi


echo "Step 3: try to start the monitor!"
pwd
if_need_monitor=`cat config/config.ini|grep "if_need_monitor"|cut -d"=" -f2`
if [ -z ${if_need_monitor} ];then
   if_need_monitor=true
fi

if [ ${if_need_monitor} = "false" ];then
   echo "No need monitor!"
   exit
fi

cd bin
monitor_pid=`ps -ef | grep "daemon.sh ${application_jar}:${application_port}" | grep -v grep | awk '{ print $2 }'`
echo "ps -ef | grep "daemon.sh ${application_jar}:${application_port}" | grep -v grep | awk '{ print $2 }'"
pwd
if [ -z "$monitor_pid" ]
then
	echo "nohup sh daemon.sh ${application_jar}:${application_port} >/dev/null 2>&1 &"
        nohup sh daemon.sh ${application_jar}:${application_port} >/dev/null 2>&1 &
else
    echo "Daemon is already startup.Pid is $monitor_pid"
fi
