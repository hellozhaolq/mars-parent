prompt Add foreign key constraint for table MARS_SYS_ROLE_MENU 添加外键约束...
prompt
alter table MARS_SYS_ROLE_MENU
  add constraint FK_SYS_ROLE_MENU_MENU_ID foreign key (MENU_ID)
  references MARS_SYS_MENU (ID);
alter table MARS_SYS_ROLE_MENU
  add constraint FK_SYS_ROLE_MENU_ROLE_ID foreign key (ROLE_ID)
  references MARS_SYS_ROLE (ID);
