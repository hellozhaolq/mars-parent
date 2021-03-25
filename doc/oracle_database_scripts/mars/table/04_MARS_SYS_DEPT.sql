prompt
prompt Creating table MARS_SYS_DEPT 创建表...
prompt ============================
prompt
create table MARS_SYS_DEPT
(
  id               VARCHAR2(32) not null,
  name             NVARCHAR2(50),
  code             VARCHAR2(50),
  remark           NVARCHAR2(100),
  type             NUMBER(1),
  subtype          VARCHAR2(20),
  parent_id        VARCHAR2(32),
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
  is '机构管理';
comment on column MARS_SYS_DEPT.id
  is '编号';
comment on column MARS_SYS_DEPT.name
  is '机构名称';
comment on column MARS_SYS_DEPT.code
  is '机构代码';
comment on column MARS_SYS_DEPT.remark
  is '备注';
comment on column MARS_SYS_DEPT.type
  is '类型【1：集团；2：学校 】';
comment on column MARS_SYS_DEPT.subtype
  is '子类型';
comment on column MARS_SYS_DEPT.parent_id
  is '上级机构ID，一级机构为0';
comment on column MARS_SYS_DEPT.order_num
  is '排序';
comment on column MARS_SYS_DEPT.create_by
  is '创建人';
comment on column MARS_SYS_DEPT.create_time
  is '创建时间';
comment on column MARS_SYS_DEPT.last_update_by
  is '更新人';
comment on column MARS_SYS_DEPT.last_update_time
  is '更新时间';
comment on column MARS_SYS_DEPT.status
  is '状态  0：禁用   1：正常';
comment on column MARS_SYS_DEPT.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table MARS_SYS_DEPT
  add constraint PK_SYS_DEPT_ID primary key (ID);
alter table MARS_SYS_DEPT
  add constraint UK_SYS_DEPT_CODE unique (CODE);