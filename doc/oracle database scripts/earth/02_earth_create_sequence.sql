prompt PL/SQL Developer Export User Objects for user EARTH@ORCL
prompt Created by Administrator on 2020年5月20日
set define off
spool create_sequence.log

prompt
prompt Creating sequence SEQ_EARTH_CONFIG_ID
prompt ==================================
prompt
create sequence SEQ_EARTH_CONFIG_ID
minvalue 1
maxvalue 99999999999999999999
start with 2
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_DEPT_ID
prompt ================================
prompt
create sequence SEQ_EARTH_DEPT_ID
minvalue 1
maxvalue 99999999999999999999
start with 15
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_DICT_ID
prompt ================================
prompt
create sequence SEQ_EARTH_DICT_ID
minvalue 1
maxvalue 99999999999999999999
start with 3
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_LOG_ID
prompt ===============================
prompt
create sequence SEQ_EARTH_LOG_ID
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_LOGIN_LOG_ID
prompt =====================================
prompt
create sequence SEQ_EARTH_LOGIN_LOG_ID
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_MENU_ID
prompt ================================
prompt
create sequence SEQ_EARTH_MENU_ID
minvalue 1
maxvalue 99999999999999999999
start with 57
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_ROLE_ID
prompt ================================
prompt
create sequence SEQ_EARTH_ROLE_ID
minvalue 1
maxvalue 99999999999999999999
start with 5
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_ROLE_DEPT_ID
prompt =====================================
prompt
create sequence SEQ_EARTH_ROLE_DEPT_ID
minvalue 1
maxvalue 99999999999999999999
start with 4
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_ROLE_MENU_ID
prompt =====================================
prompt
create sequence SEQ_EARTH_ROLE_MENU_ID
minvalue 1
maxvalue 99999999999999999999
start with 108
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_USER_ID
prompt ================================
prompt
create sequence SEQ_EARTH_USER_ID
minvalue 1
maxvalue 99999999999999999999
start with 13
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EARTH_USER_ROLE_ID
prompt =====================================
prompt
create sequence SEQ_EARTH_USER_ROLE_ID
minvalue 1
maxvalue 99999999999999999999
start with 13
increment by 1
cache 20;


prompt Done
spool off
set define on
