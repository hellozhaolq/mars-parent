prompt
prompt Creating table T_BASE_COUNTRY 创建表...
prompt ===============================
prompt
create table T_BASE_COUNTRY
(
  code    VARCHAR2(10) not null,
  name    NVARCHAR2(50),
  name_en VARCHAR2(50)
)
;
comment on table T_BASE_COUNTRY
  is '国家 ISO 3166-1';
comment on column T_BASE_COUNTRY.code
  is '代码';
comment on column T_BASE_COUNTRY.name
  is '名称';
comment on column T_BASE_COUNTRY.name_en
  is '英文名称';
alter table T_BASE_COUNTRY
  add constraint PK_STD_COUNTRY_CODE primary key (CODE);