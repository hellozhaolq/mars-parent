set heading off
set feedback off
set define off
spool db_install.log

PROMPT
PROMPT The database is initializing...
PROMPT





PROMPT
PROMPT Create table 创建表
PROMPT
@mars\table\01_MARS_STD_COUNTRY.sql
@mars\table\02_MARS_STD_NATION.sql
@mars\table\03_MARS_STD_POLITICAL_STATUS.sql
@mars\table\04_MARS_SYS_DEPT.sql
@mars\table\05_MARS_SYS_MEDIA_FILE.sql
@mars\table\06_MARS_SYS_MENU.sql
@mars\table\07_MARS_SYS_ROLE.sql
@mars\table\08_MARS_SYS_ROLE_DEPT.sql
@mars\table\09_MARS_SYS_ROLE_MENU.sql
@mars\table\10_MARS_SYS_USER.sql
@mars\table\11_MARS_SYS_USER_ROLE.sql

PROMPT
PROMPT Initialize table data 初始化表数据
PROMPT
@mars\table_data\01_MARS_STD_COUNTRY.sql
@mars\table_data\02_MARS_STD_NATION.sql
@mars\table_data\03_MARS_STD_POLITICAL_STATUS.sql
@mars\table_data\04_MARS_SYS_DEPT.sql
@mars\table_data\05_MARS_SYS_MEDIA_FILE.sql
@mars\table_data\06_MARS_SYS_MENU.sql
@mars\table_data\07_MARS_SYS_ROLE.sql
@mars\table_data\08_MARS_SYS_ROLE_DEPT.sql
@mars\table_data\09_MARS_SYS_ROLE_MENU.sql
@mars\table_data\10_MARS_SYS_USER.sql
@mars\table_data\11_MARS_SYS_USER_ROLE.sql

PROMPT
PROMPT Add foreign key constraint 添加约束 -- 此过程移到表创建中，对表创建的顺序有要求。
PROMPT

PROMPT
PROMPT Create function 创建函数
PROMPT

PROMPT
PROMPT Create type 创建type
PROMPT

PROMPT
PROMPT Create view 创建视图
PROMPT

PROMPT
PROMPT Create procedure 创建存储过程
PROMPT

PROMPT
PROMPT Create trigger 创建触发器
PROMPT





PROMPT
PROMPT The database is initialized.
PROMPT

spool off
set feedback on
set define on
prompt Done