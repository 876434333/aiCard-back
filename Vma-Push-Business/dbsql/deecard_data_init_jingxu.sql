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
VALUES ('1',
 		'-----BEGIN PRIVATE KEY-----\r\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgmkWbcHwg9nXppIdw\r\nxl/b2OjR4KZb2x81VtczYOOndGGhRANCAASAO1fTxyx8UXTz3xcQZV4tV+x2SqCX\r\nu6H3VluQ49wn8F8IAJNTAU3fJX0jAQG2hYR3ztCYF+L2mjFSV5yfLgOX\r\n-----END PRIVATE KEY-----\r\n',
 		'-----BEGIN PUBLIC KEY-----\r\nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEgDtX08csfFF0898XEGVeLVfsdkqg\r\nl7uh91ZbkOPcJ/BfCACTUwFN3yV9IwEBtoWEd87QmBfi9poxUlecny4Dlw==\r\n-----END PUBLIC KEY-----\r\n',
		'1400135028',
		'37554',
		'https://api.dev.deecard.net/push/v4.0/paycallback/weixinCallback',
		'https://api.dev.deecard.net',
		'https://res.dev.deecard.net',
		'https://res.dev.deecard.net/mobile.png',
		'https://res.dev.deecard.net/webchat_mini.png',
		'https://res.dev.deecard.net/address.png',
		'https://res.dev.deecard.net',
		'0',
		'deecard-test',
		'2zASCnOlLvBBBwIyiVwJmP9Nu04zn7NB2bnc7fhO',
		'eNOUTXmCkzvi9xDNd2bGq4qW_gaG1s0T4Ve2lm7P',
		'ww6193e3e027dcc9b1',
		'wxfeaf2c7e0c5f2207',
		1);

/* 初始的平台企业 */
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
VALUES ('00000000-0000-0000-0000-000000000000', '景旭信息技术有限公司', '', NULL, '', '', NULL, '0',
		'景旭小蜜', '15220089930',
		NULL, NULL, '', '0', '0', '0', '0', '', '1', '1',
		NULL, NULL, NULL,
		'https://res.deecard.szrenzhi.com/dc_me_ent_logo.png',
		NULL, NULL, NULL, 'AE000000', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '9999999999', '9999999999', '9999999999', '9999999999');

delete from admin;
-- 平台超级管理员
INSERT INTO admin(id,login,pass_word,name,phone,role,status,agent,type,login_time,head_icon,remark,create_time,create_id,custom_id,open_id)
VALUES ('00000000-0000-0000-0000-000000000000', 'admin', 'admin', '平台超级管理员', null, 1, 1, '超级平台', 1, null, null, null, null, null, null, null);
-- 企业管理员用户
INSERT INTO admin(id,login,pass_word,name,phone,role,status,agent,type,login_time,head_icon,remark,create_time,create_id,custom_id,open_id)
VALUES ('00000000-0000-0000-0000-000000000001', '15220089930', '15220089930', '景旭小秘', null, 1, 1, null, 5, null, null, null, null, null, '00000000-0000-0000-0000-000000000000', 'oIWGf4gBgSeK4EZ08AiN1JK86sbE');


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
INSERT INTO user_menu_ref(id, menu_id, user_id, status) VALUES ('286', '62', '00000000-0000-0000-0000-000000000000', '1');

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
VALUES ('00000000-0000-0000-0000-000000000000', 1, 0, '景旭信息技术有限公司', 0, NULL,  NULL, NULL);

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
VALUES (			'00000000-0000-0000-0000-000000000000',
					'00000000-0000-0000-0000-000000000000',
					'ww6193e3e027dcc9b1',
					'_vELKZhnnUo2aus-5w28JkpPTe3sZNqEYNrzg829d2Y',
					'1000004',
					'cDSWRRsyojUb17_kltONOqkXXF_kMUCLxivd3lcEGeM',
					'1000005',
					'q_fHW9p7gpRLfRJXdYkxF4eZ7HN3PcH-ITUv1e_tNgo',
					'wxfeaf2c7e0c5f2207',
					'9bf1ab7d5889452d160f0248fd2c23f2',
					'1519420141',
					'deecard2018abcdefg12018888888abc',
					'-----BEGIN PRIVATE KEY-----\r\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgmkWbcHwg9nXppIdw\r\nxl/b2OjR4KZb2x81VtczYOOndGGhRANCAASAO1fTxyx8UXTz3xcQZV4tV+x2SqCX\r\nu6H3VluQ49wn8F8IAJNTAU3fJX0jAQG2hYR3ztCYF+L2mjFSV5yfLgOX\r\n-----END PRIVATE KEY-----\r\n',
					'-----BEGIN PUBLIC KEY-----\r\nMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEgDtX08csfFF0898XEGVeLVfsdkqg\r\nl7uh91ZbkOPcJ/BfCACTUwFN3yV9IwEBtoWEd87QmBfi9poxUlecny4Dlw==\r\n-----END PUBLIC KEY-----\r\n',
					'1400135028',
					'admin',
					'alBfC1VPKUUA0JVV9KHRtaV2JtuBhKl1BSWu55B4iJM',
					'ZomkrVliRKOAzdQTnwvbZP7hpUa1hWCUmCHvjGrt3nQ');

--  深卡小秘
delete from staff;
INSERT INTO staff (`id`, `wx_id`, `name`, `phone`, `create_time`, `status`, `modify_time`, `create_staff_id`, `pass_word`, `department_id`, `station`,
					`head_icon`, `open_ai`, `open_boss`, `enterprise_id`, `mobile`, `mail`, `weixin`, `address`, `role`,
					`soft_img_url`,
					`signature`, `vidio_id`, `audio_id`, `discount`, `temlate_id`, `discount_type`,
					`open_id`, `company_name`, `first_letter`)
VALUES ('DCSTAFF0000000000000000000000000', 'li876434333', '景旭小蜜', '15220089930', null, '1', null,
		 '00000000000000000000000000000000', '', '1', '景旭小蜜',
		 'https://res.deecard.szrenzhi.com/dc_user_default_logo.png', '1', '1', '00000000-0000-0000-0000-000000000000', '', '', '', '', 0,
		 'https://res.deecard.szrenzhi.com/dc_card_blood0.png',
		 '',
		 NULL, NULL, '10.00', '1', '0',
		 'oIWGf4gBgSeK4EZ08AiN1JK86sbE', NULL, NULL);

delete from user_info;
INSERT INTO user_info(`id`,
						`wx_soft_id`,
						`open_id`, `nick_name`, `name`, `sex`, `phone`,
						`head_icon`, `create_time`, `modify_time`, `last_action_time`, `last_attach_time`, `hx_im_login`, `hx_im_password`, `session_key`, `last_enterprise_id`, `last_staff_id`, `from_user_id`)
VALUES ('DCUSER00-0000-0000-0000-000000000001',
						'wxfeaf2c7e0c5f2207',
						'oIWGf4gBgSeK4EZ08AiN1JK86sbE', '景旭小蜜', NULL, '1', NULL,
						'https://res.deecard.szrenzhi.com/dc_user_default_logo.png', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

delete from user_staff_rela;
INSERT INTO user_staff_rela(`id`, `user_id`, `staff_id`, `enterprise_id`, `department_id`, `froms`, `from_user_id`, `position`, `status`, `create_time`, `modify_time`, `is_zan`, `name`, `mail`, `company`, `birthday`, `remark`, `phone`, `last_attach_time`, `last_action_time`, `rate`, `clinch_time`, `clinch_rate`, `rela_status`)
VALUES ('DCREL000-0000-0000-0000-000000000001',
		'DCUSER00-0000-0000-0000-000000000001',
		'DCSTAFF0000000000000000000000000',
		'00000000-0000-0000-0000-000000000000',
		'1', '4', NULL, NULL, '1', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0.00', NULL, NULL, '1');


