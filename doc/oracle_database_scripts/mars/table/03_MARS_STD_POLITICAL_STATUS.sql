prompt
prompt Creating table MARS_STD_POLITICAL_STATUS 创建表...
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