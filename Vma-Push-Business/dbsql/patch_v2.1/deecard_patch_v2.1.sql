
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target)
VALUES ('63', '文件管理', 	'EMS/FILE', null, null, null, null, '5', '0', '1', '1', '9', '1');


DROP TABLE IF EXISTS enterprise_file;							/* 企业上传的文件表*/
DROP TABLE IF EXISTS enterprise_file_dir;						/* 企业建的文件目录表*/
CREATE TABLE enterprise_file(
	id                		varchar(36)         	not null 					COMMENT '物理主键--统一外键名file_id',
	dir_id					varchar(36)				not null 					COMMENT '外键--enterprise_file_dir的目录id',
	file_url				varchar(500)			null 						COMMENT '文件链接',
	file_name				varchar(500)			null 						COMMENT '文件名',
	file_size				varchar(36)				null						COMMENT '文件大小',
	create_time			datetime				null 						COMMENT '创建时间',
	PRIMARY KEY (id),UNIQUE KEY enterprise_file_id_uindex (id) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业文件表';

CREATE TABLE enterprise_file_dir(
	id                		varchar(36)         	not null					COMMENT '物理主键--统一外键名dir_id',
	enterprise_id			varchar(36)				not null 					COMMENT '外键--enterprise的目录id',
	dir_name				varchar(500)			null 						COMMENT '目录名',
	create_time			datetime				null 						COMMENT '创建时间',
	dir_password			varchar(6)				null 						COMMENT '目录密码，只有知道密码的人才能查看目录下的文件',
	PRIMARY KEY (id),UNIQUE KEY enterprise_file_dir_id_uindex (id) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业文件目录表';
