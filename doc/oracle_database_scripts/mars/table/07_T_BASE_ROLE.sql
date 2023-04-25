prompt
prompt Creating table T_BASE_ROLE 创建表...
prompt ============================
prompt
create table T_BASE_ROLE
(
  id               VARCHAR2(32) not null,
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
comment on table T_BASE_ROLE
  is '角色管理';
comment on column T_BASE_ROLE.id
  is '编号';
comment on column T_BASE_ROLE.name
  is '角色名称';
comment on column T_BASE_ROLE.code
  is '角色代码';
comment on column T_BASE_ROLE.remark
  is '备注';
comment on column T_BASE_ROLE.create_by
  is '创建人';
comment on column T_BASE_ROLE.create_time
  is '创建时间';
comment on column T_BASE_ROLE.last_update_by
  is '更新人';
comment on column T_BASE_ROLE.last_update_time
  is '更新时间';
comment on column T_BASE_ROLE.status
  is '状态  0：禁用   1：正常';
comment on column T_BASE_ROLE.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table T_BASE_ROLE
  add constraint PK_BASE_ROLE_ID primary key (ID);
alter table T_BASE_ROLE
  add constraint UK_BASE_ROLE_CODE unique (CODE);