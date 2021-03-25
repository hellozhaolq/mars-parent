prompt Add foreign key constraint for table MARS_SYS_USER 添加外键约束...
prompt
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
