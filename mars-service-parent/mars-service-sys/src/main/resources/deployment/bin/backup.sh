#!/bin/sh
bin_dir=`pwd`

cd ..

current_dir=$(cd `dirname $0`; pwd)
application_name="${current_dir##*/}"
application_real_path=`pwd`

cd ..

backup_real_dir=`pwd`/${application_name}_backup


config_file_path=${application_real_path}/config/config.ini

backup_number=`cat $config_file_path|grep "backup_number="|cut -d"=" -f2`


if [ ! -d ${backup_real_dir} ];then
   echo "Backup directory doesn't exist!Make backup directory!"
   mkdir ${backup_real_dir}
else
   echo "Backup directory exists!"
fi


if [ ! -d ${application_real_path} ];then
   echo "Application path doesn't exist!Do nothing!"
   exit
fi

echo "Start to backup application ${application_real_name}."


echo "Step 1: copy  current application{${application_real_path}} to backup directory{${backup_real_dir}}"

if [ -d ${backup_real_dir}/${application_name} ];then
   echo "-----Remove ${backup_real_dir}/${application_name}"
   rm -rf ${backup_real_dir}/${application_name}
fi

echo "-----Copy files!"
cp -R ${application_real_path} ${backup_real_dir}

echo "Step 2: remove all log files:${backup_real_dir}/${application_name}/logs!"

rm -rf ${backup_real_dir}/${application_name}/logs


echo "Step 3: zip files:${backup_real_dir}/${application_name}"
cd ${backup_real_dir}
zip -r  ${backup_real_dir}/${application_name}-`date +%Y%m%d%H%M`.zip ${application_name}


echo "Step 4: remove ${backup_real_dir}/${application_name}"
rm -rf ${backup_real_dir}/${application_name}

echo "Step 5: remove the earliest backup if the number of copies of the backup is greater than ${backup_number}"
cd ${backup_real_dir}
current_backup_number=`ls -l |grep "^-"|wc -l`
echo "The number of backup is: ${current_backup_number}"
if [[ ${current_backup_number} -gt ${backup_number} ]];then
   earliestBackup=`ls -l |grep "^-" | head -1 |awk  -F" " '{print $9}'`
   echo "remove the earliest backup: "${backup_real_dir}/${earliestBackup}
   rm ${backup_real_dir}/${earliestBackup}
fi


exit
