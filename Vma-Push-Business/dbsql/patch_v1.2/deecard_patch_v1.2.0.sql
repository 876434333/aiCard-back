
alter table shop 				add paytype				int 				  	DEFAULT 0;
alter table user_staff_rela 	add is_collect 			int 					DEFAULT 0;
alter table sys_config  		add qiniu_zone				varchar(16)				DEFAULT NULL;
alter table shop 				add paytype 				int 					DEFAULT 0; /*是否开通支付功能*/
// 七牛云为华南区
UPDATE sys_config SET qiniu_zone = '0';

// 删除七牛云 dc_me_radar.png (企业架构->商务雷达)



