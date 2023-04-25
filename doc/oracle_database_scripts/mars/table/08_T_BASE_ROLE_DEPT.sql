prompt
prompt Creating table T_BASE_ROLE_DEPT 创建表...
prompt =================================
prompt
create table T_BASE_ROLE_DEPT
(
  id               VARCHAR2(32) not null,
  role_id          VARCHAR2(32),
  dept_id          VARCHAR2(32),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE
)
;
comment on table T_BASE_ROLE_DEPT
  is '角色机构';
comment on column T_BASE_ROLE_DEPT.id
  is '编号';
comment on column T_BASE_ROLE_DEPT.role_id
  is '角色ID';
comment on column T_BASE_ROLE_DEPT.dept_id
  is '机构ID';
comment on column T_BASE_ROLE_DEPT.create_by
  is '创建人';
comment on column T_BASE_ROLE_DEPT.create_time
  is '创建时间';
comment on column T_BASE_ROLE_DEPT.last_update_by
  is '更新人';
comment on column T_BASE_ROLE_DEPT.last_update_time
  is '更新时间';
alter table T_BASE_ROLE_DEPT
  add constraint PK_BASE_ROLE_DEPT_ID primary key (ID);
alter table T_BASE_ROLE_DEPT
  add constraint FK_BASE_ROLE_DEPT_DEPT_ID foreign key (DEPT_ID)
  references T_BASE_DEPT (ID);
alter table T_BASE_ROLE_DEPT
  add constraint FK_BASE_ROLE_DEPT_ROLE_ID foreign key (ROLE_ID)
  references T_BASE_ROLE (ID);