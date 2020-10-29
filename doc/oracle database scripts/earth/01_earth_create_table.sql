prompt PL/SQL Developer Export User Objects for user EARTH@ORCL
prompt Created by Administrator on 2020年5月20日
set define off
spool create_table.log

prompt
prompt Creating table TAB_EARTH_CONFIG
prompt ===============================
prompt
create table TAB_EARTH_CONFIG
(
  id               NUMBER(20) not null,
  value            NVARCHAR2(100) not null,
  label            NVARCHAR2(100) not null,
  type             NVARCHAR2(100) not null,
  description      NVARCHAR2(100) not null,
  sort             NUMBER not null,
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE,
  remarks          NVARCHAR2(255),
  del_flag         NUMBER(4)
)
;
comment on table TAB_EARTH_CONFIG
  is '系统配置表';
comment on column TAB_EARTH_CONFIG.id
  is '编号';
comment on column TAB_EARTH_CONFIG.value
  is '数据值';
comment on column TAB_EARTH_CONFIG.label
  is '标签名';
comment on column TAB_EARTH_CONFIG.type
  is '类型';
comment on column TAB_EARTH_CONFIG.description
  is '描述';
comment on column TAB_EARTH_CONFIG.sort
  is '排序（升序）';
comment on column TAB_EARTH_CONFIG.create_by
  is '创建人';
comment on column TAB_EARTH_CONFIG.create_time
  is '创建时间';
comment on column TAB_EARTH_CONFIG.last_update_by
  is '更新人';
comment on column TAB_EARTH_CONFIG.last_update_time
  is '更新时间';
comment on column TAB_EARTH_CONFIG.remarks
  is '备注信息';
comment on column TAB_EARTH_CONFIG.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table TAB_EARTH_CONFIG
  add constraint PK_CONFIG primary key (ID);

prompt
prompt Creating table TAB_EARTH_DEPT
prompt =============================
prompt
create table TAB_EARTH_DEPT
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50),
  parent_id        NUMBER(20),
  order_num        NUMBER(11),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE,
  del_flag         NUMBER(4)
)
;
comment on table TAB_EARTH_DEPT
  is '机构管理';
comment on column TAB_EARTH_DEPT.id
  is '编号';
comment on column TAB_EARTH_DEPT.name
  is '机构名称';
comment on column TAB_EARTH_DEPT.parent_id
  is '上级机构ID，一级机构为0';
comment on column TAB_EARTH_DEPT.order_num
  is '排序';
comment on column TAB_EARTH_DEPT.create_by
  is '创建人';
comment on column TAB_EARTH_DEPT.create_time
  is '创建时间';
comment on column TAB_EARTH_DEPT.last_update_by
  is '更新人';
comment on column TAB_EARTH_DEPT.last_update_time
  is '更新时间';
comment on column TAB_EARTH_DEPT.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table TAB_EARTH_DEPT
  add constraint PK_DEPT primary key (ID);

prompt
prompt Creating table TAB_EARTH_DICT
prompt =============================
prompt
create table TAB_EARTH_DICT
(
  id               NUMBER(20) not null,
  value            NVARCHAR2(100) not null,
  label            NVARCHAR2(100) not null,
  type             NVARCHAR2(100) not null,
  description      NVARCHAR2(100) not null,
  sort             NUMBER not null,
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE,
  remarks          NVARCHAR2(255),
  del_flag         NUMBER(4)
)
;
comment on table TAB_EARTH_DICT
  is '字典表';
comment on column TAB_EARTH_DICT.id
  is '编号';
comment on column TAB_EARTH_DICT.value
  is '数据值';
comment on column TAB_EARTH_DICT.label
  is '标签名';
comment on column TAB_EARTH_DICT.type
  is '类型';
comment on column TAB_EARTH_DICT.description
  is '描述';
comment on column TAB_EARTH_DICT.sort
  is '排序（升序）';
comment on column TAB_EARTH_DICT.create_by
  is '创建人';
comment on column TAB_EARTH_DICT.create_time
  is '创建时间';
comment on column TAB_EARTH_DICT.last_update_by
  is '更新人';
comment on column TAB_EARTH_DICT.last_update_time
  is '更新时间';
comment on column TAB_EARTH_DICT.remarks
  is '备注信息';
comment on column TAB_EARTH_DICT.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table TAB_EARTH_DICT
  add constraint PK_DICT primary key (ID);

prompt
prompt Creating table TAB_EARTH_LOG
prompt ============================
prompt
create table TAB_EARTH_LOG
(
  id               NUMBER(20) not null,
  user_name        NVARCHAR2(50),
  operation        NVARCHAR2(50),
  method           NVARCHAR2(200),
  params           NCLOB,
  time             NUMBER(20) not null,
  ip               NVARCHAR2(64),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE
)
;
comment on table TAB_EARTH_LOG
  is '系统操作日志';
comment on column TAB_EARTH_LOG.id
  is '编号';
comment on column TAB_EARTH_LOG.user_name
  is '用户名';
comment on column TAB_EARTH_LOG.operation
  is '用户操作';
comment on column TAB_EARTH_LOG.method
  is '请求方法';
comment on column TAB_EARTH_LOG.params
  is '请求参数';
comment on column TAB_EARTH_LOG.time
  is '执行时长(毫秒)';
comment on column TAB_EARTH_LOG.ip
  is 'IP地址';
comment on column TAB_EARTH_LOG.create_by
  is '创建人';
comment on column TAB_EARTH_LOG.create_time
  is '创建时间';
comment on column TAB_EARTH_LOG.last_update_by
  is '更新人';
comment on column TAB_EARTH_LOG.last_update_time
  is '更新时间';
alter table TAB_EARTH_LOG
  add constraint PK_LOG primary key (ID);

prompt
prompt Creating table TAB_EARTH_LOGIN_LOG
prompt ==================================
prompt
create table TAB_EARTH_LOGIN_LOG
(
  id               NUMBER(20) not null,
  user_name        NVARCHAR2(50),
  status           NVARCHAR2(50),
  ip               NVARCHAR2(64),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE
)
;
comment on table TAB_EARTH_LOGIN_LOG
  is '系统登录日志';
comment on column TAB_EARTH_LOGIN_LOG.id
  is '编号';
comment on column TAB_EARTH_LOGIN_LOG.user_name
  is '用户名';
comment on column TAB_EARTH_LOGIN_LOG.status
  is '登录状态（online:在线，登录初始状态，方便统计在线人数；login:退出登录后将online置为login；logout:退出登录）';
comment on column TAB_EARTH_LOGIN_LOG.ip
  is 'IP地址';
comment on column TAB_EARTH_LOGIN_LOG.create_by
  is '创建人';
comment on column TAB_EARTH_LOGIN_LOG.create_time
  is '创建时间';
comment on column TAB_EARTH_LOGIN_LOG.last_update_by
  is '更新人';
comment on column TAB_EARTH_LOGIN_LOG.last_update_time
  is '更新时间';
alter table TAB_EARTH_LOGIN_LOG
  add constraint PK_LOGIN_LOG primary key (ID);

prompt
prompt Creating table TAB_EARTH_MENU
prompt =============================
prompt
create table TAB_EARTH_MENU
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50),
  parent_id        NUMBER(20),
  url              NVARCHAR2(200),
  perms            NVARCHAR2(500),
  type             NUMBER(11),
  icon             NVARCHAR2(50),
  order_num        NUMBER(11),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE,
  del_flag         NUMBER(4)
)
;
comment on table TAB_EARTH_MENU
  is '菜单管理';
comment on column TAB_EARTH_MENU.id
  is '编号';
comment on column TAB_EARTH_MENU.name
  is '菜单名称';
comment on column TAB_EARTH_MENU.parent_id
  is '父菜单ID，一级菜单为0';
comment on column TAB_EARTH_MENU.url
  is '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)';
comment on column TAB_EARTH_MENU.perms
  is '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)';
comment on column TAB_EARTH_MENU.type
  is '类型   0：目录   1：菜单   2：按钮';
comment on column TAB_EARTH_MENU.icon
  is '菜单图标';
comment on column TAB_EARTH_MENU.order_num
  is '排序';
comment on column TAB_EARTH_MENU.create_by
  is '创建人';
comment on column TAB_EARTH_MENU.create_time
  is '创建时间';
comment on column TAB_EARTH_MENU.last_update_by
  is '更新人';
comment on column TAB_EARTH_MENU.last_update_time
  is '更新时间';
comment on column TAB_EARTH_MENU.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table TAB_EARTH_MENU
  add constraint PK_MENU primary key (ID);

prompt
prompt Creating table TAB_EARTH_ROLE
prompt =============================
prompt
create table TAB_EARTH_ROLE
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(100),
  remark           NVARCHAR2(100),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE,
  del_flag         NUMBER(4)
)
;
comment on table TAB_EARTH_ROLE
  is '角色管理';
comment on column TAB_EARTH_ROLE.id
  is '编号';
comment on column TAB_EARTH_ROLE.name
  is '角色名称';
comment on column TAB_EARTH_ROLE.remark
  is '备注';
comment on column TAB_EARTH_ROLE.create_by
  is '创建人';
comment on column TAB_EARTH_ROLE.create_time
  is '创建时间';
comment on column TAB_EARTH_ROLE.last_update_by
  is '更新人';
comment on column TAB_EARTH_ROLE.last_update_time
  is '更新时间';
comment on column TAB_EARTH_ROLE.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table TAB_EARTH_ROLE
  add constraint PK_ROLE primary key (ID);

prompt
prompt Creating table TAB_EARTH_ROLE_DEPT
prompt ==================================
prompt
create table TAB_EARTH_ROLE_DEPT
(
  id               NUMBER(20) not null,
  role_id          NUMBER(20),
  dept_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE
)
;
comment on table TAB_EARTH_ROLE_DEPT
  is '角色机构';
comment on column TAB_EARTH_ROLE_DEPT.id
  is '编号';
comment on column TAB_EARTH_ROLE_DEPT.role_id
  is '角色ID';
comment on column TAB_EARTH_ROLE_DEPT.dept_id
  is '机构ID';
comment on column TAB_EARTH_ROLE_DEPT.create_by
  is '创建人';
comment on column TAB_EARTH_ROLE_DEPT.create_time
  is '创建时间';
comment on column TAB_EARTH_ROLE_DEPT.last_update_by
  is '更新人';
comment on column TAB_EARTH_ROLE_DEPT.last_update_time
  is '更新时间';
alter table TAB_EARTH_ROLE_DEPT
  add constraint PK_ROLE_DEPT primary key (ID);
alter table TAB_EARTH_ROLE_DEPT
  add constraint FK_ROLE_DEPT_DEPT_ID foreign key (DEPT_ID)
  references TAB_EARTH_DEPT (ID);
alter table TAB_EARTH_ROLE_DEPT
  add constraint FK_ROLE_DEPT_ROLE_ID foreign key (ROLE_ID)
  references TAB_EARTH_ROLE (ID);

prompt
prompt Creating table TAB_EARTH_ROLE_MENU
prompt ==================================
prompt
create table TAB_EARTH_ROLE_MENU
(
  id               NUMBER(20) not null,
  role_id          NUMBER(20),
  menu_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE
)
;
comment on table TAB_EARTH_ROLE_MENU
  is '角色菜单';
comment on column TAB_EARTH_ROLE_MENU.id
  is '编号';
comment on column TAB_EARTH_ROLE_MENU.role_id
  is '角色ID';
comment on column TAB_EARTH_ROLE_MENU.menu_id
  is '菜单ID';
comment on column TAB_EARTH_ROLE_MENU.create_by
  is '创建人';
comment on column TAB_EARTH_ROLE_MENU.create_time
  is '创建时间';
comment on column TAB_EARTH_ROLE_MENU.last_update_by
  is '更新人';
comment on column TAB_EARTH_ROLE_MENU.last_update_time
  is '更新时间';
alter table TAB_EARTH_ROLE_MENU
  add constraint PK_ROLE_MENU primary key (ID);
alter table TAB_EARTH_ROLE_MENU
  add constraint FK_ROLE_MENU_MENU_ID foreign key (MENU_ID)
  references TAB_EARTH_MENU (ID);
alter table TAB_EARTH_ROLE_MENU
  add constraint FK_ROLE_MENU_ROLE_ID foreign key (ROLE_ID)
  references TAB_EARTH_ROLE (ID);

prompt
prompt Creating table TAB_EARTH_USER
prompt =============================
prompt
create table TAB_EARTH_USER
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50) not null,
  nick_name        NVARCHAR2(150),
  avatar           NVARCHAR2(150),
  password         NVARCHAR2(100),
  salt             NVARCHAR2(40),
  email            NVARCHAR2(100),
  mobile           NVARCHAR2(100),
  status           NUMBER(4),
  dept_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE,
  del_flag         NUMBER(4)
)
;
comment on table TAB_EARTH_USER
  is '用户管理';
comment on column TAB_EARTH_USER.id
  is '编号';
comment on column TAB_EARTH_USER.name
  is '用户名';
comment on column TAB_EARTH_USER.nick_name
  is '昵称';
comment on column TAB_EARTH_USER.avatar
  is '头像';
comment on column TAB_EARTH_USER.password
  is '密码';
comment on column TAB_EARTH_USER.salt
  is '加密盐';
comment on column TAB_EARTH_USER.email
  is '邮箱';
comment on column TAB_EARTH_USER.mobile
  is '手机号';
comment on column TAB_EARTH_USER.status
  is '状态  0：禁用   1：正常';
comment on column TAB_EARTH_USER.dept_id
  is '机构ID';
comment on column TAB_EARTH_USER.create_by
  is '创建人';
comment on column TAB_EARTH_USER.create_time
  is '创建时间';
comment on column TAB_EARTH_USER.last_update_by
  is '更新人';
comment on column TAB_EARTH_USER.last_update_time
  is '更新时间';
comment on column TAB_EARTH_USER.del_flag
  is '是否删除  -1：已删除  0：正常';
create unique index NAME on TAB_EARTH_USER (NAME);
alter table TAB_EARTH_USER
  add constraint PK_USER primary key (ID);
alter table TAB_EARTH_USER
  add constraint FK_USER_DEPT_ID foreign key (DEPT_ID)
  references TAB_EARTH_DEPT (ID);

prompt
prompt Creating table TAB_EARTH_USER_ROLE
prompt ==================================
prompt
create table TAB_EARTH_USER_ROLE
(
  id               NUMBER(20) not null,
  user_id          NUMBER(20),
  role_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      DATE,
  last_update_by   NVARCHAR2(50),
  last_update_time DATE
)
;
comment on table TAB_EARTH_USER_ROLE
  is '用户角色';
comment on column TAB_EARTH_USER_ROLE.id
  is '编号';
comment on column TAB_EARTH_USER_ROLE.user_id
  is '用户ID';
comment on column TAB_EARTH_USER_ROLE.role_id
  is '角色ID';
comment on column TAB_EARTH_USER_ROLE.create_by
  is '创建人';
comment on column TAB_EARTH_USER_ROLE.create_time
  is '创建时间';
comment on column TAB_EARTH_USER_ROLE.last_update_by
  is '更新人';
comment on column TAB_EARTH_USER_ROLE.last_update_time
  is '更新时间';
alter table TAB_EARTH_USER_ROLE
  add constraint PK_USER_ROLE primary key (ID);
alter table TAB_EARTH_USER_ROLE
  add constraint FK_USER_ROLE_ROLE_ID foreign key (ROLE_ID)
  references TAB_EARTH_ROLE (ID);
alter table TAB_EARTH_USER_ROLE
  add constraint FK_USER_ROLE_USER_ID foreign key (USER_ID)
  references TAB_EARTH_USER (ID);


prompt Done
spool off
set define on
