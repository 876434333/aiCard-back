DROP TABLE IF EXISTS website;                        			/* 官网 */
DROP TABLE IF EXISTS website_template;                        /* 官网组件配置 */
/**
 * 官网
 */
CREATE TABLE website (
  	id               		varchar(36)				NOT NULL         			COMMENT '物理主键-website_id',
  	name 					varchar(300)			DEFAULT NULL         		COMMENT '官网名称(默认是组织名称)',
  	enterprise_id   		varchar(225)         	DEFAULT NULL          		COMMENT '企业ID',
	create_time      		datetime                DEFAULT NULL         		COMMENT '创建时间',
  	modify_time      		datetime                DEFAULT NULL         		COMMENT '修改时间',
  	PRIMARY KEY (id),UNIQUE KEY PK_website(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='官网';

/**
 * 官网组件配置
 */
CREATE TABLE website_template (
  	id               		varchar(36)				NOT NULL         			COMMENT '',
  	text_content     		varchar(10240)         DEFAULT NULL         		COMMENT '',
  	create_time      		datetime                DEFAULT NULL         		COMMENT '',
  	modify_time      		datetime                DEFAULT NULL         		COMMENT '',
  	enterprise_id    		varchar(36)            	DEFAULT NULL         		COMMENT '',
  	type             		int(11)					DEFAULT NULL         		COMMENT '1轮播图2地址3联系我们4企业简介',
  	simple_desc      		varchar(64)            	DEFAULT NULL         		COMMENT '',
  	order_num        		int(4)                 	DEFAULT NULL         		COMMENT '',
  	config           		varchar(255)           	DEFAULT NULL         		COMMENT '',
  	website_id				varchar(44)				DEFAULT NULL				COMMENT 'V1.0.0->所属官网ID, 为了兼容老版本websit_id容许为空(通过enterprise_id找官网), 新版本创建一个website记录，关联进来',
  	PRIMARY KEY (id),UNIQUE KEY PK_website_template(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='官网组件配置';

