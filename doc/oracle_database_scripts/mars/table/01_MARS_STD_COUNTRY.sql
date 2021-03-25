prompt
prompt Creating table MARS_STD_COUNTRY 创建表...
prompt ===============================
prompt
create table MARS_STD_COUNTRY
(
  code    VARCHAR2(10) not null,
  name    NVARCHAR2(50),
  name_en VARCHAR2(50)
)
;
comment on table MARS_STD_COUNTRY
  is '国家 ISO 3166-1';
comment on column MARS_STD_COUNTRY.code
  is '代码';
comment on column MARS_STD_COUNTRY.name
  is '名称';
comment on column MARS_STD_COUNTRY.name_en
  is '英文名称';
alter table MARS_STD_COUNTRY
  add constraint PK_STD_COUNTRY_CODE primary key (CODE);