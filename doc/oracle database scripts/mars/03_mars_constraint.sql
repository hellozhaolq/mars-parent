
---添加唯一约束---

alter table MARS_SYS_DEPT
  add constraint UK_SYS_DEPT_CODE unique (CODE);

alter table MARS_SYS_MENU
  add constraint UK_SYS_MENU_CODE unique (CODE);

alter table MARS_SYS_ROLE
  add constraint UK_SYS_ROLE_CODE unique (CODE);

---添加外键约束---

alter table MARS_SYS_ROLE_DEPT
  add constraint FK_SYS_ROLE_DEPT_DEPT_ID foreign key (DEPT_ID)
  references MARS_SYS_DEPT (ID);
alter table MARS_SYS_ROLE_DEPT
  add constraint FK_SYS_ROLE_DEPT_ROLE_ID foreign key (ROLE_ID)
  references MARS_SYS_ROLE (ID);

alter table MARS_SYS_ROLE_MENU
  add constraint FK_SYS_ROLE_MENU_MENU_ID foreign key (MENU_ID)
  references MARS_SYS_MENU (ID);
alter table MARS_SYS_ROLE_MENU
  add constraint FK_SYS_ROLE_MENU_ROLE_ID foreign key (ROLE_ID)
  references MARS_SYS_ROLE (ID);

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

alter table MARS_SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_ROLE_ID foreign key (ROLE_ID)
  references MARS_SYS_ROLE (ID);
alter table MARS_SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_USER_ID foreign key (USER_ID)
  references MARS_SYS_USER (ID);

---删除外键约束---

alter table 表名 drop constraint 约束名;

alter table MARS_SYS_ROLE_DEPT drop constraint FK_SYS_ROLE_DEPT_DEPT_ID;
alter table MARS_SYS_ROLE_DEPT drop constraint FK_SYS_ROLE_DEPT_ROLE_ID;

alter table MARS_SYS_ROLE_MENU drop constraint FK_SYS_ROLE_MENU_MENU_ID;
alter table MARS_SYS_ROLE_MENU drop constraint FK_SYS_ROLE_MENU_ROLE_ID;

alter table MARS_SYS_USER drop constraint FK_SYS_USER_DEPT_ID;
alter table MARS_SYS_USER drop constraint FK_SYS_USER_COUNTRY_CODE;
alter table MARS_SYS_USER drop constraint FK_SYS_USER_NATION_CODE;
alter table MARS_SYS_USER drop constraint FK_SYS_USER_POLITICAL_STATUS;

alter table MARS_SYS_USER_ROLE drop constraint FK_SYS_USER_ROLE_ROLE_ID;
alter table MARS_SYS_USER_ROLE drop constraint FK_SYS_USER_ROLE_USER_ID;

