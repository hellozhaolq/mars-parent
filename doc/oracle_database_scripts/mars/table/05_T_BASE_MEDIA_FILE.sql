prompt
prompt Creating table T_BASE_MEDIA_FILE 创建表...
prompt ==================================
prompt
create table T_BASE_MEDIA_FILE
(
  id               VARCHAR2(32) not null,
  resource_type    VARCHAR2(50),
  resource_id      VARCHAR2(32),
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
comment on table T_BASE_MEDIA_FILE
  is '图片、文件、音乐等媒体文件';
comment on column T_BASE_MEDIA_FILE.id
  is '编号';
comment on column T_BASE_MEDIA_FILE.resource_type
  is '资源类型(表名)：user-用户；';
comment on column T_BASE_MEDIA_FILE.resource_id
  is '资源编号';
comment on column T_BASE_MEDIA_FILE.file_type
  is '文件类型(字段)：avatar-头像；';
comment on column T_BASE_MEDIA_FILE.file_content
  is '文件内容';
comment on column T_BASE_MEDIA_FILE.create_by
  is '创建人';
comment on column T_BASE_MEDIA_FILE.create_time
  is '创建时间';
comment on column T_BASE_MEDIA_FILE.last_update_by
  is '更新人';
comment on column T_BASE_MEDIA_FILE.last_update_time
  is '更新时间';
comment on column T_BASE_MEDIA_FILE.status
  is '状态  0：禁用   1：正常';
comment on column T_BASE_MEDIA_FILE.del_flag
  is '是否删除  -1：已删除  0：正常';
alter table T_BASE_MEDIA_FILE
  add constraint PK_BASE_MEDIA_FILE_ID primary key (ID);