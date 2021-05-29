prompt
prompt Creating table MARS_SYS_USER_ROLE 创建表...
prompt =================================
prompt
create table MARS_SYS_USER_ROLE
(
  id               VARCHAR2(32) not null,
  user_id          VARCHAR2(32),
  role_id          VARCHAR2(32),
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
alter table MARS_SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_ROLE_ID foreign key (ROLE_ID)
  references MARS_SYS_ROLE (ID);
alter table MARS_SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_USER_ID foreign key (USER_ID)
  references MARS_SYS_USER (ID);