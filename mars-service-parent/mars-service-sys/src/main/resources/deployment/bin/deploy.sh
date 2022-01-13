#!/bin/sh

package=firstmanagementdataservice.zip
relative_path=firstmanagementdataservice

root_dir=${HOME}/${relative_path}
app_name=$(echo $package | sed 's/\.[^.]*$//')
app_root_dir=${root_dir}/${app_name}
app_bin_dir=${app_root_dir}/bin
app_config_dir=${app_root_dir}/config
app_backup_shell=${app_bin_dir}/backup.sh
app_shutdown_shell=${app_bin_dir}/shutdown.sh

# kill old server
#cd /data01/pool/showcase/it_animation;
#sh boot.sh kill;

# load env variables
source /etc/profile

cd ${deploy_dir}

if [ ! -d ${root_dir} ];then
       mkdir -p ${root_dir}
       if [ $? -ne 0 ]; then
            echo "Make directory failed!"
            exit
      else
            echo "Make directory success"
      fi
fi

cp ${package} ${root_dir}

if [ -f ${app_backup_shell} ];then
      echo "Execute backup shell"
      cd ${app_bin_dir}
      sh backup.sh
      if [ $? -ne 0 ]; then
            echo "Backup failed"
            exit
      else
            echo "Backup succeed"
      fi
fi

if [ -f ${app_shutdown_shell} ];then
      echo "Execute shutdown shell"
      cd ${app_bin_dir}
      sh shutdown.sh
      if [ $? -ne 0 ]; then
            echo "Shutdown failed"
            exit
      else
            echo "Shutdown succeed"
      fi
fi

rm -rf ${app_root_dir}

cd ${root_dir}

unzip -o ${package}
if [ $? -ne 0 ]; then
	echo "Unzip package fail"
	exit
fi

rm -rf ${package}

cd  ${app_bin_dir}
chmod +x *.sh

echo "Convert config.ini file to unix format from dosformat."
cd ${app_config_dir}
if [ -f config.ini ]; then
	vi +':w ++ff=unix' +':q' config.ini >/dev/null 2>&1
fi

echo "Convert sh file to unix format from dosformat."
cd ${app_bin_dir}
for file in `ls ${app_bin_dir}/*.sh`
do
      vi +':w ++ff=unix' +':q' ${file} >/dev/null 2>&1
done
#start the demo_hjt
sh startup.sh

echo "Done!!!"
sleep 1
