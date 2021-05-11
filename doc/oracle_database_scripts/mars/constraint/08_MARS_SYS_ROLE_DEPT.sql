prompt Add foreign key constraint for table MARS_SYS_ROLE_DEPT 添加约束...
prompt
alter table MARS_SYS_ROLE_DEPT
  add constraint FK_SYS_ROLE_DEPT_DEPT_ID foreign key (DEPT_ID)
  references MARS_SYS_DEPT (ID);
alter table MARS_SYS_ROLE_DEPT
  add constraint FK_SYS_ROLE_DEPT_ROLE_ID foreign key (ROLE_ID)
  references MARS_SYS_ROLE (ID);