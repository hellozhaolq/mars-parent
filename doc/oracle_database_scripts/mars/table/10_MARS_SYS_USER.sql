prompt
prompt Creating table MARS_SYS_USER 创建表...
prompt ============================
prompt
create table MARS_SYS_USER
(
  id                    VARCHAR2(32) not null,
  account               VARCHAR2(50) not null,
  password              VARCHAR2(50),
  name                  NVARCHAR2(50),
  nick_name             NVARCHAR2(50),
  salt                  VARCHAR2(50),
  sex                   NUMBER(1),
  birthday              DATE,
  age                   NUMBER(5),
  id_number             VARCHAR2(50),
  address               NVARCHAR2(50),
  email                 VARCHAR2(50),
  mobile                VARCHAR2(50),
  entry_time            DATE,
  departure_time        DATE,
  country_code          VARCHAR2(10),
  nation_code           VARCHAR2(10),
  political_status_code VARCHAR2(10),
  user_type             VARCHAR2(10),
  identity_code         VARCHAR2(10),
  dept_id               VARCHAR2(32),
  create_by             NVARCHAR2(50),
  create_time           TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by        NVARCHAR2(50),
  last_update_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status                NUMBER(1),
  del_flag              NUMBER(1),
  flag                  VARCHAR2(10) default '0000000000'
)
;
comment on table MARS_SYS_USER
  is '用户管理';
comment on column MARS_SYS_USER.id
  is '编号';
comment on column MARS_SYS_USER.account
  is '账号';
comment on column MARS_SYS_USER.password
  is '密码';
comment on column MARS_SYS_USER.name
  is '用户名';
comment on column MARS_SYS_USER.nick_name
  is '昵称';
comment on column MARS_SYS_USER.salt
  is '加密盐';
comment on column MARS_SYS_USER.sex
  is '性别  1：男   2：女';
comment on column MARS_SYS_USER.birthday
  is '出生日期';
comment on column MARS_SYS_USER.age
  is '年龄';
comment on column MARS_SYS_USER.id_number
  is '身份证号';
comment on column MARS_SYS_USER.address
  is '地址';
comment on column MARS_SYS_USER.email
  is '邮箱';
comment on column MARS_SYS_USER.mobile
  is '手机号';
comment on column MARS_SYS_USER.entry_time
  is '入职时间';
comment on column MARS_SYS_USER.departure_time
  is '离职时间';
comment on column MARS_SYS_USER.country_code
  is '国家代码';
comment on column MARS_SYS_USER.nation_code
  is '民族代码';
comment on column MARS_SYS_USER.political_status_code
  is '政治面貌代码';
comment on column MARS_SYS_USER.user_type
  is '用户类型';
comment on column MARS_SYS_USER.identity_code
  is '身份代码';
comment on column MARS_SYS_USER.dept_id
  is '机构ID';
comment on column MARS_SYS_USER.create_by
  is '创建人';
comment on column MARS_SYS_USER.create_time
  is '创建时间';
comment on column MARS_SYS_USER.last_update_by
  is '更新人';
comment on column MARS_SYS_USER.last_update_time
  is '更新时间';
comment on column MARS_SYS_USER.status
  is '状态  0：禁用   1：正常';
comment on column MARS_SYS_USER.del_flag
  is '是否删除  -1：已删除  0：正常';
comment on column MARS_SYS_USER.flag
  is '【0】是否:1是0否';
alter table MARS_SYS_USER
  add constraint PK_SYS_USER_ID primary key (ID);
alter table MARS_SYS_USER
  add constraint UK_SYS_USER_ACCOUNT unique (ACCOUNT);
alter table MARS_SYS_USER
  add constraint UK_SYS_USER_ID_NUMBER unique (ID_NUMBER);
alter table MARS_SYS_USER
  add constraint FK_SYS_USER_DEPT_ID foreign key (DEPT_ID)
  references MARS_SYS_DEPT (ID);
alter table MARS_SYS_USER
  add constraint FK_SYS_USER_COUNTRY_CODE foreign key (COUNTRY_CODE)
  references MARS_STD_COUNTRY (CODE);
alter table MARS_SYS_USER
  add constraint FK_SYS_USER_NATION_CODE foreign key (NATION_CODE)
  references MARS_STD_NATION (CODE);
alter table MARS_SYS_USER
  add constraint FK_SYS_USER_POLITICAL_STATUS foreign key (POLITICAL_STATUS_CODE)
  references MARS_STD_POLITICAL_STATUS (CODE);