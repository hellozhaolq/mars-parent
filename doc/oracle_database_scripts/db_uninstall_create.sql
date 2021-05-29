-- 此处set仅对当前窗口有效

SPOOL db_uninstall.sql

-- 输出域标题，缺省为on
set heading off
-- 回显本次sql命令处理的记录条数，缺省为on
set feedback off
-- 禁用替代变量，缺省为on
set define off



prompt set heading off
prompt set feedback off
prompt set define off

prompt -- Create drop type statement
prompt prompt Drop type
select 'drop type ' || object_name || ';'
  from user_objects
 where object_type = 'TYPE';

prompt -- Create drop package statement
prompt prompt Drop package
select 'drop package ' || object_name || ';'
  from user_objects
 where object_type = 'PACKAGE';

prompt -- Create drop package body statement
prompt prompt Drop package body

prompt -- Create drop database link statement
prompt prompt Drop database link
select 'drop database link ' || object_name || ';'
  from user_objects
 where object_type = 'DATABASE LINK';

prompt -- Create drop function statement
prompt prompt Drop function
select 'drop function ' || object_name || ';'
  from user_objects
 where object_type = 'FUNCTION';

prompt -- Create drop procedure statement
prompt prompt Drop procedure
select 'drop procedure ' || object_name || ';'
  from user_objects
 where object_type = 'PROCEDURE';

prompt -- Create drop trigger statement
prompt prompt Drop trigger

prompt -- Create drop view statement
prompt prompt Drop view
select 'drop view ' || view_name || ';'
  from user_views;

prompt -- Create drop constraint statement
prompt prompt Drop constraint
select 'alter table ' || table_name || ' drop constraint ' || constraint_name || ';'
  from user_constraints
 where constraint_type = 'R';

prompt -- Create drop table statement
prompt prompt Drop table
select 'drop table ' || table_name || ';'
  from user_tables;

prompt -- Create drop sequence statement
prompt prompt Drop sequence
select 'drop sequence ' || sequence_name || ';'
  from user_sequences;



spool off