DROP TABLE IF EXISTS shop;                                    /* 商城类型表*/
DROP TABLE IF EXISTS shop_product;                      		  /* 产品 */
DROP TABLE IF EXISTS shop_product_img;                  		  /* 产品图片 */
DROP TABLE IF EXISTS shop_adv;                                     /* 商城: 广告banner表*/
DROP TABLE IF EXISTS shop_category;                                /* 商城: 商品类别表 */
DROP TABLE IF EXISTS shop_offer_img;                               /* 商城: 商品图片表，记录商品图片的相关信息*/
DROP TABLE IF EXISTS shop_offer_norms;                             /* 商城: 商品规格*/
DROP TABLE IF EXISTS shop_offer_recommend;                         /* 商城: 推荐商品*/
DROP TABLE IF EXISTS shop_offer_spec;                         /* 商城: 商品表*/
DROP TABLE IF EXISTS shop_order;                              /* 商城: 订单表*/
DROP TABLE IF EXISTS shop_order_detail;                       /* 商城: 订单详情*/
DROP TABLE IF EXISTS shop_cart;                           /* 商城: 购物车*/

/**
 * 商城类型表
 */
CREATE TABLE shop (
	id                	varchar(36)          NOT NULL					COMMENT '物理主键--shop_id',
  	enterprise_id    	varchar(36)          DEFAULT NULL        		COMMENT '企业ID=enterprise.id',
	name 				varchar(200)		  DEFAULT NULL				COMMENT '商城名称',
	type 				int				      DEFAULT NULL				COMMENT '商城类型:1->产品, 2->商城',
	paytype				int 				  DEFAULT 0					COMMENT '支付方式:0->未开通支付, 1->开通了微信支付',
	create_time       	datetime              DEFAULT NULL        		COMMENT '创建时间',
	PRIMARY KEY (id), UNIQUE KEY PK_shop (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城类型';

/**
  产品表
 */
CREATE TABLE shop_product (
  id               varchar(36)             NOT NULL             COMMENT '主键(product_id)',
  name       			 varchar(50)             DEFAULT NULL         COMMENT '产品名称',
  salePrice      	 decimal(10,2)           DEFAULT NULL         COMMENT '产品价格',
  code             varchar(56)             DEFAULT NULL         COMMENT '产品编码',
  descript         varchar(500)             DEFAULT NULL         COMMENT '产品描述',
  audio_id         varchar(255)            DEFAULT NULL       	COMMENT '产品语音介绍，url（目前版本暂不需要） = attachment_audio.id',
  vidio_id         varchar(255)            DEFAULT NULL       	COMMENT '产品视频介绍, url（目前版本暂不需要） = attachment_audio.id',
  create_staff_id  varchar(36)             DEFAULT NULL         COMMENT '创建员工ID',
  enterprise_id    varchar(36)             DEFAULT NULL         COMMENT '企业ID',
  create_time      datetime                DEFAULT NULL         COMMENT '创建时间',
  modify_time      datetime                DEFAULT NULL         COMMENT '更新时间',
  PRIMARY KEY (id), UNIQUE KEY PK_shop_product(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品';

/**
 产品图片表
 */
CREATE TABLE shop_product_img (
  id                 varchar(36)           NOT NULL      				COMMENT '主键',
  product_id    	   varchar(36)           NOT NULL      				COMMENT '产品ID=shop_product.id',
  img_url            varchar(256)          DEFAULT NULL  				COMMENT '图片链接',
  type               int(11)               NOT NULL     				COMMENT '1封面图2详情图',
  descript           varchar(500)         DEFAULT NULL             COMMENT '描述',
  status             int(11)               DEFAULT '1'  				COMMENT '1有效0无效',
  `order`            int(255)              DEFAULT NULL 				COMMENT '排序',
  create_time        datetime              DEFAULT NULL  				COMMENT '创建时间',
  modify_time        datetime              DEFAULT NULL  				COMMENT '更新时间',
  PRIMARY KEY (id), UNIQUE KEY PK_shop_product_img(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品图片';

/**
 * 商城: 商品类别表
 */
CREATE TABLE shop_category (
  	id                 			varchar(36)           	NOT NULL      				COMMENT '主键(cat_id)',
  	enterprise_id    			  varchar(36)           	DEFAULT NULL        		COMMENT '企业ID=enterprise.id',
  	name 						        varchar(200)			      DEFAULT	 NULL				  COMMENT '类别名称',
  	`order`            			int(255)              	DEFAULT NULL 				  COMMENT '排序',
  	create_staff_id			    varchar(36)				      DEFAULT NULL				  COMMENT '创建人=staff.id',
  	create_time        		  datetime              	DEFAULT NULL  				COMMENT '创建时间',
  	modify_time        		  datetime              	DEFAULT NULL  				COMMENT '更新时间',
  	PRIMARY KEY (id), UNIQUE KEY PK_shop_category(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城: 商品类别表';

/**
 * 商城广告表
 */
CREATE TABLE shop_adv (
  id                 varchar(36)              NOT NULL       COMMENT '主键 FK: adv_id',
  create_staff_id    varchar(36)              DEFAULT NULL    COMMENT '创建员工ID',
  enterprise_id      varchar(36)              DEFAULT NULL    COMMENT '企业ID',
  title              varchar(128)             DEFAULT NULL    COMMENT '标题',
  location           int(11)                  DEFAULT '1'     COMMENT '默认产品首页',
  href               varchar(128)             DEFAULT NULL    COMMENT '链接地址',
  img_url            varchar(255)             DEFAULT NULL,
  a_order            int(11)                  DEFAULT '1'     COMMENT '排序',
  create_time        datetime                 DEFAULT NULL    COMMENT '创建时间',
  status             int(11)                  DEFAULT '0'     COMMENT '1正常0下线',
  modify_time        datetime                 DEFAULT NULL,
  finish_time        datetime                 DEFAULT NULL,
  begin_time         datetime                 DEFAULT NULL,
   PRIMARY KEY (id),UNIQUE KEY PK_shop_adv(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='广告banner表';

/**
 * 商品图片表
 */
CREATE TABLE shop_offer_img (
  id                 varchar(36)           NOT NULL       COMMENT '主键 FK: offerimg_id',
  offer_spec_id      varchar(36)           NOT NULL       COMMENT '商品规格ID',
  img_url            varchar(256)          DEFAULT NULL   COMMENT '图片链接',
  descript           varchar(500)          DEFAULT NULL       COMMENT '描述',
  create_time        datetime              DEFAULT NULL   COMMENT '创建时间',
  modify_time        datetime              DEFAULT NULL   COMMENT '更新时间',
  status             int(11)               DEFAULT '1'    COMMENT '1有效0无效',
  type               int(11)               NOT NULL       COMMENT '1封面图2详情图',
  `order`            int(255)              DEFAULT NULL   COMMENT '排序',
  PRIMARY KEY (id),UNIQUE KEY PK_shop_offer_img(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品图片';

/**
 * 商品规格表
 */
CREATE TABLE shop_offer_norms(
  id                varchar(36)            NOT NULL			COMMENT '主键, FK: offernorms_ID',
  name              varchar(36)            DEFAULT NULL       COMMENT '规格名字',
  offer_id          varchar(36)            DEFAULT NULL       COMMENT '商品id',
  offer_price       decimal(10,2)          DEFAULT NULL       COMMENT '商品价格',
  offer_leave       decimal(10,2)          DEFAULT NULL       COMMENT '商品库存',
  is_default        int(11)                DEFAULT NULL       COMMENT '是否默认规格 1默认 0否',
  norms_type        int(11)                DEFAULT NULL       COMMENT '规格类型 1多规格 0单规格',
  create_time       datetime               DEFAULT NULL       COMMENT '创建时间',
  status            int(11)                DEFAULT NULL       COMMENT '状态',
  norms_pic         varchar(100)          DEFAULT NULL        COMMENT '规格图片',
   PRIMARY KEY (id),UNIQUE KEY PK_shop_offer_norms(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品规格';

/**
 * 推荐商品表
 */
CREATE TABLE shop_offer_recommend (
  id               varchar(36)             NOT NULL			COMMENT '主键, FK_OFFER_RECOMMEND_ID',
  offer_id         varchar(36)             DEFAULT NULL        COMMENT '商品id',
  staff_id         varchar(36)             DEFAULT NULL        COMMENT '员工id',
  enterprise_id    varchar(36)             DEFAULT NULL        COMMENT '企业id',
  sort             int(11)                  DEFAULT NULL       COMMENT '排序',
  status           int(11)                  DEFAULT NULL       COMMENT '状态',
  create_time      datetime                 DEFAULT NULL       COMMENT '创建时间',
  voice_intro      varchar(36)             DEFAULT NULL        COMMENT '语音介绍',
	PRIMARY KEY (id),UNIQUE KEY PK_shop_offer_recommend(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推荐商品';

/**
 * 商品表
 */
CREATE TABLE shop_offer_spec (
  id               varchar(36)             NOT NULL             COMMENT '主键, FK_OFFERSPEC_ID',
  offer_name       varchar(50)             DEFAULT NULL         COMMENT '商品名称',
  offer_price      decimal(10,2)           DEFAULT NULL         COMMENT '商品价格',
  create_time      datetime                DEFAULT NULL         COMMENT '创建时间',
  update_time      datetime                DEFAULT NULL         COMMENT '更新时间',
  code             varchar(56)             DEFAULT NULL         COMMENT '商品编码',
  create_staff_id  varchar(36)             DEFAULT NULL         COMMENT '创建员工ID',
  status           int(11)                 DEFAULT '0'          COMMENT '0未上架 1上架 2售罄 3旧版本',
  enterprise_id    varchar(36)             DEFAULT NULL,
  discount         decimal(10,2)           DEFAULT NULL,
  version          varchar(36)             DEFAULT NULL         COMMENT '版本号',
  offer_sale       int(11)                 DEFAULT NULL         COMMENT '销售量',
  is_pay_online    int(11)                 DEFAULT NULL         COMMENT '是否支持线上支付 1支持 0不支持',
  is_delete        int(11)                 DEFAULT NULL         COMMENT '是否删除 1删除 0未删除',
  onsale_time      datetime                DEFAULT NULL         COMMENT '上架时间',
  extract_type     int(11)                 DEFAULT '1'          COMMENT '提成方式 1固定提成 0比例提成',
  extract_value    decimal(10,2)           DEFAULT '0.00'      COMMENT '提成额度',
  extract_per      decimal(10,2)           DEFAULT '0.00'      COMMENT '提成比例',
  type             int(11)                 DEFAULT '0'         COMMENT ' 规格类型 0单规格（默认） 1多规格',
  voice_intro      varchar(255)            DEFAULT NULL        COMMENT '商品语音介绍',
  express_fee      decimal(10,2)           DEFAULT '0.00'      COMMENT '快递费',
  category_id      varchar(36)             DEFAULT NULL        COMMENT '商品分类ID',
  descript         varchar(500)            DEFAULT NULL        COMMENT '商品描述',
  PRIMARY KEY (id), UNIQUE KEY PK_shop_offer_spec(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品';

/**
 * 订单表
 */
CREATE TABLE shop_order (
  id                   varchar(36)           NOT NULL             COMMENT '主键, PK-ORDER_ID',
  order_nbr            varchar(64)           DEFAULT NULL         COMMENT '订单号',
  offer_img            varchar(256)          DEFAULT NULL         COMMENT '商品图片',
  link_phone           varchar(32)           DEFAULT NULL         COMMENT '联系电话',
  link_man             varchar(32)           DEFAULT NULL         COMMENT '联系人姓名',
  offer_id             varchar(36)           DEFAULT NULL         COMMENT '商品ID',
  offer_num            int(11)               DEFAULT '1'           COMMENT '商品数量',
  create_time          datetime              DEFAULT NULL          COMMENT '下单时间',
  status               int(11)               DEFAULT '0'           COMMENT '0未支付 1已支付 2用户取消 3超时取消 4未发货 5已发货 6完成',
  total_price          decimal(10,2)         DEFAULT NULL          COMMENT '订单金额',
  user_id              varchar(36)           DEFAULT NULL          COMMENT '下单客户ID',
  staff_id             varchar(36)           DEFAULT NULL          COMMENT '员工ID',
  pay_time             datetime               DEFAULT NULL          COMMENT '支付时间',
  enterprise_id        varchar(36)           DEFAULT NULL,
  department_id        varchar(36)           DEFAULT NULL,
  order_price          decimal(10,2)         DEFAULT NULL          COMMENT '订单价格',
  express_fee          decimal(10,2)         DEFAULT NULL          COMMENT '快递费',
  total_price_discount decimal(10,2)         DEFAULT NULL          COMMENT '实付金额(不包含快递费 未付款的时候为0)',
  is_express           int(11)                DEFAULT '0'           COMMENT '是否需要配送 1需要 0不需要',
  address              varchar(64)           DEFAULT NULL          COMMENT '地址',
  finish_time          datetime               DEFAULT NULL          COMMENT '订单完成时间',
  extract_value        decimal(10,2)         DEFAULT NULL          COMMENT '佣金',
  express_name         varchar(32)           DEFAULT NULL          COMMENT '快递公司',
  PRIMARY KEY (id),UNIQUE KEY PK_shop_order (id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

/**
 * 订单详情表
 */
CREATE TABLE shop_order_detail (
  id                    varchar(36)            NOT NULL			COMMENT 'PK-ORDERDETAIL_ID',
  order_id              varchar(36)            DEFAULT NULL      COMMENT '订单id',
  offer_id              varchar(36)            DEFAULT NULL      COMMENT '商品id',
  norms_id              varchar(36)            DEFAULT NULL      COMMENT '规格id',
  offer_num             int(11)                DEFAULT NULL      COMMENT '购买数量',
  offer_price           decimal(10,2)          DEFAULT NULL      COMMENT '商品单价',
  offer_price_discount  decimal(10,2)          DEFAULT NULL      COMMENT '折扣后的价格',
  total_price           decimal(10,2)          DEFAULT NULL      COMMENT '总价',
  total_price_discount  decimal(10,2)          DEFAULT NULL      COMMENT '折扣后的总价',
  charges               decimal(10,2)          DEFAULT NULL      COMMENT '佣金',
  staff_id              varchar(36)            DEFAULT NULL      COMMENT '员工id',
  create_time           datetime               DEFAULT NULL      COMMENT '创建时间',
  status                int(11)                DEFAULT NULL      COMMENT '状态',
  PRIMARY KEY (id),UNIQUE KEY PK_shop_order_detail(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单详情表';

/**
 * 购物车
 */
CREATE TABLE shop_cart (
  id                varchar(36)               NOT NULL						COMMENT 'FK: cart_id',
  offer_id          varchar(36)               DEFAULT NULL             COMMENT '商品id',
  norms_id          varchar(36)               DEFAULT NULL             COMMENT '规格id',
  create_time       datetime                  DEFAULT NULL            COMMENT '创建时间',
  status            int(11)                   DEFAULT NULL             COMMENT '状态',
  num               int(11)                   DEFAULT NULL             COMMENT '数量',
  user_id           varchar(36)               DEFAULT NULL             COMMENT '用户id',
  staff_id          varchar(36)               DEFAULT NULL             COMMENT '销售人员id',
  PRIMARY KEY(id),UNIQUE KEY PK_shop_cart(id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车';


