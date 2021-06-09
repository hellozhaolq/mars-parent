prompt Loading MARS_SYS_MENU...
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('1', '系统管理', null, null, null, 0, null, null, null, null, null, '0', 0, 'el-icon-setting', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('2', '用户管理', null, null, null, 1, null, '/sys/user', null, null, null, '1', 1, 'el-icon-service', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('3', '查看', null, null, 'sys:user:view', 2, null, null, null, null, null, '2', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('4', '新增', null, null, 'sys:user:add', 2, null, null, null, null, null, '2', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('5', '修改', null, null, 'sys:user:edit', 2, null, null, null, null, null, '2', 3, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('6', '删除', null, null, 'sys:user:delete', 2, null, null, null, null, null, '2', 4, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('7', '机构管理', null, null, null, 1, null, '/sys/dept', null, null, null, '1', 2, 'el-icon-news', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('8', '查看', null, null, 'sys:dept:view', 2, null, null, null, null, null, '7', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('9', '新增', null, null, 'sys:dept:add', 2, null, null, null, null, null, '7', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('10', '修改', null, null, 'sys:dept:edit', 2, null, null, null, null, null, '7', 3, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('11', '删除', null, null, 'sys:dept:delete', 2, null, null, null, null, null, '7', 4, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('12', '角色管理', null, null, null, 1, null, '/sys/role', null, null, null, '1', 4, 'el-icon-view', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('13', '查看', null, null, 'sys:role:view', 2, null, null, null, null, null, '12', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('14', '新增', null, null, 'sys:role:add', 2, null, null, null, null, null, '12', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('15', '修改', null, null, 'sys:role:edit', 2, null, null, null, null, null, '12', 3, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('16', '删除', null, null, 'sys:role:delete', 2, null, null, null, null, null, '12', 4, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('17', '菜单管理', null, null, null, 1, null, '/sys/menu', null, null, null, '1', 5, 'el-icon-menu', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('18', '查看', null, null, 'sys:menu:view', 2, null, null, null, null, null, '17', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('19', '新增', null, null, 'sys:menu:add', 2, null, null, null, null, null, '17', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('20', '修改', null, null, 'sys:menu:edit', 2, null, null, null, null, null, '17', 3, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('21', '删除', null, null, 'sys:menu:delete', 2, null, null, null, null, null, '17', 4, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('22', '字典管理', null, null, null, 1, null, '/sys/dict', null, null, null, '1', 7, 'el-icon-edit-outline', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('23', '查看', null, null, 'sys:dict:view', 2, null, null, null, null, null, '22', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('24', '新增', null, null, 'sys:dict:add', 2, null, null, null, null, null, '22', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('25', '修改', null, null, 'sys:dict:edit', 2, null, null, null, null, null, '22', 3, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('26', '删除', null, null, 'sys:dict:delete', 2, null, null, null, null, null, '22', 4, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('27', '系统配置', null, null, null, 1, null, '/sys/config', null, null, null, '1', 7, 'el-icon-edit-outline', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('28', '查看', null, null, 'sys:config:view', 2, null, null, null, null, null, '27', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('29', '新增', null, null, 'sys:config:add', 2, null, null, null, null, null, '27', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('30', '修改', null, null, 'sys:config:edit', 2, null, null, null, null, null, '27', 3, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('31', '删除', null, null, 'sys:config:delete', 2, null, null, null, null, null, '27', 4, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('32', '登录日志', null, null, null, 1, null, '/sys/loginlog', null, null, null, '1', 8, 'el-icon-info', null, null, 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('33', '查看', null, null, 'sys:loginlog:view', 2, null, null, null, null, null, '32', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('34', '删除', null, null, 'sys:loginlog:delete', 2, null, null, null, null, null, '32', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('35', '操作日志', null, null, null, 1, null, '/sys/log', null, null, null, '1', 8, 'el-icon-info', null, null, 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('36', '查看', null, null, 'sys:log:view', 2, null, null, null, null, null, '35', 1, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('37', '删除', null, null, 'sys:log:delete', 2, null, null, null, null, null, '35', 2, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('38', '系统监控', null, null, null, 0, null, null, null, null, null, '0', 4, 'el-icon-info', 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('39', '数据监控', null, null, null, 1, null, 'http://127.0.0.1:8001/druid/login.html', null, null, null, '38', 0, 'el-icon-warning', null, null, 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('40', '查看', null, null, 'sys:druid:view', 2, null, null, null, null, null, '39', 0, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('41', '服务监控', null, null, null, 1, null, 'http://127.0.0.1:8000/', null, null, null, '38', 1, 'el-icon-view', 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('42', '查看', null, null, 'sys:monitor:view', 2, null, null, null, null, null, '41', 0, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('43', '服务治理', null, null, null, 0, null, null, null, null, null, '0', 2, 'el-icon-service', 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('44', '注册中心', null, null, null, 1, null, 'http://127.0.0.1:8500', null, null, null, '43', 0, ' el-icon-view', 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('45', '查看', null, null, 'sys:consul:view', 2, null, null, null, null, null, '44', 0, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('46', '接口文档', null, null, null, 1, null, 'http://127.0.0.1:8001/swagger-ui.html', null, null, null, '0', 3, 'el-icon-document', null, null, 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('47', '查看', null, null, 'sys:swagger:view', 2, null, null, null, null, null, '46', 0, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('48', '代码生成', null, null, null, 1, null, '/generator/generator', null, null, null, '0', 5, 'el-icon-star-on', 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('49', '查看', null, null, 'sys:generator:view', 2, null, null, null, null, null, '48', 0, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('50', '在线用户', null, null, null, 1, null, '/sys/online', null, null, null, '0', 5, 'el-icon-view', 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('51', '查看', null, null, 'sys:online:view', 2, null, null, null, null, null, '50', 0, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('52', '使用案例', null, null, null, 0, null, null, null, null, null, '0', 6, 'el-icon-picture-outline', null, null, 'admin', to_timestamp('01-01-2020 10:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('53', '国际化', null, null, null, 1, null, '/demo/i18n', null, null, null, '52', 1, 'el-icon-edit', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('54', '查看', null, null, 'sys:dict:view', 2, null, null, null, null, null, '53', 0, null, null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('55', '换皮肤', null, null, null, 1, null, '/demo/theme', null, null, null, '52', 2, 'el-icon-picture', null, null, null, null, 1, 0);
insert into MARS_SYS_MENU (id, name, code, remark, perms, type, url_type, url, scheme, path, target, parent_id, order_num, icon, create_by, create_time, last_update_by, last_update_time, status, del_flag)
values ('56', '查看', null, null, 'sys:dict:view', 2, null, null, null, null, null, '55', 0, null, null, null, null, null, 1, 0);
commit;
prompt 56 records loaded