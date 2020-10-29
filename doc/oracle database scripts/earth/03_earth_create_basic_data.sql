prompt PL/SQL Developer Export Tables for user EARTH@ORCL
prompt Created by Administrator on 2020年5月20日
set feedback off
set define off

prompt Loading TAB_EARTH_CONFIG...
insert into TAB_EARTH_CONFIG (id, value, label, type, description, sort, create_by, create_time, last_update_by, last_update_time, remarks, del_flag)
values (1, '#14889A', 'theme', 'color', '主题色', 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, '主题色', 0);
commit;
prompt 1 records loaded

prompt Loading TAB_EARTH_DEPT...
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (1, '轻尘集团', null, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (2, '牧尘集团', null, 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (3, '三国集团', null, 2, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (4, '上海分公司', 2, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (5, '北京分公司', 1, 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (6, '北京分公司', 2, 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (7, '技术部', 5, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (8, '技术部', 4, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (9, '技术部', 6, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (10, '市场部', 5, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (11, '市场部', 6, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (12, '魏国', 3, 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (13, '蜀国', 3, 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
insert into TAB_EARTH_DEPT (id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (14, '吴国', 3, 2, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, 0);
commit;
prompt 14 records loaded

prompt Loading TAB_EARTH_DICT...
insert into TAB_EARTH_DICT (id, value, label, type, description, sort, create_by, create_time, last_update_by, last_update_time, remarks, del_flag)
values (1, 'male', '男', 'sex', '性别', 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, '性别', 0);
insert into TAB_EARTH_DICT (id, value, label, type, description, sort, create_by, create_time, last_update_by, last_update_time, remarks, del_flag)
values (2, 'female', '女', 'sex', '性别', 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null, '性别', 0);
commit;
prompt 2 records loaded

prompt Loading TAB_EARTH_MENU...
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (1, '系统管理', 0, null, null, 0, 'el-icon-setting', 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (2, '用户管理', 1, '/sys/user', null, 1, 'el-icon-service', 1, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (3, '查看', 2, null, 'sys:user:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (4, '新增', 2, null, 'sys:user:add', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (5, '修改', 2, null, 'sys:user:edit', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (6, '删除', 2, null, 'sys:user:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (7, '机构管理', 1, '/sys/dept', null, 1, 'el-icon-news', 2, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (8, '查看', 7, null, 'sys:dept:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (9, '新增', 7, null, 'sys:dept:add', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (10, '修改', 7, null, 'sys:dept:edit', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (11, '删除', 7, null, 'sys:dept:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (12, '角色管理', 1, '/sys/role', null, 1, 'el-icon-view', 4, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (13, '查看', 12, null, 'sys:role:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (14, '新增', 12, null, 'sys:role:add', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (15, '修改', 12, null, 'sys:role:edit', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (16, '删除', 12, null, 'sys:role:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (17, '菜单管理', 1, '/sys/menu', null, 1, 'el-icon-menu', 5, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (18, '查看', 17, null, 'sys:menu:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (19, '新增', 17, null, 'sys:menu:add', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (20, '修改', 17, null, 'sys:menu:edit', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (21, '删除', 17, null, 'sys:menu:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (22, '字典管理', 1, '/sys/dict', null, 1, 'el-icon-edit-outline', 7, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (23, '查看', 22, null, 'sys:dict:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (24, '新增', 22, null, 'sys:dict:add', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (25, '修改', 22, null, 'sys:dict:edit', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (26, '删除', 22, null, 'sys:dict:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (27, '系统配置', 1, '/sys/config', null, 1, 'el-icon-edit-outline', 7, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (28, '查看', 27, null, 'sys:config:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (29, '新增', 27, null, 'sys:config:add', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (30, '修改', 27, null, 'sys:config:edit', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (31, '删除', 27, null, 'sys:config:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (32, '登录日志', 1, '/sys/loginlog', null, 1, 'el-icon-info', 8, null, null, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (33, '查看', 32, null, 'sys:loginlog:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (34, '删除', 32, null, 'sys:loginlog:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (35, '操作日志', 1, '/sys/log', null, 1, 'el-icon-info', 8, null, null, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (36, '查看', 35, null, 'sys:log:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (37, '删除', 35, null, 'sys:log:delete', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (38, '系统监控', 0, null, null, 0, 'el-icon-info', 4, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (39, '数据监控', 38, 'http://127.0.0.1:8001/druid/login.html', null, 1, 'el-icon-warning', 0, null, null, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (40, '查看', 39, null, 'sys:druid:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (41, '服务监控', 38, 'http://127.0.0.1:8000/', null, 1, 'el-icon-view', 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (42, '查看', 41, null, 'sys:monitor:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (43, '服务治理', 0, null, null, 0, 'el-icon-service', 2, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (44, '注册中心', 43, 'http://127.0.0.1:8500', null, 1, ' el-icon-view', 0, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (45, '查看', 44, null, 'sys:consul:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (46, '接口文档', 0, 'http://127.0.0.1:8001/swagger-ui.html', null, 1, 'el-icon-document', 3, null, null, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (47, '查看', 46, null, 'sys:swagger:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (48, '代码生成', 0, '/generator/generator', null, 1, 'el-icon-star-on', 5, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (49, '查看', 48, null, 'sys:generator:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (50, '在线用户', 0, '/sys/online', null, 1, 'el-icon-view', 5, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (51, '查看', 50, null, 'sys:online:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (52, '使用案例', 0, null, null, 0, 'el-icon-picture-outline', 6, null, null, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (53, '国际化', 52, '/demo/i18n', null, 1, 'el-icon-edit', 1, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (54, '查看', 53, null, 'sys:dict:view', 2, null, 0, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (55, '换皮肤', 52, '/demo/theme', null, 1, 'el-icon-picture', 2, null, null, null, null, 0);
insert into TAB_EARTH_MENU (id, name, parent_id, url, perms, type, icon, order_num, create_by, create_time, last_update_by, last_update_time, del_flag)
values (56, '查看', 55, null, 'sys:dict:view', 2, null, 0, null, null, null, null, 0);
commit;
prompt 56 records loaded

prompt Loading TAB_EARTH_ROLE...
insert into TAB_EARTH_ROLE (id, name, remark, create_by, create_time, last_update_by, last_update_time, del_flag)
values (1, 'admin', '超级管理员', 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_ROLE (id, name, remark, create_by, create_time, last_update_by, last_update_time, del_flag)
values (2, 'mng', '项目经理', 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_ROLE (id, name, remark, create_by, create_time, last_update_by, last_update_time, del_flag)
values (3, 'dev', '开发人员', 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_ROLE (id, name, remark, create_by, create_time, last_update_by, last_update_time, del_flag)
values (4, 'test', '测试人员', 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
commit;
prompt 4 records loaded

prompt Loading TAB_EARTH_ROLE_DEPT...
insert into TAB_EARTH_ROLE_DEPT (id, role_id, dept_id, create_by, create_time, last_update_by, last_update_time)
values (1, 1, 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into TAB_EARTH_ROLE_DEPT (id, role_id, dept_id, create_by, create_time, last_update_by, last_update_time)
values (2, 2, 2, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into TAB_EARTH_ROLE_DEPT (id, role_id, dept_id, create_by, create_time, last_update_by, last_update_time)
values (3, 3, 3, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 3 records loaded

prompt Loading TAB_EARTH_ROLE_MENU...
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (1, 2, 1, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (2, 2, 2, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (3, 2, 3, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (4, 2, 4, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (5, 2, 5, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (6, 2, 6, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (7, 2, 7, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (8, 2, 8, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (9, 2, 9, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (10, 2, 10, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (11, 2, 11, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (12, 2, 12, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (13, 2, 13, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (14, 2, 14, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (15, 2, 15, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (16, 2, 16, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (17, 2, 17, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (18, 2, 18, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (19, 2, 19, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (20, 2, 20, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (21, 2, 21, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (22, 2, 22, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (23, 2, 23, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (24, 2, 24, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (25, 2, 25, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (26, 2, 26, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (27, 2, 27, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (28, 2, 28, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (29, 2, 29, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (30, 2, 30, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (31, 2, 31, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (32, 2, 32, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (33, 2, 33, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (34, 2, 34, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (35, 2, 35, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (36, 2, 36, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (37, 2, 37, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (38, 2, 43, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (39, 2, 44, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (40, 2, 45, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (41, 2, 46, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (42, 2, 47, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (43, 2, 38, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (44, 2, 39, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (45, 2, 40, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (46, 2, 41, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (47, 2, 42, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (48, 2, 48, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (49, 2, 49, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (50, 2, 50, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (51, 2, 51, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (52, 2, 52, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (53, 2, 53, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (54, 2, 54, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (55, 2, 55, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (56, 2, 56, null, null, null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (57, 3, 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (58, 3, 2, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (59, 3, 3, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (60, 3, 4, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (61, 3, 5, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (62, 3, 6, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (63, 3, 7, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (64, 3, 8, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (65, 3, 12, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (66, 3, 13, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (67, 3, 17, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (68, 3, 18, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (69, 3, 22, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (70, 3, 23, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (71, 3, 24, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (72, 3, 25, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (73, 3, 26, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (74, 3, 27, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (75, 3, 28, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (76, 3, 29, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (77, 3, 30, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (78, 3, 31, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (79, 3, 32, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (80, 3, 33, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (81, 3, 35, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (82, 3, 36, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (83, 3, 43, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (84, 3, 44, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (85, 3, 45, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (86, 3, 38, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (87, 3, 39, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (88, 3, 40, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (89, 3, 41, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (90, 3, 42, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (91, 3, 50, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (92, 3, 51, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (93, 4, 1, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (94, 4, 2, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (95, 4, 3, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (96, 4, 7, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (97, 4, 8, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (98, 4, 17, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (99, 4, 18, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (100, 4, 32, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
commit;
prompt 100 records committed...
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (101, 4, 33, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (102, 4, 35, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (103, 4, 36, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (104, 4, 46, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (105, 4, 47, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (106, 4, 50, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into TAB_EARTH_ROLE_MENU (id, role_id, menu_id, create_by, create_time, last_update_by, last_update_time)
values (107, 4, 51, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
commit;
prompt 107 records loaded

prompt Loading TAB_EARTH_USER...
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (1, 'admin', '超管', null, 'bd1718f058d8a02468134432b8656a86', 'YzcmCZNvbXocrsz9dm8e', 'admin@qq.com', '13612345678', 1, 4, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (2, 'liubei', '刘备', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 7, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (3, 'zhaoyun', '赵云', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 7, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (4, 'zhugeliang', '诸葛亮', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 7, 11, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (5, 'caocao', '曹操', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 8, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (6, 'dianwei', '典韦', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 10, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (7, 'xiahoudun', '夏侯惇', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 8, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (8, 'xunyu', '荀彧', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 10, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (9, 'sunquan', '孙权', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 10, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (10, 'zhouyu', '周瑜', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 11, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (11, 'luxun', '陆逊', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 11, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
insert into TAB_EARTH_USER (id, name, nick_name, avatar, password, salt, email, mobile, status, dept_id, create_by, create_time, last_update_by, last_update_time, del_flag)
values (12, 'huanggai', '黄盖', null, 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 11, 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-01-2020 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 0);
commit;
prompt 12 records loaded

prompt Loading TAB_EARTH_USER_ROLE...
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (1, 1, 1, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (2, 2, 2, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (3, 3, 3, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (4, 4, 4, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (5, 5, 2, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (6, 6, 3, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (7, 7, 3, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (8, 8, 4, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (9, 9, 2, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (10, 10, 2, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (11, 11, 2, null, null, null, null);
insert into TAB_EARTH_USER_ROLE (id, user_id, role_id, create_by, create_time, last_update_by, last_update_time)
values (12, 12, 3, null, null, null, null);
commit;
prompt 12 records loaded

set feedback on
set define on
prompt Done
