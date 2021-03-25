prompt Add foreign key constraint for table MARS_SYS_USER_ROLE 添加外键约束...
prompt
alter table MARS_SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_ROLE_ID foreign key (ROLE_ID)
  references MARS_SYS_ROLE (ID);
alter table MARS_SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_USER_ID foreign key (USER_ID)
  references MARS_SYS_USER (ID);
