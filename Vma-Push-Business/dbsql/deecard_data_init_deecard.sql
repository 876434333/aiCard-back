
delete from sys_config;
INSERT INTO sys_config(
		`id`,
		`im_private`,
		`im_publick`,
		`im_sdk_app`,
		`account_type`,
		`wx_pay_callback_url`,
        `branch`,
        `img_url`,
        `mobile_logo`,
        `wechat_logo`,
        `location_logo`,
		qiniu_url,
		qiniu_zone,
		qiniu_bucket,
		qiniu_access_key,
		qiniu_secret_key,
		pf_corp_id,
		pf_mini_appid,
		pf_default_deptid)
VALUES ('1', '-----BEGIN PRIVATE KEY-----\r\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgmkWbcHwg9nXppIdw\r\nxl/b2OjR4KZb2x81VtczYOOndGGhRANCAASAO1fTxyx8UXTz3xcQZV4tV+x2SqCX\r\nu6H3VluQ49wn8F8IAJNTAU3fJX0jAQG2hYR3ztCYF+L2mjFSV5yfLgOX\r\n-----END PRIVATE KEY-----\r\n', '-----BEGIN PUBLIC KEY-----\r\nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEgDtX08csfFF0898XEGVeLVfsdkqg\r\nl7uh91ZbkOPcJ/BfCACTUwFN3yV9IwEBtoWEd87QmBfi9poxUlecny4Dlw==\r\n-----END PUBLIC KEY-----\r\n', '1400135028', '37554', 'https://api.deecard.net/push/v4.0/paycallback/weixinCallback', 'https://api.deecard.net', 'https://res.deecard.net', 'https://res.deecard.net/mobile.png', 'https://res.deecard.net/webchat_mini.png', 'https://res.deecard.net/address.png', '2zASCnOlLvBBBwIyiVwJmP9Nu04zn7NB2bnc7fhO', 'eNOUTXmCkzvi9xDNd2bGq4qW_gaG1s0T4Ve2lm7P', 'https://res.deecard.net', 'deecard-prod', 'wwb6f9595dcb4e7463', 'wxb7c8f050d4f97cdd', '2', '0');

delete from enterprise;
INSERT INTO enterprise(`id`,
						`name`,
						`address`,
						`phone`,
						`business_license_url`,
						`business_license_code`,
						`expiry_time`,
						`sale_card_num`,
						`manager_name`,
						`manager_phone`,
						`create_time`,
						`modify_time`,
						`create_staff_id`,
						`auth_wx_soft`,
						`auth_ai_radar`,
						`auth_boss_radar`,
						`used_car_num`,
						`password`,
						`role`,
						`status`,
						`expire_time`,
						`auth_soft`,
						`auth_wei`,
						`head_icon`,
						`icon`,
						`template_id`, `is_deploy`, `enterprise_no`, `login_account`, `remark`, `address_info`, `parent_id`, `province_code`, `city_code`, `area_code`, `template_time`, `money_init`,
						`money_sum`, `money_leave`, `card_sum`)
VALUES ('00000000-0000-0000-0000-000000000000', null, '南京深卡网络技术有限公司', 'AE000000', '', null, '', '', null, '0', '滴卡小秘', '18952002107', '', null, null, '0', '0', '0', '0', '', '1', '1', null, null, null, 'https://res.deecard.net/dc_me_ent_logo.png', null, null, null, null, null, null, null, null, null, null, '2147483647', '2147483647', '2147483610', '2147483647', '834a3b6a-07ad-4c03-871f-17dcaa538a22', 'e8dad05e-0147-4f63-8abd-49f44bc66496');

delete from admin;
INSERT INTO admin(id,login,pass_word,name,phone,role,status,agent,type,login_time,head_icon,remark,create_time,create_id,custom_id,open_id)
VALUES ('00000000-0000-0000-0000-000000000000', 'admin', 'admin', '平台超级管理员', null, '1', '1', '超级平台', '1', '2019-01-21 16:00:42', null, null, null, null, null, null);

INSERT INTO admin(id,login,pass_word,name,phone,role,status,agent,type,login_time,head_icon,remark,create_time,create_id,custom_id,open_id)
VALUES ('00000000-0000-0000-0000-000000000001', '18952002107', '18952002107', '深卡小蜜', null, '1', '1', null, '5', '2018-12-12 22:04:19', null, null, null, null, '00000000-0000-0000-0000-000000000000', 'okTnc4j3za-_JO0ybgW-OJW-lPqk');

delete from user_menu_ref;
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('1',   '1',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('2',   '2',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('3',   '3',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('4',   '4',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('5',   '5',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('6',   '6',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('7',   '7',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('8',   '8',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('9',   '9',   '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('10',  '10',  '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('11',  '11',  '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('12',  '13',  '00000000-0000-0000-0000-000000000000',  '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('13',  '54',  '00000000-0000-0000-0000-000000000000',  '0');
INSERT INTO user_menu_ref(id, menu_id, user_id, status)  VALUES ('31', '62', '00000000-0000-0000-0000-000000000000', '1');

INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('14', '40', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('15', '46', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('16', '44', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('17', '43', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('18', '61', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('19', '58', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('20', '38', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('21', '53', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('22', '48', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('23', '55', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('24', '39', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('25', '56', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('26', '49', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('27', '60', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('28', '57', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('29', '47', '00000000-0000-0000-0000-000000000001', '1');
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('30', '59', '00000000-0000-0000-0000-000000000001', '1');

delete from department;
INSERT INTO department (enterprise_id, id, parent_id, name, `order`, wx_id, create_time, modify_time)
VALUES ('1', '00000000-0000-0000-0000-000000000000', '0', '南京深卡网络技术有限公司', '0', null, null, null);


delete from deploy;
INSERT INTO deploy (`enterprise_id`,
					 `id`,
					 `CorpID`,
					 `contactssecret`,
					 `ai_AgentId`,
					 `aisecret`,
					 `boss_AgentId`,
					 `bosssecret`,
					 `app_id`,
					 `secret`,
					 `mch_id`,
					 `pay_key`,
					 `private_key`,
					 `public_key`,
					 `skd_app_id`,
					 `manager_id`,
					 `message_template`,
					 `pay_template`)
VALUES ('00000000-0000-0000-0000-000000000000', 'wwb6f9595dcb4e7463', '5HX84DlYKLYDplbvp5_b0heQYevuh3Fq7b_OddfBO6Q', 'oaZWIfTPZc4UgkI5ZCxleU8ytvW6GLdM8B_-Zzm3hyI', 'Uva3_2E4lLB3Lo0eKqWLqP9x8GXKhoReJEsj6IZf4fA', '1000003', '1000002', 'wxb7c8f050d4f97cdd', '302dc3326974a2961b81c1d5f5295e42', '1519420141', 'deecard2018abcdefg12018888888abc', '00000000-0000-0000-0000-000000000000', '-----BEGIN PRIVATE KEY-----\r\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgmkWbcHwg9nXppIdw\r\nxl/b2OjR4KZb2x81VtczYOOndGGhRANCAASAO1fTxyx8UXTz3xcQZV4tV+x2SqCX\r\nu6H3VluQ49wn8F8IAJNTAU3fJX0jAQG2hYR3ztCYF+L2mjFSV5yfLgOX\r\n-----END PRIVATE KEY-----\r\n', '-----BEGIN PUBLIC KEY-----\r\nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEgDtX08csfFF0898XEGVeLVfsdkqg\r\nl7uh91ZbkOPcJ/BfCACTUwFN3yV9IwEBtoWEd87QmBfi9poxUlecny4Dlw==\r\n-----END PUBLIC KEY-----\r\n', '1400135028', 'admin', 'iAqNwyIfbE51ZDr--R5m6icIB3fZ0azzoxLKfiVbuJM', 'ZomkrVliRKOAzdQTnwvbZP7hpUa1hWCUmCHvjGrt3nQ');

delete from staff;
INSERT INTO staff (`id`, `wx_id`, `name`, `phone`, `create_time`, `status`, `modify_time`, `create_staff_id`, `pass_word`, `department_id`, `station`,
					`head_icon`, `open_ai`, `open_boss`, `enterprise_id`, `mobile`, `mail`, `weixin`, `address`, `role`,
					`soft_img_url`,
					`signature`, `vidio_id`, `audio_id`, `discount`, `temlate_id`, `discount_type`,
					`open_id`, `company_name`, `first_letter`)
VALUES ('DCSTAFF0000000000000000000000000', '00000000-0000-0000-0000-000000000000', '1', 'bcbb005094c0401896d2085424b68796', '18915962107', '深卡小蜜', '18915962107', null, '1', '2019-02-04 20:11:19', '00000000000000000000000000000000', '', '客服', 'https://res.deecard.net/imga8796d2e75084300b0ac5fc5a177af407.png', '1', '1', '', '7496606@qq.com', '南京市建邺区嘉业国际2号楼1801', '0', 'https://res.deecard.net/img089f09c06f6647a49c8097b998fa52614.jpg', '深卡网络是专业智能名片运营商，已经覆盖超20,000+个平台用户及150,000+名片用户；通过AI智能名片将纸质名片与微信名片连接，赋予了新一代智能名片：带雷达、懂管理、有商城等新特性，帮助2亿商务人士提升商务沟通效率，赋能9000万销售人员拓客、提升业绩。', '10.00', '4', '0', '1c31eaa7958d40c8af7234a3fd391b36', null, 'okTnc4j3za-_JO0ybgW-OJW-lPqk', null, null, '你好，我是南京深卡网络技术有限公司的深卡小蜜，这是我的电子名片，请惠存~', '', null, null);

delete from user_info;
INSERT INTO user_info(`id`,
						`wx_soft_id`,
						`open_id`, `nick_name`, `name`, `sex`, `phone`,
						`head_icon`, `create_time`, `modify_time`, `last_action_time`, `last_attach_time`, `hx_im_login`, `hx_im_password`, `session_key`, `last_enterprise_id`, `last_staff_id`, `from_user_id`)
VALUES ('DCUSER00-0000-0000-0000-000000000001', 'wxb7c8f050d4f97cdd', 'okTnc4j3za-_JO0ybgW-OJW-lPqk', '滴卡小蜜', null, '1', '18952002107', 'https://res.deecard.net/dc_user_default_logo.png', null, null, null, null, null, null, null, '360af6e9-7a51-4b46-b746-2a58d9d18dc5', '1eba573aafaf40b4986ddf26c55ac390', null);

delete from user_staff_rela;
INSERT INTO user_staff_rela(`id`, `user_id`, `staff_id`, `enterprise_id`, `department_id`, `froms`, `from_user_id`, `position`, `status`, `create_time`, `modify_time`, `is_zan`, `name`, `mail`, `company`, `birthday`, `remark`, `phone`, `last_attach_time`, `last_action_time`, `rate`, `clinch_time`, `clinch_rate`, `rela_status`)
VALUES ('DCREL000-0000-0000-0000-000000000001', 'DCUSER00-0000-0000-0000-000000000001', 'DCSTAFF0000000000000000000000000', '00000000-0000-0000-0000-000000000000', '1', '4', null, null, '1', null, null, '1', null, null, null, null, null, null, null, '2019-01-11 15:15:02', '25.00', null, null, '1', '0');