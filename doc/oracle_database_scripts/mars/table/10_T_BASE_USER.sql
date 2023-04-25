prompt
prompt Creating table T_BASE_USER 创建表...
prompt ============================
prompt
create table T_BASE_USER
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
comment on table T_BASE_USER
  is '用户管理';
comment on column T_BASE_USER.id
  is '编号';
comment on column T_BASE_USER.account
  is '账号';
comment on column T_BASE_USER.password
  is '密码';
comment on column T_BASE_USER.name
  is '用户名';
comment on column T_BASE_USER.nick_name
  is '昵称';
comment on column T_BASE_USER.salt
  is '加密盐';
comment on column T_BASE_USER.sex
  is '性别  1：男   2：女';
comment on column T_BASE_USER.birthday
  is '出生日期';
comment on column T_BASE_USER.age
  is '年龄';
comment on column T_BASE_USER.id_number
  is '身份证号';
comment on column T_BASE_USER.address
  is '地址';
comment on column T_BASE_USER.email
  is '邮箱';
comment on column T_BASE_USER.mobile
  is '手机号';
comment on column T_BASE_USER.entry_time
  is '入职时间';
comment on column T_BASE_USER.departure_time
  is '离职时间';
comment on column T_BASE_USER.country_code
  is '国家代码';
comment on column T_BASE_USER.nation_code
  is '民族代码';
comment on column T_BASE_USER.political_status_code
  is '政治面貌代码';
comment on column T_BASE_USER.user_type
  is '用户类型';
comment on column T_BASE_USER.identity_code
  is '身份代码';
comment on column T_BASE_USER.dept_id
  is '机构ID';
comment on column T_BASE_USER.create_by
  is '创建人';
comment on column T_BASE_USER.create_time
  is '创建时间';
comment on column T_BASE_USER.last_update_by
  is '更新人';
comment on column T_BASE_USER.last_update_time
  is '更新时间';
comment on column T_BASE_USER.status
  is '状态  0：禁用   1：正常';
comment on column T_BASE_USER.del_flag
  is '是否删除  -1：已删除  0：正常';
comment on column T_BASE_USER.flag
  is '【0】是否:1是0否';
alter table T_BASE_USER
  add constraint PK_BASE_USER_ID primary key (ID);
alter table T_BASE_USER
  add constraint UK_BASE_USER_ACCOUNT unique (ACCOUNT);
alter table T_BASE_USER
  add constraint UK_BASE_USER_ID_NUMBER unique (ID_NUMBER);
alter table T_BASE_USER
  add constraint FK_BASE_USER_DEPT_ID foreign key (DEPT_ID)
  references T_BASE_DEPT (ID);
alter table T_BASE_USER
  add constraint FK_BASE_USER_COUNTRY_CODE foreign key (COUNTRY_CODE)
  references T_BASE_COUNTRY (CODE);
alter table T_BASE_USER
  add constraint FK_BASE_USER_NATION_CODE foreign key (NATION_CODE)
  references T_BASE_NATION (CODE);
alter table T_BASE_USER
  add constraint FK_BASE_USER_POLITICAL_STATUS foreign key (POLITICAL_STATUS_CODE)
  references T_BASE_POLITICAL_STATUS (CODE);