prompt
prompt Creating table MARS_STD_NATION 创建表...
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