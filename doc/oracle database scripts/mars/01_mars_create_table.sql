prompt PL/SQL Developer Export User Objects for user MARS@192.168.0.3:1521/ORCL.ZHAOLQ.COM
prompt Created by Administrator on 2020��10��26��
set define off
spool bbb.log

prompt
prompt Creating table MARS_STD_COUNTRY
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
  is '���� ISO 3166-1';
comment on column MARS_STD_COUNTRY.code
  is '����';
comment on column MARS_STD_COUNTRY.name
  is '����';
comment on column MARS_STD_COUNTRY.name_en
  is 'Ӣ������';
alter table MARS_STD_COUNTRY
  add constraint PK_STD_COUNTRY_CODE primary key (CODE);

prompt
prompt Creating table MARS_STD_NATION
prompt ==============================
prompt
create table MARS_STD_NATION
(
  code VARCHAR2(10) not null,
  name NVARCHAR2(50)
)
;
comment on table MARS_STD_NATION
  is '���� GB 3304-91';
comment on column MARS_STD_NATION.code
  is '����';
comment on column MARS_STD_NATION.name
  is '����';
alter table MARS_STD_NATION
  add constraint PK_STD_NATION_CODE primary key (CODE);

prompt
prompt Creating table MARS_STD_POLITICAL_STATUS
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
  is '������ò';
comment on column MARS_STD_POLITICAL_STATUS.code
  is '����';
comment on column MARS_STD_POLITICAL_STATUS.name
  is '����';
comment on column MARS_STD_POLITICAL_STATUS.name_short
  is '���';
alter table MARS_STD_POLITICAL_STATUS
  add constraint PK_STD_POLITICAL_STATUS_CODE primary key (CODE);

prompt
prompt Creating table MARS_SYS_DEPT
prompt ============================
prompt
create table MARS_SYS_DEPT
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50),
  code             VARCHAR2(50),
  remark           NVARCHAR2(100),
  type             NUMBER(1),
  subtype          VARCHAR2(20),
  parent_id        NUMBER(20),
  order_num        NUMBER(10),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status           NUMBER(1),
  del_flag         NUMBER(1)
)
;
comment on table MARS_SYS_DEPT
  is '��������';
comment on column MARS_SYS_DEPT.id
  is '���';
comment on column MARS_SYS_DEPT.name
  is '��������';
comment on column MARS_SYS_DEPT.code
  is '��������';
comment on column MARS_SYS_DEPT.remark
  is '��ע';
comment on column MARS_SYS_DEPT.type
  is '���͡�1�����ţ�2��ѧУ ��';
comment on column MARS_SYS_DEPT.subtype
  is '������';
comment on column MARS_SYS_DEPT.parent_id
  is '�ϼ�����ID��һ������Ϊ0';
comment on column MARS_SYS_DEPT.order_num
  is '����';
comment on column MARS_SYS_DEPT.create_by
  is '������';
comment on column MARS_SYS_DEPT.create_time
  is '����ʱ��';
comment on column MARS_SYS_DEPT.last_update_by
  is '������';
comment on column MARS_SYS_DEPT.last_update_time
  is '����ʱ��';
comment on column MARS_SYS_DEPT.status
  is '״̬  0������   1������';
comment on column MARS_SYS_DEPT.del_flag
  is '�Ƿ�ɾ��  -1����ɾ��  0������';
alter table MARS_SYS_DEPT
  add constraint PK_SYS_DEPT_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_MEDIA_FILE
prompt ==================================
prompt
create table MARS_SYS_MEDIA_FILE
(
  id               NUMBER(20) not null,
  resource_type    VARCHAR2(50),
  resource_id      NUMBER(20),
  file_type        VARCHAR2(50),
  file_content     BLOB,
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status           NUMBER(1),
  del_flag         NUMBER(1)
)
;
comment on table MARS_SYS_MEDIA_FILE
  is 'ͼƬ���ļ������ֵ�ý���ļ�';
comment on column MARS_SYS_MEDIA_FILE.id
  is '���';
comment on column MARS_SYS_MEDIA_FILE.resource_type
  is '��Դ����(����)��user-�û���';
comment on column MARS_SYS_MEDIA_FILE.resource_id
  is '��Դ���';
comment on column MARS_SYS_MEDIA_FILE.file_type
  is '�ļ�����(�ֶ�)��avatar-ͷ��';
comment on column MARS_SYS_MEDIA_FILE.file_content
  is '�ļ�����';
comment on column MARS_SYS_MEDIA_FILE.create_by
  is '������';
comment on column MARS_SYS_MEDIA_FILE.create_time
  is '����ʱ��';
comment on column MARS_SYS_MEDIA_FILE.last_update_by
  is '������';
comment on column MARS_SYS_MEDIA_FILE.last_update_time
  is '����ʱ��';
comment on column MARS_SYS_MEDIA_FILE.status
  is '״̬  0������   1������';
comment on column MARS_SYS_MEDIA_FILE.del_flag
  is '�Ƿ�ɾ��  -1����ɾ��  0������';
alter table MARS_SYS_MEDIA_FILE
  add constraint PK_SYS_MEDIA_FILE_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_MENU
prompt ============================
prompt
create table MARS_SYS_MENU
(
  id               NUMBER(20) not null,
  name             NVARCHAR2(50),
  code             VARCHAR2(50),
  remark           NVARCHAR2(100),
  perms            VARCHAR2(100),
  type             NUMBER(1),
  url_type         NUMBER(1),
  url              VARCHAR2(200),
  scheme           VARCHAR2(20),
  path             VARCHAR2(100),
  target           VARCHAR2(20),
  parent_id        NUMBER(20),
  order_num        NUMBER(2),
  icon             VARCHAR2(50),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE,
  status           NUMBER(1),
  del_flag         NUMBER(1)
)
;
comment on table MARS_SYS_MENU
  is '�˵�����';
comment on column MARS_SYS_MENU.id
  is '���';
comment on column MARS_SYS_MENU.name
  is '�˵�����';
comment on column MARS_SYS_MENU.code
  is '�˵�����';
comment on column MARS_SYS_MENU.remark
  is '��ע';
comment on column MARS_SYS_MENU.perms
  is '��Ȩ(����ö��ŷָ����磺sys:user:add,sys:user:edit)';
comment on column MARS_SYS_MENU.type
  is '����   0��Ŀ¼   1���˵�   2����ť';
comment on column MARS_SYS_MENU.url_type
  is 'url���ͣ�1.��ͨҳ�� 2.Ƕ�׷�����ҳ�� 3.Ƕ�������ⲿҳ��';
comment on column MARS_SYS_MENU.url
  is '�˵�URL,���ͣ�1.��ͨҳ�棨���û����� /sys/user�� 2.Ƕ�������ⲿҳ�棬��http(s)��ͷ������ 3.Ƕ�׷�����ҳ�棬ʹ��iframe:ǰ׺+Ŀ��URL(��SQL��أ� iframe:/druid/login.html, iframe:ǰ׺���滻�ɷ�������ַ)';
comment on column MARS_SYS_MENU.scheme
  is '·��ǰ׺';
comment on column MARS_SYS_MENU.path
  is '����·��';
comment on column MARS_SYS_MENU.target
  is '�򿪷�ʽ:_self������,_blank�´���';
comment on column MARS_SYS_MENU.parent_id
  is '���˵�ID��һ���˵�Ϊ0';
comment on column MARS_SYS_MENU.order_num
  is '����';
comment on column MARS_SYS_MENU.icon
  is '�˵�ͼ��';
comment on column MARS_SYS_MENU.create_by
  is '������';
comment on column MARS_SYS_MENU.create_time
  is '����ʱ��';
comment on column MARS_SYS_MENU.last_update_by
  is '������';
comment on column MARS_SYS_MENU.last_update_time
  is '����ʱ��';
comment on column MARS_SYS_MENU.status
  is '״̬  0������   1������';
comment on column MARS_SYS_MENU.del_flag
  is '�Ƿ�ɾ��  -1����ɾ��  0������';
alter table MARS_SYS_MENU
  add constraint PK_SYS_MENU_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_ROLE
prompt ============================
prompt
create table MARS_SYS_ROLE
(
  id               NUMBER(20) not null,
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
comment on table MARS_SYS_ROLE
  is '��ɫ����';
comment on column MARS_SYS_ROLE.id
  is '���';
comment on column MARS_SYS_ROLE.name
  is '��ɫ����';
comment on column MARS_SYS_ROLE.code
  is '��ɫ����';
comment on column MARS_SYS_ROLE.remark
  is '��ע';
comment on column MARS_SYS_ROLE.create_by
  is '������';
comment on column MARS_SYS_ROLE.create_time
  is '����ʱ��';
comment on column MARS_SYS_ROLE.last_update_by
  is '������';
comment on column MARS_SYS_ROLE.last_update_time
  is '����ʱ��';
comment on column MARS_SYS_ROLE.status
  is '״̬  0������   1������';
comment on column MARS_SYS_ROLE.del_flag
  is '�Ƿ�ɾ��  -1����ɾ��  0������';
alter table MARS_SYS_ROLE
  add constraint PK_SYS_ROLE_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_ROLE_DEPT
prompt =================================
prompt
create table MARS_SYS_ROLE_DEPT
(
  id               NUMBER(20) not null,
  role_id          NUMBER(20),
  dept_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE
)
;
comment on table MARS_SYS_ROLE_DEPT
  is '��ɫ����';
comment on column MARS_SYS_ROLE_DEPT.id
  is '���';
comment on column MARS_SYS_ROLE_DEPT.role_id
  is '��ɫID';
comment on column MARS_SYS_ROLE_DEPT.dept_id
  is '����ID';
comment on column MARS_SYS_ROLE_DEPT.create_by
  is '������';
comment on column MARS_SYS_ROLE_DEPT.create_time
  is '����ʱ��';
comment on column MARS_SYS_ROLE_DEPT.last_update_by
  is '������';
comment on column MARS_SYS_ROLE_DEPT.last_update_time
  is '����ʱ��';
alter table MARS_SYS_ROLE_DEPT
  add constraint PK_SYS_ROLE_DEPT_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_ROLE_MENU
prompt =================================
prompt
create table MARS_SYS_ROLE_MENU
(
  id               NUMBER(20) not null,
  role_id          NUMBER(20),
  menu_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE
)
;
comment on table MARS_SYS_ROLE_MENU
  is '��ɫ�˵�';
comment on column MARS_SYS_ROLE_MENU.id
  is '���';
comment on column MARS_SYS_ROLE_MENU.role_id
  is '��ɫID';
comment on column MARS_SYS_ROLE_MENU.menu_id
  is '�˵�ID';
comment on column MARS_SYS_ROLE_MENU.create_by
  is '������';
comment on column MARS_SYS_ROLE_MENU.create_time
  is '����ʱ��';
comment on column MARS_SYS_ROLE_MENU.last_update_by
  is '������';
comment on column MARS_SYS_ROLE_MENU.last_update_time
  is '����ʱ��';
alter table MARS_SYS_ROLE_MENU
  add constraint PK_SYS_ROLE_MENU_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_USER
prompt ============================
prompt
create table MARS_SYS_USER
(
  id                    NUMBER(20) not null,
  account               VARCHAR2(50) not null,
  password              VARCHAR2(50),
  name                  NVARCHAR2(50),
  nick_name             NVARCHAR2(50),
  salt                  VARCHAR2(50),
  sex                   NUMBER(1),
  birthday              DATE,
  age                   NUMBER(3),
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
  dept_id               NUMBER(20),
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
  is '�û�����';
comment on column MARS_SYS_USER.id
  is '���';
comment on column MARS_SYS_USER.account
  is '�˺�';
comment on column MARS_SYS_USER.password
  is '����';
comment on column MARS_SYS_USER.name
  is '�û���';
comment on column MARS_SYS_USER.nick_name
  is '�ǳ�';
comment on column MARS_SYS_USER.salt
  is '������';
comment on column MARS_SYS_USER.sex
  is '�Ա�  1����   2��Ů';
comment on column MARS_SYS_USER.birthday
  is '��������';
comment on column MARS_SYS_USER.age
  is '����';
comment on column MARS_SYS_USER.id_number
  is '���֤��';
comment on column MARS_SYS_USER.address
  is '��ַ';
comment on column MARS_SYS_USER.email
  is '����';
comment on column MARS_SYS_USER.mobile
  is '�ֻ���';
comment on column MARS_SYS_USER.entry_time
  is '��ְʱ��';
comment on column MARS_SYS_USER.departure_time
  is '��ְʱ��';
comment on column MARS_SYS_USER.country_code
  is '���Ҵ���';
comment on column MARS_SYS_USER.nation_code
  is '�������';
comment on column MARS_SYS_USER.political_status_code
  is '������ò����';
comment on column MARS_SYS_USER.user_type
  is '�û�����';
comment on column MARS_SYS_USER.identity_code
  is '��ݴ���';
comment on column MARS_SYS_USER.dept_id
  is '����ID';
comment on column MARS_SYS_USER.create_by
  is '������';
comment on column MARS_SYS_USER.create_time
  is '����ʱ��';
comment on column MARS_SYS_USER.last_update_by
  is '������';
comment on column MARS_SYS_USER.last_update_time
  is '����ʱ��';
comment on column MARS_SYS_USER.status
  is '״̬  0������   1������';
comment on column MARS_SYS_USER.del_flag
  is '�Ƿ�ɾ��  -1����ɾ��  0������';
comment on column MARS_SYS_USER.flag
  is '��0���Ƿ�:1��0��';
alter table MARS_SYS_USER
  add constraint PK_SYS_USER_ID primary key (ID);

prompt
prompt Creating table MARS_SYS_USER_ROLE
prompt =================================
prompt
create table MARS_SYS_USER_ROLE
(
  id               NUMBER(20) not null,
  user_id          NUMBER(20),
  role_id          NUMBER(20),
  create_by        NVARCHAR2(50),
  create_time      TIMESTAMP(6) WITH LOCAL TIME ZONE,
  last_update_by   NVARCHAR2(50),
  last_update_time TIMESTAMP(6) WITH LOCAL TIME ZONE
)
;
comment on table MARS_SYS_USER_ROLE
  is '�û���ɫ';
comment on column MARS_SYS_USER_ROLE.id
  is '���';
comment on column MARS_SYS_USER_ROLE.user_id
  is '�û�ID';
comment on column MARS_SYS_USER_ROLE.role_id
  is '��ɫID';
comment on column MARS_SYS_USER_ROLE.create_by
  is '������';
comment on column MARS_SYS_USER_ROLE.create_time
  is '����ʱ��';
comment on column MARS_SYS_USER_ROLE.last_update_by
  is '������';
comment on column MARS_SYS_USER_ROLE.last_update_time
  is '����ʱ��';
alter table MARS_SYS_USER_ROLE
  add constraint PK_SYS_USER_ROLE_ID primary key (ID);

prompt Done
spool off
set define on
