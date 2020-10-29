prompt PL/SQL Developer Export User Objects for user MARS@192.168.0.3:1521/ORCL.ZHAOLQ.COM
prompt Created by Administrator on 2020年10月26日
set define off
spool bbb.log

prompt
prompt Creating table MARS_STD_COUNTRY
prompt ===============================
prompt
create table MARS_STD_COUNTRY
(
  code    VARCHAR2(10) not null,
  name    NVARCHAR2(50),
  name_en VARCHAR2(50)
)
;
comment on table MARS_STD_COUNTRY
  is '国家 ISO 3166-1';
comment on column MARS_STD_COUNTRY.code
  is '代码';
comment on column MARS_STD_COUNTRY.name
  is '名称';
comment on column MARS_STD_COUNTRY.name_en
  is '英文名称';
alter table MARS_STD_COUNTRY
  add constraint PK_STD_COUNTRY_CODE primary key (CODE);

prompt
prompt Creating table MARS_STD_NATION
prompt ==============================
prompt
create table MARS_STD_NATION
(
  code VARCHAR2(10) not null,
  name NVARCHAR2(50)
)
;
comment on table MARS_STD_NATION
  is '民族 GB 3304-91';
comment on column MARS_STD_NATION.code
  is '代码';
comment on column MARS_STD_NATION.name
  is '名称';
alter table MARS_STD_NATION
  add constraint PK_STD_NATION_CODE primary key (CODE);

prompt
prompt Creating table MARS_STD_POLITICAL_STATUS
prompt ========================================
prompt
create table MARS_STD_POLITICAL_STATUS
(
  code       VARCHAR2(10) not null,
  name       NVARCHAR2(50),
  name_short NVARCHAR2(50)
)
;
comment on table MARS_STD_POLITICAL_STATUS
  is '政治面貌';
comment on column MARS_STD_POLITICAL_STATUS.code
  is '代码';
comment on column MARS_STD_POLITICAL_STATUS.name
  is '名称';
comment on column MARS_STD_POLITICAL_STATUS.name_short
  is '简称';
alter table MARS_STD_POLITICAL_STATUS
  add constraint PK_STD_POLITICAL_STATUS_CODE primary key (CODE);

prompt
prompt Creating table MARS_SYS_DEPT
prompt ============================
prompt
create table MARS_SYS_DEPT
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50),
  code             VARCHAR2(50),
  remark           NVARCHAR2(100),
  type             NUMBER(1),
  subtype          VARCHAR2(20),
  parent_id        NUMBER(20),
  order_num        NUMBER(10),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status           NUMBER(1),
  del_flag         NUMBER(1)
)
;
comment on table MARS_SYS_DEPT
  is '机构管理';
comment on column MARS_SYS_DEPT.id
  is '编号';
comment on column MARS_SYS_DEPT.name
  is '机构名称';
comment on column MARS_SYS_DEPT.code
  is '机构代码';
comment on column MARS_SYS_DEPT.remark
  is '备注';
comment on column MARS_SYS_DEPT.type
  is '类型【1：集团；2：学校 】';
comment on column MARS_SYS_DEPT.subtype
  is '子类型';
comment on column MARS_SYS_DEPT.parent_id
  is '上级机构ID，一级机构为0';
comment on column MARS_SYS_DEPT.order_num
  is '排序';
comment on column MARS_SYS_DEPT.create_by
  is '创建人';
comment on column MARS_SYS_DEPT.create_time
  is '创建时间';
comment on column MARS_SYS_DEPT.last_update_by
  is '更新人';
comment on column MARS_SYS_DEPT.last_update_time
  is '更新时间';
comment on column MARS_SYS_DEPT.status
  is '状态  0：禁用   1：正常';
comment on column MARS_SYS_DEPT.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table MARS_SYS_DEPT
  add constraint PK_SYS_DEPT_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_MEDIA_FILE
prompt ==================================
prompt
create table MARS_SYS_MEDIA_FILE
(
  id               NUMBER(20) not null,
  resource_type    VARCHAR2(50),
  resource_id      NUMBER(20),
  file_type        VARCHAR2(50),
  file_content     BLOB,
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status           NUMBER(1),
  del_flag         NUMBER(1)
)
;
comment on table MARS_SYS_MEDIA_FILE
  is '图片、文件、音乐等媒体文件';
comment on column MARS_SYS_MEDIA_FILE.id
  is '编号';
comment on column MARS_SYS_MEDIA_FILE.resource_type
  is '资源类型(表名)：user-用户；';
comment on column MARS_SYS_MEDIA_FILE.resource_id
  is '资源编号';
comment on column MARS_SYS_MEDIA_FILE.file_type
  is '文件类型(字段)：avatar-头像；';
comment on column MARS_SYS_MEDIA_FILE.file_content
  is '文件内容';
comment on column MARS_SYS_MEDIA_FILE.create_by
  is '创建人';
comment on column MARS_SYS_MEDIA_FILE.create_time
  is '创建时间';
comment on column MARS_SYS_MEDIA_FILE.last_update_by
  is '更新人';
comment on column MARS_SYS_MEDIA_FILE.last_update_time
  is '更新时间';
comment on column MARS_SYS_MEDIA_FILE.status
  is '状态  0：禁用   1：正常';
comment on column MARS_SYS_MEDIA_FILE.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table MARS_SYS_MEDIA_FILE
  add constraint PK_SYS_MEDIA_FILE_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_MENU
prompt ============================
prompt
create table MARS_SYS_MENU
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50),
  code             VARCHAR2(50),
  remark           NVARCHAR2(100),
  perms            VARCHAR2(100),
  type             NUMBER(1),
  url_type         NUMBER(1),
  url              VARCHAR2(200),
  scheme           VARCHAR2(20),
  path             VARCHAR2(100),
  target           VARCHAR2(20),
  parent_id        NUMBER(20),
  order_num        NUMBER(2),
  icon             VARCHAR2(50),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status           NUMBER(1),
  del_flag         NUMBER(1)
)
;
comment on table MARS_SYS_MENU
  is '菜单管理';
comment on column MARS_SYS_MENU.id
  is '编号';
comment on column MARS_SYS_MENU.name
  is '菜单名称';
comment on column MARS_SYS_MENU.code
  is '菜单代码';
comment on column MARS_SYS_MENU.remark
  is '备注';
comment on column MARS_SYS_MENU.perms
  is '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)';
comment on column MARS_SYS_MENU.type
  is '类型   0：目录   1：菜单   2：按钮';
comment on column MARS_SYS_MENU.url_type
  is 'url类型：1.普通页面 2.嵌套服务器页面 3.嵌套完整外部页面';
comment on column MARS_SYS_MENU.url
  is '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)';
comment on column MARS_SYS_MENU.scheme
  is '路径前缀';
comment on column MARS_SYS_MENU.path
  is '请求路径';
comment on column MARS_SYS_MENU.target
  is '打开方式:_self窗口内,_blank新窗口';
comment on column MARS_SYS_MENU.parent_id
  is '父菜单ID，一级菜单为0';
comment on column MARS_SYS_MENU.order_num
  is '排序';
comment on column MARS_SYS_MENU.icon
  is '菜单图标';
comment on column MARS_SYS_MENU.create_by
  is '创建人';
comment on column MARS_SYS_MENU.create_time
  is '创建时间';
comment on column MARS_SYS_MENU.last_update_by
  is '更新人';
comment on column MARS_SYS_MENU.last_update_time
  is '更新时间';
comment on column MARS_SYS_MENU.status
  is '状态  0：禁用   1：正常';
comment on column MARS_SYS_MENU.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table MARS_SYS_MENU
  add constraint PK_SYS_MENU_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_ROLE
prompt ============================
prompt
create table MARS_SYS_ROLE
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50),
  code             VARCHAR2(50),
  remark           NVARCHAR2(100),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status           NUMBER(1),
  del_flag         NUMBER(1)
)
;
comment on table MARS_SYS_ROLE
  is '角色管理';
comment on column MARS_SYS_ROLE.id
  is '编号';
comment on column MARS_SYS_ROLE.name
  is '角色名称';
comment on column MARS_SYS_ROLE.code
  is '角色代码';
comment on column MARS_SYS_ROLE.remark
  is '备注';
comment on column MARS_SYS_ROLE.create_by
  is '创建人';
comment on column MARS_SYS_ROLE.create_time
  is '创建时间';
comment on column MARS_SYS_ROLE.last_update_by
  is '更新人';
comment on column MARS_SYS_ROLE.last_update_time
  is '更新时间';
comment on column MARS_SYS_ROLE.status
  is '状态  0：禁用   1：正常';
comment on column MARS_SYS_ROLE.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table MARS_SYS_ROLE
  add constraint PK_SYS_ROLE_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_ROLE_DEPT
prompt =================================
prompt
create table MARS_SYS_ROLE_DEPT
(
  id               NUMBER(20) not null,
  role_id          NUMBER(20),
  dept_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE
)
;
comment on table MARS_SYS_ROLE_DEPT
  is '角色机构';
comment on column MARS_SYS_ROLE_DEPT.id
  is '编号';
comment on column MARS_SYS_ROLE_DEPT.role_id
  is '角色ID';
comment on column MARS_SYS_ROLE_DEPT.dept_id
  is '机构ID';
comment on column MARS_SYS_ROLE_DEPT.create_by
  is '创建人';
comment on column MARS_SYS_ROLE_DEPT.create_time
  is '创建时间';
comment on column MARS_SYS_ROLE_DEPT.last_update_by
  is '更新人';
comment on column MARS_SYS_ROLE_DEPT.last_update_time
  is '更新时间';
alter table MARS_SYS_ROLE_DEPT
  add constraint PK_SYS_ROLE_DEPT_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_ROLE_MENU
prompt =================================
prompt
create table MARS_SYS_ROLE_MENU
(
  id               NUMBER(20) not null,
  role_id          NUMBER(20),
  menu_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE
)
;
comment on table MARS_SYS_ROLE_MENU
  is '角色菜单';
comment on column MARS_SYS_ROLE_MENU.id
  is '编号';
comment on column MARS_SYS_ROLE_MENU.role_id
  is '角色ID';
comment on column MARS_SYS_ROLE_MENU.menu_id
  is '菜单ID';
comment on column MARS_SYS_ROLE_MENU.create_by
  is '创建人';
comment on column MARS_SYS_ROLE_MENU.create_time
  is '创建时间';
comment on column MARS_SYS_ROLE_MENU.last_update_by
  is '更新人';
comment on column MARS_SYS_ROLE_MENU.last_update_time
  is '更新时间';
alter table MARS_SYS_ROLE_MENU
  add constraint PK_SYS_ROLE_MENU_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_USER
prompt ============================
prompt
create table MARS_SYS_USER
(
  id                    NUMBER(20) not null,
  account               VARCHAR2(50) not null,
  password              VARCHAR2(50),
  name                  NVARCHAR2(50),
  nick_name             NVARCHAR2(50),
  salt                  VARCHAR2(50),
  sex                   NUMBER(1),
  birthday              DATE,
  age                   NUMBER(3),
  id_number             VARCHAR2(50),
  address               NVARCHAR2(50),
  email                 VARCHAR2(50),
  mobile                VARCHAR2(50),
  entry_time            DATE,
  departure_time        DATE,
  country_code          VARCHAR2(10),
  nation_code           VARCHAR2(10),
  political_status_code VARCHAR2(10),
  user_type             VARCHAR2(10),
  identity_code         VARCHAR2(10),
  dept_id               NUMBER(20),
  create_by             NVARCHAR2(50),
  create_time           TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by        NVARCHAR2(50),
  last_update_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status                NUMBER(1),
  del_flag              NUMBER(1),
  flag                  VARCHAR2(10) default '0000000000'
)
;
comment on table MARS_SYS_USER
  is '用户管理';
comment on column MARS_SYS_USER.id
  is '编号';
comment on column MARS_SYS_USER.account
  is '账号';
comment on column MARS_SYS_USER.password
  is '密码';
comment on column MARS_SYS_USER.name
  is '用户名';
comment on column MARS_SYS_USER.nick_name
  is '昵称';
comment on column MARS_SYS_USER.salt
  is '加密盐';
comment on column MARS_SYS_USER.sex
  is '性别  1：男   2：女';
comment on column MARS_SYS_USER.birthday
  is '出生日期';
comment on column MARS_SYS_USER.age
  is '年龄';
comment on column MARS_SYS_USER.id_number
  is '身份证号';
comment on column MARS_SYS_USER.address
  is '地址';
comment on column MARS_SYS_USER.email
  is '邮箱';
comment on column MARS_SYS_USER.mobile
  is '手机号';
comment on column MARS_SYS_USER.entry_time
  is '入职时间';
comment on column MARS_SYS_USER.departure_time
  is '离职时间';
comment on column MARS_SYS_USER.country_code
  is '国家代码';
comment on column MARS_SYS_USER.nation_code
  is '民族代码';
comment on column MARS_SYS_USER.political_status_code
  is '政治面貌代码';
comment on column MARS_SYS_USER.user_type
  is '用户类型';
comment on column MARS_SYS_USER.identity_code
  is '身份代码';
comment on column MARS_SYS_USER.dept_id
  is '机构ID';
comment on column MARS_SYS_USER.create_by
  is '创建人';
comment on column MARS_SYS_USER.create_time
  is '创建时间';
comment on column MARS_SYS_USER.last_update_by
  is '更新人';
comment on column MARS_SYS_USER.last_update_time
  is '更新时间';
comment on column MARS_SYS_USER.status
  is '状态  0：禁用   1：正常';
comment on column MARS_SYS_USER.del_flag
  is '是否删除  -1：已删除  0：正常';
comment on column MARS_SYS_USER.flag
  is '【0】是否:1是0否';
alter table MARS_SYS_USER
  add constraint PK_SYS_USER_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_USER_ROLE
prompt =================================
prompt
create table MARS_SYS_USER_ROLE
(
  id               NUMBER(20) not null,
  user_id          NUMBER(20),
  role_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE
)
;
comment on table MARS_SYS_USER_ROLE
  is '用户角色';
comment on column MARS_SYS_USER_ROLE.id
  is '编号';
comment on column MARS_SYS_USER_ROLE.user_id
  is '用户ID';
comment on column MARS_SYS_USER_ROLE.role_id
  is '角色ID';
comment on column MARS_SYS_USER_ROLE.create_by
  is '创建人';
comment on column MARS_SYS_USER_ROLE.create_time
  is '创建时间';
comment on column MARS_SYS_USER_ROLE.last_update_by
  is '更新人';
comment on column MARS_SYS_USER_ROLE.last_update_time
  is '更新时间';
alter table MARS_SYS_USER_ROLE
  add constraint PK_SYS_USER_ROLE_ID primary key (ID);

prompt Done
spool off
set define on
