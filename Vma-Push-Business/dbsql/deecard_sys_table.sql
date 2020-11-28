DROP TABLE IF EXISTS sys_config;                              /* 系统配置表*/
DROP TABLE IF EXISTS index_config;                            /* ID序号控制表 */

DROP TABLE IF EXISTS admin;                                   /* 后台管理员用户表 */
DROP TABLE IF EXISTS admin_menu_rela;                         /* 后台管理员菜单权限表*/
DROP TABLE IF EXISTS menu_resource;                           /* 后台菜单表 */
DROP TABLE IF EXISTS menu_info;                               /* 后台菜单表(作废) */

/**
 * 系统全局参数配置表
 */
CREATE TABLE sys_config (
	id                    	int(11)              	NOT NULL					COMMENT '',
  	im_private            	varchar(1024)       	DEFAULT NULL				COMMENT '',
  	im_publick            	varchar(1024)       	DEFAULT NULL				COMMENT '',
  	im_sdk_app            	varchar(64)         	DEFAULT NULL				COMMENT '',
  	account_type          	varchar(64)         	DEFAULT NULL				COMMENT '',
  	wx_pay_callback_url   varchar(128)        	DEFAULT NULL				COMMENT '',
  	branch                	varchar(128)        	DEFAULT NULL				COMMENT '',
  	img_url               	varchar(128)        	DEFAULT NULL				COMMENT '',
  	mobile_logo           	varchar(256)        	DEFAULT NULL				COMMENT '',
  	wechat_logo           	varchar(256)        	DEFAULT NULL				COMMENT '',
  	location_logo         	varchar(256)        	DEFAULT NULL				COMMENT '',
  	qiniu_url             	varchar(128)        	DEFAULT NULL				COMMENT '',
  	qiniu_bucket          	varchar(32)         	DEFAULT NULL				COMMENT '',
  	qiniu_zone				varchar(16)				DEFAULT NULL				COMMENT 'V1.0.4->七牛云对应的区域: 0->华东, 1->??, 2->华南',
  	qiniu_access_key      	varchar(128)        	DEFAULT NULL				COMMENT '',
  	qiniu_secret_key      	varchar(128)        	DEFAULT NULL				COMMENT '',
  	pf_corp_id				varchar(64)				DEFAULT NULL 				COMMENT 'V1.0.0->平台_企业微信ID',
  	pf_mini_appid			varchar(64)				DEFAULT NULL 				COMMENT 'V1.0.0->平台_小程序ID',
  	pf_default_deptid		varchar(50)				DEFAULT NULL				COMMENT 'V1.0.0->平台_员工默认企业微信部门ID',
  	PRIMARY KEY (id),UNIQUE KEY PK_sys_config(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

/**
 * ID序号控制表
 * (使用MyISAM事务，不要用InnoDB)
 */
CREATE TABLE index_config (
  	id                    	varchar(36)        		NOT NULL        			COMMENT '项目Id',
  	prix                  	varchar(32)        		DEFAULT NULL   			COMMENT '前缀',
  	no                    	varchar(21)        		DEFAULT NULL   			COMMENT '编码',
  	dept_id               	varchar(36)        		DEFAULT NULL   			COMMENT '部门Id',
  	enterprise_id         	varchar(36)        		DEFAULT NULL   			COMMENT '企业Id',
  	create_time           	datetime            	DEFAULT NULL   			COMMENT '创建时间',
  	modified_time         	datetime            	DEFAULT NULL   			COMMENT '修改时间',
  	PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ID序号控制表';

/**
 * 后台管理员用户表
 */
CREATE TABLE admin (
  	id             			varchar(36)     		NOT NULL					COMMENT '物理主键--统一外键名admin_id',
  	login          			varchar(32)     		DEFAULT NULL         		COMMENT '登录账号',
  	pass_word      			varchar(32)     		DEFAULT NULL         		COMMENT '密码',
  	name           			varchar(32)     		DEFAULT NULL         		COMMENT '姓名',
  	phone          			varchar(32)     		DEFAULT NULL         		COMMENT '联系电话',
  	role           			int(11)         		DEFAULT '0'           		COMMENT '0普通账号(运营者), 1超级管理员账号',
  	status         			int(11)         		DEFAULT '1'           		COMMENT '1有效0无效2表示过期',
  	agent          			varchar(32)     		DEFAULT NULL         		COMMENT '代理商',
  	type           			int(11)         		DEFAULT NULL          		COMMENT '1超级后台2贴牌商3地区总代理4代理商5企业',
  	login_time     		datetime         		DEFAULT NULL         		COMMENT '登录时间',
  	head_icon      			varchar(255)    		DEFAULT NULL         		COMMENT '头像',
  	remark         			varchar(200)    		DEFAULT NULL         		COMMENT '备注',
  	create_time    		datetime         		DEFAULT NULL         		COMMENT '创建时间',
  	create_id      			varchar(36)     		DEFAULT NULL         		COMMENT '创建自己的企业ID = enterprise.id, 小程序创建则填写为00000000-0000-0000-0000-000000000000',
  	custom_id      			varchar(36)     		DEFAULT NULL         		COMMENT '所属企业ID = enterprise.id, 超级后台默认为0， 似乎应该改为enterprise_id',
  	open_id            		varchar(64)          	DEFAULT ''          		COMMENT 'V2.0.0->FROM微信: 微信开放ID = user_info.openid',
   	PRIMARY KEY (id),UNIQUE KEY PK_admin(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='后台管理员用户表';
-- CONSTRAINT FK_admin_enterprise 	foreign key(custom_id) 		references enterprise(id),
-- CONSTRAINT FK_admin_creater 		foreign key(create_id) 		references enterprise(id)

/**
 * 作废：后台菜单表(已经被menu_resource表取代，后续版本逐步去掉)
 */
CREATE TABLE menu_info(
  	id            			int(11)                	NOT NULL AUTO_INCREMENT		COMMENT '',
  	title         			varchar(16)            	NOT NULL DEFAULT ''  		COMMENT '名称',
  	url           			text                    NOT NULL DEFAULT ''       COMMENT '地址',
  	menu_type     			int(2)                 	NOT NULL DEFAULT '1'  		COMMENT '类型：1超级管理 2管理员 3店铺主帐号 4匠人主帐号 5店铺或者匠人的管理员',
  	description   			varchar(128)           	DEFAULT NULL          		COMMENT '描述',
  	parent_id     			int(11)                	NOT NULL DEFAULT '0'  		COMMENT '父菜单id',
  	menu_icon     			varchar(32)            	DEFAULT NULL          		COMMENT '图标',
  	code          			varchar(32)            	DEFAULT NULL          		COMMENT '唯一码',
  	sort_no       			int(11)                	DEFAULT '0'            	COMMENT '序号',
  	target        			int(2)                 	DEFAULT '0'            	COMMENT '打开方式：0、默认打开方式；1、当前页，2、新窗口',
  	status        			int(2)                 	NOT NULL DEFAULT '1'   	COMMENT '状态：0、禁用；1、启用',
  	PRIMARY KEY (id),UNIQUE KEY PK_menu_info(code) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

/**
 * 后台菜单表
 */
CREATE TABLE menu_resource (
  	id             		varchar(36)            	NOT NULL DEFAULT ''       	COMMENT '物理主键--统一外键名menu_id',
  	parent_id      		varchar(36)            DEFAULT NULL               COMMENT '父级菜单',
  	name           		varchar(100)           	DEFAULT NULL               COMMENT '菜单名称',
  	url            		varchar(100)           	DEFAULT NULL               COMMENT 'url地址',
  	create_time    	datetime               	DEFAULT NULL               COMMENT '创建时间',
  	create_by      		varchar(36)            DEFAULT NULL               COMMENT '创建人',
  	update_time    	datetime               	DEFAULT NULL               COMMENT '更新时间',
  	update_by      		varchar(36)           	DEFAULT NULL               COMMENT '更新人',
  	role           		varchar(2)             DEFAULT NULL               COMMENT '所属角色角色（1 超级后台  2 贴牌商 3 地区总代 4 代理商  5 企业）. by PLH 有点多余',
  	rank           		varchar(2)             DEFAULT NULL               COMMENT '节点层级',
  	is_leaf        		varchar(2)             DEFAULT NULL               COMMENT '是否叶子节点（0 否，1是）',
  	seq            		int(11)                	DEFAULT NULL               COMMENT '顺序',
  	target         		int(11)                	DEFAULT '1'					COMMENT '',
  	PRIMARY KEY (id), UNIQUE KEY PK_menu_resource(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='后台菜单表';

/**
 * 后台管理员菜单权限表
 */
CREATE TABLE admin_menu_rela (
  	id            			varchar(36)         	NOT NULL       				COMMENT '物理主键',
  	menu_id       			varchar(36)         	DEFAULT NULL   			COMMENT '菜单ID',
  	admin_id      			varchar(36)         	DEFAULT NULL   			COMMENT '员工ID',
  	create_time   			datetime             	DEFAULT NULL   			COMMENT '创建时间',
  	status        			int(11)              	DEFAULT '1'				COMMENT '状态',
  	PRIMARY KEY (id), UNIQUE KEY PK_admin_menu_rela (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='后台管理员菜单权限表';

-- CONSTRAINT FK_adminmenurela_menu 		foreign key(menu_id) 		references menu_resource(id),
-- CONSTRAINT FK_adminmenurela_admin 		foreign key(admin_id) 		references admin(id)

