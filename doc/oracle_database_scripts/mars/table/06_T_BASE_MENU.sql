prompt
prompt Creating table T_BASE_MENU 创建表...
prompt ============================
prompt
create table T_BASE_MENU
(
  id               VARCHAR2(32) not null,
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
  parent_id        VARCHAR2(32),
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
comment on table T_BASE_MENU
  is '菜单管理';
comment on column T_BASE_MENU.id
  is '编号';
comment on column T_BASE_MENU.name
  is '菜单名称';
comment on column T_BASE_MENU.code
  is '菜单代码';
comment on column T_BASE_MENU.remark
  is '备注';
comment on column T_BASE_MENU.perms
  is '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)';
comment on column T_BASE_MENU.type
  is '类型   0：目录   1：菜单   2：按钮';
comment on column T_BASE_MENU.url_type
  is 'url类型：1.普通页面 2.嵌套服务器页面 3.嵌套完整外部页面';
comment on column T_BASE_MENU.url
  is '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)';
comment on column T_BASE_MENU.scheme
  is '路径前缀';
comment on column T_BASE_MENU.path
  is '请求路径';
comment on column T_BASE_MENU.target
  is '打开方式:_self窗口内,_blank新窗口';
comment on column T_BASE_MENU.parent_id
  is '父菜单ID，一级菜单为0';
comment on column T_BASE_MENU.order_num
  is '排序';
comment on column T_BASE_MENU.icon
  is '菜单图标';
comment on column T_BASE_MENU.create_by
  is '创建人';
comment on column T_BASE_MENU.create_time
  is '创建时间';
comment on column T_BASE_MENU.last_update_by
  is '更新人';
comment on column T_BASE_MENU.last_update_time
  is '更新时间';
comment on column T_BASE_MENU.status
  is '状态  0：禁用   1：正常';
comment on column T_BASE_MENU.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table T_BASE_MENU
  add constraint PK_BASE_MENU_ID primary key (ID);
alter table T_BASE_MENU
  add constraint UK_BASE_MENU_CODE unique (CODE);