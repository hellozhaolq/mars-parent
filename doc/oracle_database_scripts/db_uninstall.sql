set feedback off
set define off
spool db_uninstall.log

PROMPT
PROMPT The database is uninstalling...
PROMPT





PROMPT
PROMPT drop trigger
PROMPT

PROMPT
PROMPT drop procedure
PROMPT

PROMPT
PROMPT drop view
PROMPT

PROMPT
PROMPT drop type
PROMPT

PROMPT
PROMPT drop function
PROMPT

PROMPT
PROMPT drop constraint
PROMPT
alter table MARS_SYS_ROLE_DEPT drop constraint FK_SYS_ROLE_DEPT_DEPT_ID;
alter table MARS_SYS_ROLE_DEPT drop constraint FK_SYS_ROLE_DEPT_ROLE_ID;
alter table MARS_SYS_ROLE_MENU drop constraint FK_SYS_ROLE_MENU_MENU_ID;
alter table MARS_SYS_ROLE_MENU drop constraint FK_SYS_ROLE_MENU_ROLE_ID;
alter table MARS_SYS_USER drop constraint FK_SYS_USER_DEPT_ID;
alter table MARS_SYS_USER drop constraint FK_SYS_USER_COUNTRY_CODE;
alter table MARS_SYS_USER drop constraint FK_SYS_USER_NATION_CODE;
alter table MARS_SYS_USER drop constraint FK_SYS_USER_POLITICAL_STATUS;
alter table MARS_SYS_USER_ROLE drop constraint FK_SYS_USER_ROLE_ROLE_ID;
alter table MARS_SYS_USER_ROLE drop constraint FK_SYS_USER_ROLE_USER_ID;

PROMPT
PROMPT drop table
PROMPT
DROP TABLE MARS_STD_COUNTRY;
DROP TABLE MARS_STD_NATION;
DROP TABLE MARS_STD_POLITICAL_STATUS;
DROP TABLE MARS_SYS_DEPT;
DROP TABLE MARS_SYS_MEDIA_FILE;
DROP TABLE MARS_SYS_MENU;
DROP TABLE MARS_SYS_ROLE;
DROP TABLE MARS_SYS_ROLE_DEPT;
DROP TABLE MARS_SYS_ROLE_MENU;
DROP TABLE MARS_SYS_USER;
DROP TABLE MARS_SYS_USER_ROLE;





PROMPT
PROMPT The database is uninstalled.
PROMPT

spool off
set feedback on
set define on
prompt Done