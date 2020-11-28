delete from shop_template;
INSERT INTO `shop_template` VALUES ('1', '1001', '37', '300', '30', '基础商城模版', '2018-06-14 05:51:52', '2018-06-14 05:51:57', '1');

delete from index_config;
INSERT INTO index_config (`id`, `prix`, `no`, `dept_id`, `enterprise_id`, `create_time`, `modified_time`) VALUES ('9ecd7dfc-bad5-403c-87c1-30872bb9c722', 	'SO', 	'0', NULL, 'SO', '2018-09-05 17:14:09', '2018-09-05 17:14:09');
INSERT INTO index_config (`id`, `prix`, `no`, `dept_id`, `enterprise_id`, `create_time`, `modified_time`) VALUES ('bcaa5bf1-a4fb-4f1d-a7bd-fca8058775ff', 	'OAR', 	'0', NULL, 'OAR', '2018-09-05 17:38:23', '2018-09-05 17:38:23');
INSERT INTO index_config (`id`, `prix`, `no`, `dept_id`, `enterprise_id`, `create_time`, `modified_time`) VALUES ('83003fb4-3349-4795-92d8-701a5a73fe92', 	'AA', 	'0', NULL, 'AA', '2018-09-05 17:40:15', '2018-09-05 17:40:15');
INSERT INTO index_config (`id`, `prix`, `no`, `dept_id`, `enterprise_id`, `create_time`, `modified_time`) VALUES ('22221c38-310e-4a31-84ae-a8ca6945478b', 	'AE', 	'0', NULL, 'AE', '2018-09-05 17:42:10', '2018-09-05 17:42:10');
INSERT INTO index_config (`id`, `prix`, `no`, `dept_id`, `enterprise_id`, `create_time`, `modified_time`) VALUES ('ab9f3f7d-8602-46d1-98ed-0bfdee929df3', 	'OAG', 	'0', NULL, 'OAG', '2018-09-19 17:15:23', '2018-09-19 17:15:23');
-- DEP必須 = SysConfig.pf_default_deptid的值，否微信会报错
INSERT INTO index_config (`id`, `prix`, `no`, `dept_id`, `enterprise_id`, `create_time`, `modified_time`) VALUES ('ab9f3f7d-8602-46d1-98ed-111111111111', 	'DEP', 	'2', NULL, 'DEP', '2018-09-19 17:15:23', '2018-09-19 17:15:23');

delete from menu_resource;
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('1', '首页', 			'SMS/INDEX', 					null, 	'0', 	null, null, '1', '0', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('2',  '客户管理', 		'SMS/CUSTOMER', 				null, 	'0', 	null, null, '1', '0', '1', '0', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('3',  '迹点管理', 		'SMS/TRACE_POINT', 			null, 	'0', 	null, null, '1', '0', '1', '1', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('4',  '模版管理', 		'SMS/TEMPLATE', 				null, 	'0', 	null, null, '1', '0', '1', '1', '4', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('5',  '员工管理', 		'SMS/STAFF', 					null, 	'0', 	null, null, '1', '0', '1', '1', '5', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('6',  '系统设置', 		'SMS/SETTING', 				null, 	'0', 	null, null, '1', '0', '1', '0', '6', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('7',  '贴牌商列表', 	'SMS/OEM', 						null, 	'0', 	null, null, '1', '2', '2', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('8',  '地区总代理列表','SMS/REGIONAL_AGENT', 		null, 	'0', 	null, null, '1', '2', '2', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('9',  '代理商列表', 	'SMS/AGENT', 					null, 	'0', 	null, null, '1', '2', '2', '1', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('10', '企业列表', 		'SMS/ENTERPRISE', 				null,	'0', 	null, null, '1', '2', '2', '1', '4', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('11', '申请列表', 		'SMS/ENTERPRISE_APPLY', 		null, 	'0', 	null, null, '1', '2', '2', '1', '5', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('13', '价格设置', 		'SMS/PRICE_SETTING', 			null, 	'0', 	null, null, '1', '6', '2', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('14', '首页', 			'OMS/INDEX', 					null, 	null, 	null, null, '2', '0', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('15', '客户管理', 		'OMS/CUSTOMER', 				null, 	null, 	null, null, '2', '0', '1', '0', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('16', '地区总代理列表','OMS/REGIONAL_AGENT', 		null, 	null, 	null, null, '2', '15', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('17', '代理商列表', 	'OMS/AGENT', 					null, 	null, 	null, null, '2', '15', '1', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('18', '企业列表', 		'OMS/ENTERPRISE', 				null, 	null, 	null, null, '2', '15', '1', '1', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('19', '迹点管理', 		'OMS/TRACE_POINT',	 			null, 	null, 	null, null, '2', '0', '1', '0', '4', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('20', '员工管理', 		'OMS/STAFF', 					null, 	null, 	null, null, '2', '0', '1', '1', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('21', '首页', 			'RAMS/INDEX', 					null, 	null, 	null, null, '3', '0', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('22', '客户管理',	 	'RAMS/CUSTOMER', 				null, 	null, 	null, null, '3', '0', '1', '0', '4', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('23', '代理商列表', 	'RAMS/AGENT', 					null, 	null, 	null, null, '3', '22', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('24', '企业列表', 		'RAMS/ENTERPRISE', 			null, 	null, 	null, null, '3', '22', '1', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('25', '迹点管理', 		'RAMS/TRACE_POINT', 			null, 	null, 	null, null, '3', '0', '1', '0', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('26', '员工管理', 		'RAMS/STAFF', 					null, 	null, 	null, null, '3', '0', '1', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('27', '首页', 			'PMS/INDEX', 					null, 	null, 	null, null, '4', '0', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('28', '客户管理', 		'PMS/CUSTOMER', 				null, 	null, 	null, null, '4', '0', '1', '0', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('29', '企业列表', 		'PMS/ENTERPRISE', 				null, 	null, 	null, null, '4', '28', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('30', '迹点管理', 		'PMS/TRACE_POINT', 			null, 	null, 	null, null, '4', '0', '1', '0', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('31', '员工管理', 		'PMS/STAFF', 					null, 	null, 	null, null, '4', '0', '1', '1', '4', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('32', '我的迹点报表', 	'PMS/TRACE_POINT_MINE', 		null, 	null, 	null, null, '4', '30', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('33', '下级报表', 		'PMS/TRACE_POINT',	 			null, 	null, 	null, null, '4', '30', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('34', '我的迹点报表', 	'RAMS/TRACE_POINT_MINE', 		null, 	null, 	null, null, '3', '25', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('35', '下级报表', 		'RAMS/TRACE_POINT', 			null, 	null, 	null, null, '3', '25', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('36', '下级报表', 		'OMS/TRACE_POINT', 			null, 	null, 	null, null, '2', '19', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('37', '我的迹点报表', 	'OMS/TRACE_POINT_MINE', 		null, 	null, 	null, null, '2', '19', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('38', '首页', 			'EMS/INDEX', 					null, 	null, 	null, null, '5', '0', '1', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('39', '小程序管理', 	'EMS/MINIPROGRAM', 			null, 	null, 	null, null, '5', '0', '1', '0', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('40', '人员管理', 		'EMS/USER', 					null, 	null, 	null, null, '5', '0', '1', '0', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('43', '小程序授权 ', 	'EMS/MINIPROGRAM_PERMISSON', 	null, 	null, 	null, null, '5', '0', '1', '0', '6', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('44', '官网管理', 		'EMS/WEBSTIE', 				null, 	null, 	null, null, '5', '39', '2', '1', '1', '2');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('46', '朋友圈管理', 	'EMS/CIRCLE', 					null, 	null, 	null, null, '5', '39', '2', '1', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('47', '员工管理', 		'EMS/ORG', 						null, 	null, 	null, null, '5', '40', '2', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('48', '雷达权限', 		'EMS/RADAR', 					null, 	null, 	null, null, '5', '40', '2', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('49', '工作交接', 		'EMS/HANDOVER', 				null, 	null, 	null, null, '5', '40', '2', '1', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('53', '微信授权 ', 	'EMS/WEIXIN_PERMISSON', 		null, 	null, 	null, null, '5', '43', '2', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('54', '推广信息', 		'SMS/WEB_APPLY', 				null, 	'0', 	null, null, '1', '0', '1', '1', '7', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('55', '商城管理', 		'EMS/STORE', 					null, 	null, 	null, null, '5', '0', '1', '0', '7', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('56', '商品管理', 		'EMS/STORE_OFFER', 			null, 	null, 	null, null, '5', '55', '2', '1', '1', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('57', '订单管理', 		'EMS/STORE_ORDER', 			null, 	null, 	null, null, '5', '55', '2', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('58', '折扣权限', 		'EMS/STORE_DISCOUNT', 			null, 	null, 	null, '', 	 '5', '55', '2', '1', '3', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('59', '员工订单统计', 	'EMS/STAFF_ORDER', 			null, 	null, 	null, null, '5', '55', '2', '1', '4', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('60', '广告管理', 		'EMS/ADV', 						null, 	'0', 	null, null, '5', '0', '1', '1', '8', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('61', '部门管理', 		'EMS/DEPT', 					null, 	null, 	null, null, '5', '40', '2', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('62', '推荐名片',  	'SMS/RCDCARD',                	null, 	null, 	null, null, '1', '6', '2', '1', '2', '1');
INSERT INTO menu_resource(id,name,url,create_time,create_by,update_time,update_by,role,parent_id,rank,is_leaf,seq,target) VALUES ('63', '文件管理', 		'EMS/FILE', 					null, 	null, 	null, null, '5', '0', '1', '1', '9', '1');

