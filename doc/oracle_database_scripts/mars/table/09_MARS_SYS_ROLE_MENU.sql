prompt
prompt Creating table MARS_SYS_ROLE_MENU 创建表...
prompt =================================
prompt
create table MARS_SYS_ROLE_MENU
(
  id               VARCHAR2(32) not null,
  role_id          VARCHAR2(32),
  menu_id          VARCHAR2(32),
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