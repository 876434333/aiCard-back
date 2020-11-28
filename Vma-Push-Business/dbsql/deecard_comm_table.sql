DROP TABLE IF EXISTS calendar;                                /* 通用表：日历表 */
DROP TABLE IF EXISTS province_info;                           /* 通用表：省表 */
DROP TABLE IF EXISTS city_info;                               /* 通用表：市表*/
DROP TABLE IF EXISTS area_info;                               /* 通用表：区表*/

DROP TABLE IF EXISTS attachment_vedio;                         /* 通用表：视频附件表*/
DROP TABLE IF EXISTS attachment_audio;                         /* 通用表：语音附件表*/
DROP TABLE IF EXISTS attachment_image;                         /* 通用表：图片附件表*/

/**
 * 日历表
 */
CREATE TABLE calendar (
	datelist               date                    DEFAULT NULL				COMMENT  '日期: YYYY-MM-DD 2010-01-01 至 2037-12-31'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=FIXED COMMENT='日历表';

/**
 * 省编码表
 */
CREATE TABLE province_info (
  	id                 		int(11)         		NOT NULL AUTO_INCREMENT		COMMENT '',
  	province_code      	varchar(16)            	DEFAULT NULL				COMMENT '',
  	province_name      	varchar(200)           DEFAULT NULL				COMMENT '',
  	create_time        	datetime              	DEFAULT NULL				COMMENT '',
  	PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='省编码表';

/**
 * 市编码表
 */
CREATE TABLE city_info (
  	id              		int(11)         		NOT NULL AUTO_INCREMENT,
  	city_code       		varchar(16)           	DEFAULT NULL				COMMENT '',
  	city_name       		varchar(200)          	DEFAULT NULL				COMMENT '',
  	province_id     		int(11)               	DEFAULT NULL				COMMENT '',
  	create_time     		datetime              	DEFAULT NULL				COMMENT '',
  	jian_pin        		varchar(30)          	DEFAULT NULL				COMMENT '',
  	quan_pin        		varchar(30)          	DEFAULT NULL				COMMENT '',
  	is_hot          		int(1)               	DEFAULT NULL				COMMENT '',
  	status          		int(1)               	DEFAULT '1'				COMMENT '',
  	PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='市编码表';

/**
 * 区县编码表
 */
CREATE TABLE area_info (
  	id               		int(11)               	NOT NULL AUTO_INCREMENT		COMMENT '',
  	area_code        		varchar(16)           	NOT NULL					COMMENT '',
  	area_name        		varchar(200)          	NOT NULL					COMMENT '',
  	city_id          		int(11)                	NOT NULL					COMMENT '',
  	province_id      		int(11)                	DEFAULT NULL				COMMENT '',
  	create_time      		datetime               	DEFAULT NULL				COMMENT '',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3197 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='区县编码表';

/**
 * 通用表： 视频记录表
 */
CREATE TABLE attachment_vedio(
	id             			varchar(36)            	NOT NULL              		COMMENT '物理主键(外键名: vedio_id)',
	name 					varchar(256)           	DEFAULT NULL				COMMENT '源文件名(上传时,用户选择的文件名, 包括后缀, 不包括路径)',
	url						varchar(256)           	DEFAULT NULL				COMMENT '资源URL(七牛云)',
	size 					int 					DEFAULT NULL				COMMENT '附件大小, 单位kb',
	duration				int 					DEFAULT NULL				COMMENT '播放时长, 单位秒',
	cover_imageurl			varchar(256)           	DEFAULT NULL				COMMENT '封面图片(七牛云url)',
	creator_id				varchar(36)				DEFAULT NULL				COMMENT '上传人: from员工=staff.id, from小程序用户=user_info.id',
	createtime				datetime                DEFAULT NULL				COMMENT '上传时间',
	modify_time    		datetime                DEFAULT NULL				COMMENT '修改时间',
	PRIMARY KEY(id),	UNIQUE KEY PK_attachment_vedio(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频表';

/**
 * 通用表： 语音记录表
 */
CREATE TABLE attachment_audio(
	id             			varchar(36)            	NOT NULL              		COMMENT '物理主键(外键名: audio_id)',
	name 					varchar(256)           	DEFAULT NULL				COMMENT '源文件名(上传时,用户选择的文件名, 包括后缀, 不包括路径)',
	url						varchar(256)           	DEFAULT NULL				COMMENT '资源URL(七牛云)',
	size 					int 					DEFAULT NULL				COMMENT '附件大小, 单位kb',
	duration				int 					DEFAULT NULL				COMMENT '播放时长, 单位秒',
	creator_id				varchar(36)				DEFAULT NULL				COMMENT '上传人: from员工=staff.id, from小程序用户=user_info.id',
	createtime				datetime                DEFAULT NULL				COMMENT '上传时间',
	modify_time    		datetime                DEFAULT NULL				COMMENT '修改时间',
	PRIMARY KEY(id),	UNIQUE KEY PK_attachment_audio(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='语音表';

/**
 * 通用表： 图片记录表
 */
CREATE TABLE attachment_image(
	id             			varchar(36)            	NOT NULL              		COMMENT '物理主键(外键名: image_id)',
	name 					varchar(256)           	DEFAULT NULL				COMMENT '源文件名(上传时,用户选择的文件名, 包括后缀, 不包括路径)',
	url						varchar(256)           	DEFAULT NULL				COMMENT '资源URL(七牛云)',
	size 					int 					DEFAULT NULL				COMMENT '附件大小, 单位kb',
	creator_id				varchar(36)				DEFAULT NULL				COMMENT '上传人: from员工=staff.id, from小程序用户=user_info.id',
	createtime				datetime                DEFAULT NULL				COMMENT '上传时间',
	modify_time    		datetime                DEFAULT NULL				COMMENT '修改时间',
	PRIMARY KEY (id),	UNIQUE KEY PK_attachment_image(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图片表';



