DROP TABLE IF EXISTS user_info;                               /* 小程序用户表 */
DROP TABLE IF EXISTS enterprise;                              /* 企业表 */
DROP TABLE IF EXISTS deploy;                                  /* 企业表小程序配置表*/
DROP TABLE IF EXISTS department;                              /* 部门表*/
DROP TABLE IF EXISTS staff;                                   /* 企业员工表 */
DROP TABLE IF EXISTS staff_image_rela;                        /* 企业员工个人照片记录表*/
DROP TABLE IF EXISTS staff_quit;								/* 员工离职交接表 */
DROP TABLE IF EXISTS user_staff_rela;                         /* 小程序用户与员工的关联表: 名片夹*/
DROP TABLE IF EXISTS label_type;                              /* 标签类别---已经作废*/
DROP TABLE IF EXISTS label_info;                              /* 用户标签*/
DROP TABLE IF EXISTS label_user_rela;                         /* 用户标签关联表*/
DROP TABLE IF EXISTS user_label_info;							/* 同label_user_rela, 后续去掉 */

DROP TABLE IF EXISTS bi_action_log;                           /* 每日的BI事件统计,数据由定时任务写入*/
DROP TABLE IF EXISTS bi_attach_user_log;                      /* 员工跟进记录日志表 -- 未使用*/
DROP TABLE IF EXISTS bi_im_log;                               /* 员工IM记录日志表 -- 未使用*/
DROP TABLE IF EXISTS bi_order_log;                            /* 员工订单记录日志表*/
DROP TABLE IF EXISTS bi_user_log;                             /* 新增客户BI表*/
DROP TABLE IF EXISTS child_dept_enter;                        /* ??? -- 未使用*/
DROP TABLE IF EXISTS child_dept_enter_copy;                   /* ??? -- 未使用*/
DROP TABLE IF EXISTS circle_comment_log;                      /* 朋友圈评论表*/
DROP TABLE IF EXISTS circle_company;                          /* 企业朋友圈*/
DROP TABLE IF EXISTS circle_img;                              /* 朋友圈图片表*/
DROP TABLE IF EXISTS circle_zan_log                           /* 点赞记录表*/;

DROP TABLE IF EXISTS click_action                             /* 点击时间字典表 -- 未使用*/;
DROP TABLE IF EXISTS click_action_history;                    /* 历史点击事件表 -- 未使用*/
DROP TABLE IF EXISTS click_action_log;                        /* 点击事件日志表*/

DROP TABLE IF EXISTS enter_api_rela;                          /* 企业api*/
DROP TABLE IF EXISTS exception_log;                           /* 异常日志记录表*/


DROP TABLE IF EXISTS kj_point_log;                            /* 迹点日志表*/
DROP TABLE IF EXISTS kj_point_rate;                           /* 迹点比列表*/
DROP TABLE IF EXISTS logistics;                               /* 物流表 -- 未使用*/
DROP TABLE IF EXISTS message_log;                             /* 短信发送记录表 -- 未使用*/
DROP TABLE IF EXISTS num;                                     /* ??? -- 未使用*/
DROP TABLE IF EXISTS quick_reply;                             /* 快捷回复表 -- 未使用*/
DROP TABLE IF EXISTS shop_template;                           /* 模板管理*/
DROP TABLE IF EXISTS totals;                                  /* 点赞统计表*/
DROP TABLE IF EXISTS user_address;                            /* 收货地址表*/
DROP TABLE IF EXISTS user_form_rela;                          /* 用户关系表*/
DROP TABLE IF EXISTS user_menu_ref;                           /* 用户_菜单关系表*/
DROP TABLE IF EXISTS web_apply_log;                           /* 合作申请记录表*/
DROP TABLE IF EXISTS web_config;                              /* 后台前端配置 -- 未使用*/
DROP TABLE IF EXISTS recommend_card;                          /* 推荐名片表*/

DROP TABLE IF EXISTS enterprise_file;							/* 企业上传的文件表*/
DROP TABLE IF EXISTS enterprise_file_dir;						/* 企业建的文件目录表*/
/**
 * 小程序用户表
 */
CREATE TABLE user_info(
  	id                 		varchar(36)      		NOT NULL            		COMMENT '物理主键--统一外键名user_id',
  	wx_soft_id         	varchar(36)            	DEFAULT NULL       		COMMENT '微信小程序ID = deploy.app_id',
  	open_id            		varchar(64)          	DEFAULT ''          		COMMENT 'FROM微信: 微信开放ID',
  	nick_name          		varchar(64)            	DEFAULT ''         			COMMENT 'FROM微信: 昵称',
  	name               		varchar(32)           	DEFAULT NULL				COMMENT '??????',
  	sex                		int(11)                	DEFAULT '1'				COMMENT 'FROM微信: 性别---1男,2女',
  	phone              		varchar(128)          	DEFAULT NULL				COMMENT 'FROM微信: 手机号',
  	head_icon          		varchar(256)           	DEFAULT ''         			COMMENT 'FROM微信: 头像',
  	create_time        	datetime               	DEFAULT NULL       		COMMENT '创建时间',
  	modify_time        	datetime                DEFAULT NULL       		COMMENT '修改时间',
  	last_action_time   	datetime               	DEFAULT NULL        		COMMENT '最近动作时间??????',
  	last_attach_time   	datetime               	DEFAULT NULL        		COMMENT '最后跟进时间??????',
  	hx_im_login        	varchar(64)           	DEFAULT NULL				COMMENT '暂时没有用, 后面逐步版本去掉??????',
  	hx_im_password     	varchar(64)           	DEFAULT NULL				COMMENT '暂时没有用, 后面逐步版本去掉??????',
  	session_key        	varchar(128)          	DEFAULT NULL				COMMENT '??????',
  	last_enterprise_id		varchar(36)				DEFAULT NULL				COMMENT 'BY V1.0.0: 员工最后一次使用的企业ID(一个员工可能有多个企业)',
  	last_staff_id			varchar(36)				DEFAULT NULL				COMMENT 'BY V1.0.0: 用户最后查看的名片员工ID=staff.id。 如果这个last_staff_id和id在',
  	from_user_id			varchar(36)				DEFAULT NULL				COMMENT 'BY V1.0.0: 用户的来源, 用于奖励小程序裂变的开发。統一小程序用戶身份',
  	PRIMARY KEY (id),UNIQUE KEY PK_user_info(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='小程序用户表';
create unique index uidx_user_openid on user_info(wx_soft_id, open_id);

/**
 *  企业表(企业/代理商/地区总代理/贴牌商)
 */
CREATE TABLE enterprise (
	id                    	varchar(36)      		NOT NULL                  	COMMENT '物理主键--统一外键名enterprise_id',
	parent_id             	varchar(36)       		DEFAULT NULL             	COMMENT '父企业ID = enterprise.id',
	name                  	varchar(128)     		NOT NULL      				COMMENT '企业名称',
	enterprise_no         	varchar(32)       		DEFAULT ''               	COMMENT '企业编码(通过index_config来递增)',
	address               	varchar(128)     		DEFAULT ''                	COMMENT '企业地址',
	phone                 	varchar(64)      		DEFAULT NULL              	COMMENT '联系电话',
  	business_license_url  varchar(256)     		DEFAULT ''                	COMMENT '营业执照图片地址',
  	business_license_code varchar(64)      		DEFAULT ''                	COMMENT '营业执照编码',
  	expiry_time           	varchar(36)      		DEFAULT NULL              	COMMENT '证件有效期',
  	sale_card_num         	int(11)           		DEFAULT '0'               	COMMENT '名片数量',
  	manager_name          	varchar(64)      		DEFAULT ''                 COMMENT '管理员名字',
  	manager_phone         	varchar(64)      		DEFAULT NULL              	COMMENT '管理员手机号',
  	create_staff_id       	varchar(36)      		DEFAULT ''                	COMMENT '创建自己的企业ID=enterprise.id, 似乎和enterprise.parentid重复。小程序创建自己,填写平台00000000-0000-0000-0000-000000000000',
  	create_time           	datetime          		DEFAULT NULL				COMMENT '',
  	modify_time           	datetime          		DEFAULT NULL				COMMENT '',
  	auth_wx_soft          	int(11)           		DEFAULT '0'					COMMENT '',
  	auth_ai_radar         	int(11)           		DEFAULT '0'					COMMENT '',
  	auth_boss_radar       	int(11)           		DEFAULT '0'					COMMENT '',
  	used_car_num          	int(11)           		DEFAULT '0'               	COMMENT '已用卡片数量',
  	password              	varchar(32)       		DEFAULT ''					COMMENT '',
  	role                  	int(11)           		DEFAULT '1'               	COMMENT '角色1企业  2代理商  3地区总代理  4贴牌商',
  	status                	int(11)           		DEFAULT '1'               	COMMENT '状态 1有效0解散',
  	expire_time           	datetime           		DEFAULT NULL             	COMMENT '到期时间',
  	auth_soft             	int(11)           		DEFAULT NULL				COMMENT '',
  	auth_wei              	int(11)           		DEFAULT NULL				COMMENT '',
  	head_icon             	varchar(255)      		DEFAULT NULL				COMMENT '',
  	icon                  	varchar(255)      		DEFAULT NULL             	COMMENT '企业头像',
  	template_id           	varchar(225)      		DEFAULT NULL             	COMMENT '模版id = shop_template.id',
  	template_time         	datetime           		DEFAULT NULL            	COMMENT '模版提交申请时间',
  	is_deploy             	int(11)           		DEFAULT NULL             	COMMENT '是否部署0为待部署1为已部署',
  	login_account         	varchar(32)       		DEFAULT NULL             	COMMENT '登录账号',
  	remark                	varchar(512)      		DEFAULT NULL             	COMMENT '备注',
  	address_info          	varchar(256)      		DEFAULT NULL             	COMMENT '地址json信息',
  	province_code         	varchar(16)       		DEFAULT NULL				COMMENT '',
  	city_code             	varchar(16)       		DEFAULT NULL				COMMENT '',
  	area_code             	varchar(16)       		DEFAULT NULL				COMMENT '',
  	money_init            	int(11)            		DEFAULT NULL            	COMMENT '初始迹点',
  	money_sum             	int(11)            		DEFAULT NULL            	COMMENT '累计迹点',
  	money_leave           	int(11)            		DEFAULT NULL            	COMMENT '剩余迹点',
  	card_sum              	int(11)            		DEFAULT '0'             	COMMENT '累计名片数',
  	website_id				varchar(36)				DEFAULT NULL				COMMENT 'V1.0.0->默认官网',
  	shop_id					varchar(36)				DEFAULT NULL				COMMENT 'V1.0.0->默认商城',
  	PRIMARY KEY (id),UNIQUE KEY PK_enterprise (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业表';
create unique index uidx_enterprise_no on enterprise(enterprise_no);
-- CONSTRAINT FK_enterprise_create_ent 	foreign key(create_staff_id) 		references enterprise(id),
-- CONSTRAINT FK_enterprise_shoptemplate 	foreign key(template_id) 			references shop_template(id)

/**
 * 企业小程序部署表
 */
CREATE TABLE deploy(
  id              	varchar(225)         NOT NULL              	COMMENT '主键',
  CorpID          	varchar(225)         DEFAULT NULL          	COMMENT '企业微信: 公司的CropID',
  contactssecret  	varchar(225)         DEFAULT NULL          	COMMENT '企业微信: 通讯录密钥',
  bosssecret      	varchar(225)         DEFAULT NULL          	COMMENT 'boss雷达密钥',
  aisecret        	varchar(225)         DEFAULT NULL          	COMMENT 'ai雷达密钥',
  boss_AgentId    	varchar(225)         DEFAULT NULL          	COMMENT 'boss雷达应用',
  ai_AgentId      	varchar(225)         DEFAULT NULL          	COMMENT 'ai雷达应用',
  app_id          	varchar(128)         DEFAULT NULL          	COMMENT '小程序id',
  secret          	varchar(256)         DEFAULT NULL          	COMMENT '小程序密钥',
  mch_id          	varchar(256)         DEFAULT NULL          	COMMENT '商户id',
  pay_key         	varchar(128)         DEFAULT NULL          	COMMENT '支付密钥',
  enterprise_id   	varchar(225)         DEFAULT NULL          	COMMENT '企业ID',
  private_key     	varchar(255)         DEFAULT NULL          	COMMENT '腾讯云通讯：IM私钥',
  public_key      	varchar(255)         DEFAULT NULL          	COMMENT '腾讯云通讯：IM共钥',
  skd_app_id      	varchar(255)         DEFAULT NULL				COMMENT '腾讯云通讯: sdkAppId',
  manager_id      	varchar(255)         DEFAULT NULL          	COMMENT '管理员ID',
  message_template	varchar(255)         DEFAULT NULL          	COMMENT '消息模版',
  pay_template    	varchar(255)         DEFAULT NULL          	COMMENT '支付模版',
  /** isPlatform		int 				DEFAULT '1'			COMMENT '是否是平台部署 1->平台, 0->企业独立部署' */
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业小程序部署表';

/**
 * 部门表
 */
CREATE TABLE department(
	id             			varchar(36)          	NOT NULL             		COMMENT '物理主键--统一外键名department_id. 该字段数字在本企业内递增',
	enterprise_id  		varchar(36)          	NOT NULL					COMMENT '物理主键--和id组合成唯一主键',
	parent_id      			varchar(36)          	DEFAULT '1'          		COMMENT '父级部门ID = department.id',
	name           			varchar(64)          	NOT NULL DEFAULT '' 		COMMENT '部门名称',
	`order`          		int(11)               	DEFAULT '1'					COMMENT '显示顺序',
  	wx_id          			varchar(36)          	DEFAULT NULL				COMMENT 'FROM 企业微信：现在值都是空的,是否可以去掉??????',
  	create_time    		datetime              	DEFAULT NULL				COMMENT '创建时间',
  	modify_time    		datetime              	DEFAULT NULL				COMMENT '修改时间',
  	PRIMARY KEY (id,enterprise_id),UNIQUE KEY PK_department (id,enterprise_id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';
-- CONSTRAINT FK_department_enterprise 		foreign key(enterprise_id) 		references enterprise(id)

/**
 * 企业员工表
 */
CREATE TABLE staff (
  	id                		varchar(36)         	NOT NULL					COMMENT '物理主键--统一外键名staff_id',
  	enterprise_id    		varchar(36)            	DEFAULT NULL            	COMMENT '企业ID=department.enterprise_id',
  	department_id    		varchar(36)            	DEFAULT NULL            	COMMENT '部门ID=department.id',
  	wx_id             		varchar(36)            	DEFAULT ''               	COMMENT 'FROM 企业微信：账号,可以在企业微信中修改',
  	weixin           		varchar(64)            	DEFAULT ''					COMMENT 'FROM 微信的用户微信号,用户在手机微信修改',
  	name              		varchar(32)            	NOT NULL DEFAULT ''    	COMMENT '名片字段: 姓名',
  	phone             		varchar(32)            	NOT NULL DEFAULT ''    	COMMENT 'FROM 企业微信：电话',
  	create_time       		datetime               	DEFAULT NULL           	COMMENT '创建时间',
  	status           		int(11)               	DEFAULT NULL            	COMMENT '0->离职, 1正常 2认领中(by 1.0.0重用客迹定义)（keji是暂停使用)，3申请中,4拒绝. 离职后交接人放在staff_quit表, 其离职客户交接标记在user_staff_rela.rela_status中，具体看对应表的字段说明',
  	modify_time      		datetime                DEFAULT NULL				COMMENT '',
  	create_staff_id  		varchar(36)            	DEFAULT NULL				COMMENT '创建改员工的企业 = enterprise.id, 似乎和enterprise_id重复',
  	pass_word        		varchar(32)            	NOT NULL DEFAULT ''		COMMENT '',
  	station          		varchar(128)           	DEFAULT NULL            	COMMENT '名片字段: 岗位',
  	head_icon        		varchar(500)           	DEFAULT ''					COMMENT '名片字段: 头像URL',
  	open_ai          		int(11)                	DEFAULT '0'             	COMMENT 'AI雷达开关',
  	open_boss        		int(11)                	DEFAULT '0'             	COMMENT 'boss雷达开关',
  	mobile           		varchar(64)            	DEFAULT ''					COMMENT '名片字段: 手机号，默认=phone，员工自己可改',
  	mail             		varchar(128)           	DEFAULT ''					COMMENT '名片字段: 邮箱',
  	address          		varchar(128)           	DEFAULT ''					COMMENT '名片字段: 地址',
  	role             		varchar(32)            	DEFAULT NULL				COMMENT 'V1.0.0->系统角色：0管理员, 1运营者, 2普通员工',
  	soft_img_url     		varchar(512)           	DEFAULT NULL				COMMENT 'From小程序_小程序二维码',
  	signature        		varchar(2048)          	DEFAULT NULL				COMMENT '名片字段: 个性签名',
  	discount         		decimal(10,2)          	DEFAULT '100.00'          	COMMENT '名片字段: 商城优惠折扣 0-100',
  	temlate_id       		int(11)               	DEFAULT '1'               	COMMENT '名片字段: 名片模板id分为1、2、3三种模版',
  	discount_type    		int(11)               	DEFAULT '0'               	COMMENT '1 个人特权 0企业特权',
  	vidio_id				varchar(36)				DEFAULT NULL				COMMENT 'V1.0.0->名片字段: 个人视频 = attachment_vedio.id',
  	audio_id				varchar(36)				DEFAULT NULL				COMMENT 'V1.0.0->名片字段: 个人语音 = attachment_audio.id',
  	open_id            		varchar(64)          	DEFAULT ''          		COMMENT 'V1.0.0->FROM微信: 微信开放ID = user_info.openid',
  	company_name			varchar(300)			DEFAULT ''					COMMENT 'V1.0.0->名片字段: 公司名称, 新建员工默认从enterprise.name复制过来, 员工自己可以按自己的要求修改，可以不和enterprise.name相同',
  	first_letter			char(1)					DEFAULT ''					COMMENT 'V1.0.0->For排序，编辑name字段值即更新本字段为其为英文首字母, 不能识别(如表情等特殊字符)用#来替代',
  	share_setup			varchar(300)			DEFAULT ''					COMMENT 'V1.0.0->员工分享设置内容',
  	audit_staff_id			varchar(36)				DEFAULT ''					COMMENT 'V1.0.0->员工申请批复人(管理员或运营者)=staff.id',
	audit_time				datetime                DEFAULT NULL				COMMENT 'V1.0.0->员工申请批复时间',
  	card_img_url           varchar(512)           DEFAULT NULL				COMMENT '分享名片时的背景图Url',
  	PRIMARY KEY (id),	UNIQUE KEY PK_staff(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业员工表';
-- CONSTRAINT FK_staff_department foreign key(enterprise_id, department_id) references department(enterprise_id, id)

/**
 * 员工个人图片关联表
 */
CREATE TABLE staff_image_rela(
	id             			varchar(36)            	NOT NULL              		COMMENT '物理主键(外键名: staff_image_id)',
	staff_id				varchar(36)            	DEFAULT NULL				COMMENT '员工ID = staff.id',
	image_id				varchar(36)            	DEFAULT NULL				COMMENT '图片ID = attachment_image.id',
	`order`        			int               		DEFAULT NULL				COMMENT '显示顺序',
  	status         			int(11)               	DEFAULT NULL             	COMMENT '1有效0无效',
  	create_time    		datetime               	DEFAULT NULL				COMMENT '创建时间',
  	modify_time    		datetime               	DEFAULT NULL				COMMENT '修改时间',
  	PRIMARY KEY (id),	UNIQUE KEY PK_staff_images_rela(id) USING BTREE
  	/* CONSTRAINT FK_staff_images_staff 		foreign key(staff_id) 		references staff_images(id), */
  	/* CONSTRAINT FK_staff_images_image 		foreign key(image_id) 		references attachment_image(id), */
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工个人图片记录表';

/**
 * 员工离职交接表
 */
CREATE TABLE staff_quit (
	id                  	varchar(36)          	NOT NULL					COMMENT '物理主键, fk=quit_id',
	quit_staff_id			varchar(36)				NOT NULL					COMMENT '离职员工ID = staff.id',
	receiver_staff_id		varchar(36)				NOT NULL					COMMENT '接收员工ID = staff.id',
	create_time    		datetime               	DEFAULT NULL				COMMENT '创建(离职)时间',
	PRIMARY KEY(id),	UNIQUE KEY PK_staff_quit(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工离职交接表';
-- ALTER TABLE staff_quit ADD CONSTRAINT FK_staff_quit_quit foreign key(quit_staff_id) 			references staff(id);
-- ALTER TABLE staff_quit ADD CONSTRAINT FK_staff_quit_receiver foreign key(receiver_staff_id) 	references staff(id);

/**
 * 小程序和员工的关系表: 名片夹
 */
CREATE TABLE user_staff_rela (
  	id                  	varchar(36)          	NOT NULL					COMMENT '物理主键',
  	user_id             	varchar(36)          	DEFAULT NULL				COMMENT '小程序用户ID = user_info.id',
  	staff_id            	varchar(36)          	DEFAULT NULL				COMMENT '员工ID = staff.id',
	enterprise_id       	varchar(36)          	DEFAULT NULL				COMMENT '员工所在的企业 = enterprise.id, 冗余字段，后续考虑去除',
	department_id       	varchar(36)           	DEFAULT NULL				COMMENT '员工所在的部分 = deparetment.id, 冗余字段，后续考虑去除',
	froms               	int(11)               	DEFAULT '1'        	 	COMMENT '建立关联的方式：0创建企业1扫码2分享3工作交接4平台推送(如小秘关联)',
	from_user_id        	varchar(36)           	DEFAULT NULL				COMMENT '分享的小程序用户ID = user_info.openid',
	position            	varchar(64)           	DEFAULT NULL				COMMENT '置顶: 按staff_id进行分组(在名片夹中显示的顺序)',
  	status              	int(11)                	DEFAULT '1'					COMMENT '名片关联状态：1启用，0屏蔽',
  	create_time         	datetime              	DEFAULT NULL				COMMENT '创建时间',
  	modify_time         	datetime              	DEFAULT NULL				COMMENT '修改时间',
  	is_zan              	int(11)               	DEFAULT '0'					COMMENT '',
  	name                	varchar(32)           	DEFAULT NULL				COMMENT '',
  	mail                	varchar(64)           	DEFAULT NULL				COMMENT '',
  	company             	varchar(64)           	DEFAULT NULL				COMMENT '',
  	birthday            	varchar(64)           	DEFAULT NULL				COMMENT '',
  	remark              	varchar(128)          	DEFAULT NULL				COMMENT '',
  	phone               	varchar(32)           	DEFAULT NULL				COMMENT '',
  	last_attach_time    	datetime               	DEFAULT NULL				COMMENT '',
  	last_action_time    	datetime               	DEFAULT NULL				COMMENT '',
  	rate                	decimal(10,2)         	DEFAULT '10.00'     	 	COMMENT '预计成交率  每天系统自动计算',
  	clinch_time         	datetime               	DEFAULT NULL        		COMMENT '成交日期',
  	clinch_rate         	decimal(10,0)         	DEFAULT NULL        		COMMENT '成交率',
  	rela_status			int 					DEFAULT '1'					COMMENT 'V1.0.0->客户和员工的关系,1是正常关系, 0->为员工离职后标记为待交接状态, 2->表示客户已经和交接人建立了关系(本表新记录),2状态下如果该原因又复职了,则重新从2改为1(不要新建记录)',
  	is_collect				int 					DEFAULT 0					COMMENT '是否收藏->1:收藏，0:未收藏',
  	PRIMARY KEY (id),KEY PK_user_staff_rela(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='小程序和员工的关系表';
-- CONSTRAINT FK_user_staff_rela_user 	foreign key(user_id) 	references user_info(id),
-- CONSTRAINT FK_user_staff_rela_staff 	foreign key(staff_id) 	references staff(id)

/**
 * 用户标签表
 */
CREATE TABLE label_info (
  	id                 		varchar(36)         	NOT NULL                  	COMMENT '物理主键--统一外键名: label_id',
  	label_name         	varchar(32)         	DEFAULT NULL              	COMMENT '标签名称',
  	type            		int         			DEFAULT NULL				COMMENT '标签类型： 0->系统标签, 1->用户标签',
  	staff_id				varchar(36)				DEFAULT NULL				COMMENT '标签定义人: 用户标签=staff.id, 系统标签值=0000-0000-0000-0000',
  	create_time        	datetime             	DEFAULT NULL				COMMENT '创建时间',
  	modify_time        	datetime             	DEFAULT NULL				COMMENT '修改时间',
  	PRIMARY KEY (id),	KEY PK_label_info(id) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户标签表';

/**
 * 标签类型表(没有用到，暂时作废)
 */
/**
CREATE TABLE label_type (
  	id                		varchar(36)          	DEFAULT NULL 				COMMENT '主键',
  	type_name         		varchar(32)          	DEFAULT NULL 				COMMENT '类别名称',
  	create_time       		datetime              	DEFAULT NULL				COMMENT='标签类别',
  	modify_time       		datetime             	DEFAULT NULL				COMMENT='标签类别',
  	status            		int(11)              	DEFAULT '1'					COMMENT='标签类别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 			COMMENT='标签类别';
*/

/**
 * 用户标签关联表
 */
CREATE TABLE label_user_rela(
  	id                		varchar(36)         	NOT NULL					COMMENT '物理主键ID',
  	label_id          		varchar(36)				DEFAULT NULL				COMMENT '标签ID=label_info.id',
  	user_id           		varchar(36)         	DEFAULT NULL				COMMENT '小程序用户ID  = user_info.id： user_id和staff_id取其一',
  	staff_id          		varchar(36)         	DEFAULT NULL				COMMENT '员工ID = staff.id：            user_id和staff_id取其一',
  	create_time       		datetime             	DEFAULT NULL				COMMENT '创建时间',
  	modify_time       		datetime             	DEFAULT NULL				COMMENT '修改时间',
  	PRIMARY KEY(id),	KEY PK_label_user_rela(id) USING BTREE
  	/* CONSTRAINT FK_label_user_rela_user 	foreign key(user_id) 	references user_info(id), */
  	/* CONSTRAINT FK_label_user_rela_staff 	foreign key(staff_id) 	references staff(id) */
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户标签关联表';
-- ALTER TABLE label_user_rela ADD CONSTRAINT FK_label_user_rela_user foreign key(user_id) 	references user_info(id)

/**
 * 用户标签关联表 From 客迹的多余表，去掉，已经被label_user_rela
 */
 /**
CREATE TABLE user_label_info (
  	id                 		varchar(36)          	NOT NULL              		COMMENT '主键',
  	label_id           		varchar(36)          	DEFAULT NULL          		COMMENT '标签ID',
  	create_time        		datetime              	DEFAULT NULL				COMMENT '',
  	modify_time        		datetime              	DEFAULT NULL				COMMENT '',
  	staff_id           		varchar(36)          	DEFAULT NULL				COMMENT '',
  	PRIMARY KEY (id),UNIQUE KEY PK_user_label_info(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户标签关联表';
*/

/***************************************************************************************************/


CREATE TABLE bi_action_log (
  id               		varchar(36)         	NOT NULL,
  staff_id         		varchar(36)         	DEFAULT NULL        	COMMENT '员工ID',
  action_code     		varchar(16)         	DEFAULT NULL        	COMMENT '业务动作编码',
  num              		int(11)             	DEFAULT NULL       	COMMENT '该动作次数',
  create_time     		datetime             	DEFAULT NULL,
  enterprise_id   		varchar(36)         	DEFAULT NULL        	COMMENT '企业ID',
  department_id   		varchar(36)         	DEFAULT NULL        	COMMENT '部门ID',
  PRIMARY KEY (id),UNIQUE KEY bi_action_log_id_uindex (id) USING BTREE,
  KEY bi_action_log_staff_id_enterprise_id_action_code_index (staff_id,enterprise_id,action_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='每日的BI事件统计,数据由定时任务写入';

CREATE TABLE bi_attach_user_log (
  id               		varchar(36)           	NOT NULL,
  staff_id         		varchar(36)           	DEFAULT NULL        	COMMENT '员工ID',
  num              		int(11)                	DEFAULT NULL       	COMMENT '当天跟进次数',
  create_time      	datetime               	DEFAULT NULL,
  enterprise_id    	varchar(36)           	DEFAULT NULL       	COMMENT '企业ID',
  department_id    	varchar(36)           	DEFAULT NULL       	COMMENT '部门ID',
  PRIMARY KEY (id),UNIQUE KEY bi_attach_user_log_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE bi_im_log (
  id                      	varchar(36)             	NOT NULL,
  num                     	int(11)                  	DEFAULT '0',
  create_time             	datetime                 	DEFAULT NULL,
  enterprise_id           	varchar(36)             	DEFAULT NULL   	COMMENT '企业ID',
  department_id           	varchar(36)             	DEFAULT NULL   	COMMENT '部门ID',
  staff_id                	varchar(36)             	DEFAULT NULL   	COMMENT '员工ID',
  PRIMARY KEY (id),UNIQUE KEY bi_im_log_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE bi_order_log (
  id                     	varchar(36)             	NOT NULL,
  staff_id               	varchar(36)              	DEFAULT NULL   	COMMENT '员工ID',
  num                    	int(11)                  	DEFAULT NULL    	COMMENT '当天成交订单',
  create_time            	datetime                 	DEFAULT NULL,
  enterprise_id          	varchar(36)             	DEFAULT NULL    	COMMENT '企业ID',
  department_id          	varchar(36)             	DEFAULT NULL    	COMMENT '部门ID',
  PRIMARY KEY (id),UNIQUE KEY bi_order_log_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE bi_user_log (
  id                     	varchar(36)            	NOT NULL,
  num                    	int(11)                	DEFAULT '0',
  create_time            	datetime               	DEFAULT NULL,
  enterprise_id          	varchar(36)            	DEFAULT NULL   	COMMENT '企业ID',
  department_id          	varchar(36)            	DEFAULT NULL   	COMMENT '部门ID',
  staff_id               	varchar(36)            	DEFAULT NULL   	COMMENT '员工ID',
  PRIMARY KEY (id),UNIQUE KEY bi_user_log_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新增客户BI表';


CREATE TABLE child_dept_enter (
  id                    	varchar(36)             DEFAULT NULL,
  enterprise_id         	varchar(36)             DEFAULT NULL,
  department_id         	varchar(36)             DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE child_dept_enter_copy (
  id                 		varchar(36)            DEFAULT NULL,
  enterprise_id      		varchar(36)            DEFAULT NULL,
  department_id      		varchar(36)            DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE circle_comment_log (
  id                	varchar(36)            	NOT NULL   			COMMENT '主键',
  content           	varchar(512)           	DEFAULT '' 			COMMENT '评论内容',
  user_id           	varchar(36)            	DEFAULT '' 			COMMENT '用户ID',
  circle_id         	varchar(36)            	NOT NULL   			COMMENT '朋友圈记录ID',
  create_time       	datetime               	DEFAULT NULL,
  modify_time       	datetime               	DEFAULT NULL,
  status            	int(11)               	DEFAULT '1' 		COMMENT '1有效0无效',
  flag              	int(1)                	DEFAULT '0' 		COMMENT '1公司 0 销售人员 2客户',
  PRIMARY KEY (id),UNIQUE KEY circle_comment_log_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='朋友圈评论表';

CREATE TABLE circle_company (
  id               		varchar(36)            	NOT NULL       		COMMENT '主键',
  enterprise_id    	varchar(36)            	NOT NULL       		COMMENT '企业ID',
  employee_id      	varchar(36)            	NOT NULL       		COMMENT '员工ID',
  create_time      	datetime               	DEFAULT NULL   	COMMENT '发布时间',
  zan_num          		int(11)                	DEFAULT '0'    	COMMENT '点赞数',
  ping_num         		int(11)                	DEFAULT '0'    	COMMENT '评论数',
  content          		mediumtext                             		COMMENT '富文本内容',
  modify_time      	datetime               	DEFAULT NULL,
  flag             		int(1)                 	DEFAULT '0'    	COMMENT '0个人 1公司',
  type             		int(11)                	DEFAULT NULL  		COMMENT '0朋友圈 1小图模式 2大图模式',
  title            		varchar(255)          	DEFAULT NULL,
  cover            		varchar(255)          	DEFAULT NULL   	COMMENT '封面图',
  PRIMARY KEY (id),UNIQUE KEY circle_company_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业朋友圈';

CREATE TABLE circle_img (
  id              	varchar(36)           	NOT NULL,
  img_url         	varchar(256)          	DEFAULT NULL       	COMMENT '图片全路径url',
  circle_id       	varchar(36)           	DEFAULT NULL       	COMMENT '朋友圈记录ID',
  create_time     	datetime               	DEFAULT NULL,
  modify_time     	datetime               	DEFAULT NULL,
  status          	int(11)                	DEFAULT '1',
  i_order         	int(11)                	DEFAULT '1'        	COMMENT '图片排序',
  PRIMARY KEY (id),UNIQUE KEY circle_img_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='朋友圈图片表';

CREATE TABLE circle_zan_log (
  id               	varchar(36)           	NOT NULL,
  circle_id        varchar(36)           	NOT NULL       		COMMENT '朋友圈记录ID',
  user_id          varchar(36)           	NOT NULL       		COMMENT '用户ID',
  create_time     	datetime              	DEFAULT NULL,
  modify_time     	datetime              	DEFAULT NULL,
  status          	int(11)              	DEFAULT '1'    	COMMENT '1有效0无效',
  flag            	int(1)               	DEFAULT '0'     	COMMENT '1公司 0 销售人员 2客户',
  PRIMARY KEY (id),UNIQUE KEY circle_zan_log_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点赞记录表';

CREATE TABLE click_action (
  id            	varchar(36)        	NOT NULL        	COMMENT '主键',
  code          	varchar(4)         	DEFAULT ''      	COMMENT '编码',
  description   	varchar(128)       	DEFAULT NULL    	COMMENT '点击事件描述',
  status        	int(11)            	DEFAULT '0'     	COMMENT '1有效0无效',
  create_time   	datetime           	DEFAULT NULL    	COMMENT '创建时间',
  modify_time   	datetime           	DEFAULT NULL    	COMMENT '修改时间',
  enterprise_id 	varchar(36)        	DEFAULT '',
  PRIMARY KEY (id),UNIQUE KEY click_action_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点击时间字典表';

CREATE TABLE click_action_history (
  id             	varchar(36)          	NOT NULL              	COMMENT '主键',
  action_code    	varchar(4)           	NOT NULL  DEFAULT ''  	COMMENT '事件编码',
  user_id        	varchar(36)          	DEFAULT NULL          	COMMENT '用户ID',
  create_time    	datetime              	DEFAULT NULL         	COMMENT '创建时间',
  description    	varchar(256)         	DEFAULT ''            	COMMENT '事件描述',
  employee_id    	varchar(36)          	NOT NULL              	COMMENT '销售人员ID',
  enterprise_id  	varchar(36)          	NOT NULL              	COMMENT '企业ID',
  action_target  	varchar(32)          	DEFAULT NULL,
  num            	int(11)              	DEFAULT '1',
  offer_id       	varchar(36)          	DEFAULT '0',
  department_id  	varchar(36)          	DEFAULT NULL,
  PRIMARY KEY (id),UNIQUE KEY click_action_log_id_uindex (id) USING BTREE,
  KEY click_action_log_enterprise_id_action_code_employee_id_index (enterprise_id,action_code,employee_id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE click_action_log (
  id              	varchar(36)         	NOT NULL             	COMMENT '主键',
  action_code     	varchar(4)          	NOT NULL DEFAULT ''  	COMMENT '事件编码',
  user_id         	varchar(36)         	DEFAULT NULL         	COMMENT '用户ID',
  create_time     	datetime             	DEFAULT NULL        	COMMENT '创建时间',
  description     	varchar(256)        	DEFAULT ''           	COMMENT '事件描述',
  employee_id     	varchar(36)         	NOT NULL             	COMMENT '销售人员ID',
  enterprise_id   	varchar(36)         	NOT NULL             	COMMENT '企业ID',
  action_target   	varchar(32)         	DEFAULT NULL,
  num             	int(11)             	DEFAULT '1',
  offer_id        	varchar(36)         	DEFAULT '0',
  department_id   	varchar(36)         	DEFAULT NULL,
  PRIMARY KEY (id),UNIQUE KEY click_action_log_id_uindex (id) USING BTREE,
  KEY click_action_log_enterprise_id_user_id_index (enterprise_id,user_id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点击事件日志表';

CREATE TABLE enter_api_rela(
  id              	int(11)               	NOT NULL             	AUTO_INCREMENT,
  enterprise_id   	varchar(36)          	DEFAULT NULL          	COMMENT '企业id',
  api_url         	varchar(100)         	DEFAULT NULL          	COMMENT 'api路径',
  app_id          	varchar(36)          	DEFAULT NULL,
  create_time     	datetime              	DEFAULT NULL,
  img_url        	varchar(100)          	DEFAULT NULL          	COMMENT '七牛图片路径',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业api';


CREATE TABLE exception_log (
  id                    	int(11)            	NOT NULL              AUTO_INCREMENT,
  param                 	varchar(800)       	DEFAULT NULL,
  exception_info        	text ,
  create_time           	datetime           	DEFAULT NULL,
  action_key            	varchar(512)       	DEFAULT NULL,
  ip                    	varchar(100)       	DEFAULT NULL,
  port                  	int(11)            	DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=14361 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE kj_point_log (
  id                   	varchar(36)           	NOT NULL,
  custom_id            varchar(36)           	DEFAULT NULL         COMMENT '客户id',
  content              	varchar(256)         	DEFAULT NULL         COMMENT '事项',
  operation            varchar(256)          	DEFAULT NULL         COMMENT '操作迹点（1 增加、2 减少）',
  operation_point     	float                 	DEFAULT NULL         COMMENT '操作迹点数（例：1000）',
  remain_point        	float(11,0)           	DEFAULT NULL         COMMENT '剩余迹点',
  create_time          	datetime              	DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP  COMMENT '操作时间',
  create_by            	varchar(36)          	DEFAULT NULL          COMMENT '操作人',
  target_id            	varchar(36)          	DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE kj_point_rate (
  id                  	varchar(225)        	NOT NULL DEFAULT ''     	COMMENT '主键ID',
  oem_rate            	float               	DEFAULT NULL            	COMMENT '贴牌商汇率(1迹点与元换算)\n',
  regional_rate       	float               	DEFAULT NULL            	COMMENT '地区总代理汇率(1迹点与元换算)''',
  agent_rate          	float               	DEFAULT NULL            	COMMENT '代理商汇率',
  create_time         	datetime            	DEFAULT NULL             	COMMENT '创建时间',
  modify_time         	datetime            	DEFAULT NULL             	COMMENT '修改时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='迹点汇率表';

CREATE TABLE logistics (
  id               	varchar(36)           	NOT NULL,
  order_id         	varchar(36)           	DEFAULT NULL       	COMMENT '订单id',
  sender           	varchar(10)           	DEFAULT NULL       	COMMENT '快递员姓名',
  s_phone          	varchar(11)           	DEFAULT NULL       	COMMENT '快递员电话',
  create_time      datetime              	DEFAULT NULL       	COMMENT '创建时间',
  status           	int(11)               	DEFAULT NULL        	COMMENT '状态 1派送中 0 派送完成',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物流表';


CREATE TABLE message_log (
  id               	int(11)                	NOT NULL                  	AUTO_INCREMENT,
  phone            	varchar(20)            DEFAULT NULL             	COMMENT '手机号',
  code             	varchar(10)            DEFAULT NULL             	COMMENT '验证码',
  create_time      datetime               	DEFAULT NULL             	COMMENT '发送时间',
  status           	varchar(50)            	DEFAULT NULL             	COMMENT '-1发送失败 0发送成功 待验证 1已验证',
  sms_message_sid 	varchar(50)            	DEFAULT NULL             	COMMENT '第三方平台id',
  message          	varchar(500)           	DEFAULT NULL             	COMMENT '短信内容',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE num (
  i                  int(11)              DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE quick_reply (
  id                	varchar(36)               	DEFAULT NULL,
  staff_id          	varchar(36)               	DEFAULT NULL           COMMENT '员工id',
  enterprise_id     	varchar(36)               	DEFAULT NULL           COMMENT '企业id',
  content           	varchar(64)               	DEFAULT NULL           COMMENT '回复内容',
  create_time       	datetime                   	DEFAULT NULL           COMMENT '创建时间',
  status            	int(11)                   	DEFAULT NULL           COMMENT '状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='快捷回复';

CREATE TABLE shop_template (
  id                	varchar(225)              	NOT NULL                 	COMMENT 'id',
  code              	varchar(255)              	DEFAULT NULL             	COMMENT '编号',
  enterprise_num    	int(11)                   	DEFAULT NULL             	COMMENT '企业数',
  small_cost        	int(10)                   	DEFAULT NULL             	COMMENT '小程序费用\n',
  card_cost         	int(10)                   	DEFAULT NULL             	COMMENT '名片费用',
  template_name     	varchar(225)              	DEFAULT NULL             	COMMENT '模版名字',
  create_time       	datetime                  	DEFAULT NULL              	COMMENT '创建时间',
  modify_time       	datetime                  	DEFAULT NULL              	COMMENT '修改时间',
  status            	int(11)                   	DEFAULT NULL              	COMMENT '状态',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模版管理';

CREATE TABLE totals (
  id                   	varchar(36)          	NOT NULL,
  zhuan_old            int(11)              	DEFAULT '0',
  zhuan_today          int(11)              	DEFAULT '0',
  zan_old              int(11)              	DEFAULT '0',
  zan_today            int(11)              	DEFAULT '0',
  view_old             int(11)              	DEFAULT '0',
  view_today           int(11)              	DEFAULT '0',
  staff_id             varchar(36)          	DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user_address (
  id                  	varchar(36)            	NOT NULL,
  user_id             	varchar(36)            	DEFAULT NULL          COMMENT '用户id',
  name                	varchar(36)            	DEFAULT NULL          COMMENT '联系人姓名',
  phone               	varchar(11)            	DEFAULT NULL          COMMENT '联系人电话',
  address             	varchar(100)           	DEFAULT NULL          COMMENT '详细地址',
  province_code       	varchar(16)            	DEFAULT NULL          COMMENT '省代码',
  city_code           	varchar(16)            	DEFAULT NULL          COMMENT '市代码',
  area_code           	varchar(16)            	DEFAULT NULL          COMMENT '区代码',
  is_default          	int(11)                	DEFAULT NULL          COMMENT '是否默认',
  create_time         	datetime               	DEFAULT NULL          COMMENT '创建时间',
  status              	int(11)                	DEFAULT NULL          COMMENT '状态',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收货地址表';

CREATE TABLE user_form_rela (
  id                 	char(36)               	NOT NULL,
  user_id            	char(36)               	NOT NULL,
  form_id           	char(36)               	NOT NULL,
  staff_id           	char(36)               	DEFAULT NULL,
  enterprise_id      	char(36)               	DEFAULT NULL,
  create_time        	datetime               	DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user_menu_ref (
  id                  	varchar(36)         	NOT NULL               	COMMENT '主键id',
  menu_id             	varchar(100)        	DEFAULT NULL          	COMMENT '菜单id',
  user_id             	varchar(100)        	DEFAULT NULL          	COMMENT '用户id',
  status              	int(11)             	DEFAULT '1'            COMMENT '1启用  0禁用',
  PRIMARY KEY (id),UNIQUE KEY company_id_uindex (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户_菜单关系表';


CREATE TABLE web_apply_log (
  id                 	int(11)               	NOT NULL            	AUTO_INCREMENT,
  name               	varchar(30)          	DEFAULT NULL        	COMMENT '姓名',
  mobile             	varchar(20)          	DEFAULT NULL        	COMMENT '电话',
  company_name       	varchar(30)          	DEFAULT NULL        	COMMENT '公司',
  remarks            	varchar(200)         	DEFAULT NULL        	COMMENT '备注',
  create_time        	datetime              	DEFAULT NULL,
  apply_type         	int(1)                	DEFAULT '1'          	COMMENT '1免费试用 2合作申请',
  platform_id        	int(11)              	DEFAULT '17',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE web_config (
  id                	varchar(36)             	NOT NULL,
  web_title         	varchar(16)             	DEFAULT NULL,
  web_icon          	varchar(255)            	DEFAULT NULL,
  login_type        	int(11)                 	DEFAULT NULL       COMMENT '对应admin表中的type 1超级后台 2贴牌商 3地区总代理 4代理商',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE recommend_card (
  id                  	varchar(36)             	NOT NULL            	COMMENT '主键',
  staff_id           	varchar(36)             	DEFAULT NULL       	COMMENT 'staff_id',
  status             	int(11)                  	DEFAULT 1          		COMMENT '状态：0禁用，1启用',
  view_number       	int(11)                  	DEFAULT null        	COMMENT '浏览次数',
  view_order        	int(11)                  	DEFAULT null        	COMMENT '展示顺序',
  create_time        	datetime                 	DEFAULT null       	COMMENT '创建时间',
  notify_time        	datetime                 	DEFAULT null       	COMMENT '修改时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/**
 * 企业文件表
 */
CREATE TABLE enterprise_file(
	id                		varchar(36)         	NOT NULL					COMMENT '物理主键--统一外键名file_id',
	dir_id					varchar(36)				not null 					COMMENT '外键--enterprise_file_dir的目录id',
	file_url				varchar(500)			null 						COMMENT '文件链接',
	file_name				varchar(500)			null 						COMMENT '文件名',
	file_size				varchar(36)				null						COMMENT '文件大小',
	create_time			datetime				null 						COMMENT '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业文件表';

/**
 * 企业文件目录表
 */
CREATE TABLE enterprise_file_dir(
	id                		varchar(36)         	NOT NULL					COMMENT '物理主键--统一外键名dir_id',
	enterprise_id			varchar(36)				not null 					COMMENT '外键--enterprise的目录id',
	dir_name				varchar(500)			null 						COMMENT '目录名',
	create_time			datetime				null 						COMMENT '创建时间',
	dir_password			varchar(6)				null 						COMMENT '目录密码，只有知道密码的人才能查看目录下的文件',
	show_order				int						null						COMMENT '文件目录的显示顺序'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业文件目录表';