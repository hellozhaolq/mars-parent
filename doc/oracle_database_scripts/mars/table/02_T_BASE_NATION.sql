prompt
prompt Creating table T_BASE_NATION 创建表...
prompt ==============================
prompt
create table T_BASE_NATION
(
  code VARCHAR2(10) not null,
  name NVARCHAR2(50)
)
;
comment on table T_BASE_NATION
  is '民族 GB 3304-91';
comment on column T_BASE_NATION.code
  is '代码';
comment on column T_BASE_NATION.name
  is '名称';
alter table T_BASE_NATION
  add constraint PK_STD_NATION_CODE primary key (CODE);