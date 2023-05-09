#!/bin/sh

echo "Step 1: Check if backup exist."

bin_dir=`pwd`

cd ..
current_dir=$(cd `dirname $0`; pwd)
application_name="${current_dir##*/}"
application_real_path=`pwd`

cd ..
backup_real_dir=`pwd`/${application_name}_backup
root_dir=`pwd`

if [ -d ${backup_real_dir} ];then
	cd ${backup_real_dir}
	current_backup_number=`ls -l |grep "^-"|wc -l`
	echo "The number of backup is: ${current_backup_number}"
	if [[ ${current_backup_number} -gt 0 ]];then
		echo "The backup exist."
        else
		echo "No backup,exit!"
		exit
	fi
else
	echo "No backup,exit."
	exit
fi

echo "Step 2: stop the application if it's running."
cd ${bin_dir}
sh shutdown.sh


echo "Step 3:Rename the ${application_real_path} to ${application_real_path_tmp}"
application_real_path_tmp=${application_real_path}.tmp
mv ${application_real_path} ${application_real_path_tmp}


echo "Step 4:Get the latest backup!"
cd ${backup_real_dir}

latest_backup=`ls -l | tail -1 |awk  -F" " '{print $9}'`
echo "The latest_backup is ${latest_backup}"

echo "Step 5: Copy and unzip the latest backup!"

cp ${latest_backup} ${root_dir}
cd ${root_dir}
pwd
unzip -d ${root_dir} ${latest_backup}  

echo "Step 6: clean the useless files."
rm -rf ${application_real_path_tmp}
echo ${latest_backup}
rm -rf ${latest_backup}

echo "Step 7: restart the application."

cd ${bin_dir}
sh startup.sh




