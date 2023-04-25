prompt
prompt updateTime: 2020-05-11 11:37:00
prompt The 2020-05-11 11:37:00 version upgrade begins
prompt author: zhaolq
prompt message: 修改身份证号为18位随机数，避免重复
prompt
update T_BASE_USER t set t.id_number = substr(dbms_random.value,2,18);
commit;
prompt End of version 2020-05-11 11:37:00 upgrade

