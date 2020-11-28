DROP TABLE IF EXISTS recommend_card;                          /*推荐名片表*/

/* 推荐名片表*/
CREATE TABLE recommend_card (
  id                  varchar(36)             NOT NULL            COMMENT "主键",
  staff_id           varchar(36)             DEFAULT NULL        COMMENT 'staff_id',
  status             int(11)                  DEFAULT 1          COMMENT '状态：0禁用，1启用',
  view_number       int(11)                  DEFAULT null        COMMENT '浏览次数',
  view_order        int(11)            DEFAULT null        COMMENT '展示顺序',
  create_time        datetime                 DEFAULT null       COMMENT '创建时间',
  notify_time        datetime                 DEFAULT null       COMMENT '修改时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('62', '推荐名片', 	'SMS/RCDCARD', null, null, null, null, '1', '6', '2', '1', '2', '1');


