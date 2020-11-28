select wxuser.nick_name, wxuser.open_id, staff.name,dept.name,ent.name,adm.login
from user_info 									wxuser
	inner join user_staff_rela 		rela 					on rela.user_id 	= wxuser.id
	inner join staff  						staff					on staff.id 			= rela.staff_id
	inner join department					dept					on dept.id 				= staff.department_id and rela.enterprise_id = dept.enterprise_id
	inner join enterprise					ent						on ent.id 				= dept.enterprise_id
	inner join admin							adm						on adm.custom_id 	= ent.id
where wxuser.id = '82722883-43c7-4878-a2ba-3c7678d1aa42'
  and wxuser.wx_soft_id = 'wx12102dd828844893'

--查我所在企业的管理员账号
select *
from admin
where custom_id in(
		select ent.id
		from enterprise 								ent
			inner join department					dept					on dept.enterprise_id 				= ent.id
			inner join staff  						staff					on staff.enterprise_id 	= dept.enterprise_id and staff.department_id = dept.id
			inner join user_staff_rela 		rela 					on rela.staff_id 	= staff.id
			inner join user_info 					wxuser				on wxuser.id = rela.user_id
		where wxuser.id = '82722883-43c7-4878-a2ba-3c7678d1aa42'
		and wxuser.wx_soft_id = 'wx12102dd828844893'
)

--我所在企业的列表
select ent.id, ent.name
from enterprise 								ent
where id in(
		select dept.enterprise_id
		from department					dept
			inner join staff  						staff					on staff.enterprise_id 	= dept.enterprise_id and staff.department_id = dept.id
			inner join user_staff_rela  	rela					on rela.staff_id 	= staff.id
			inner join user_info  				wxuser				on wxuser.id = rela.user_id
		where wxuser.id = '5683814d-e608-4633-a6bd-5c04fa84000c'
		and wxuser.wx_soft_id = 'wx5163714a5523f854'
)

--查我名片夹的名片
select *
from staff staff
where staff.id in(
	select staff_id
	from user_staff_rela rela
		inner join user_info  				wxuser				on wxuser.id = rela.user_id
	where wxuser.id = '5683814d-e608-4633-a6bd-5c04fa84000c'
		and wxuser.wx_soft_id = 'wx5163714a5523f854'
)

--查我自己的名片
select *
from staff staff
	inner join user_staff_rela rela					on staff.id = rela.staff_id
	inner join user_info  		 wxuser				on wxuser.id = rela.user_id
where wxuser.id = '5683814d-e608-4633-a6bd-5c04fa84000c'
		and wxuser.wx_soft_id = 'wx5163714a5523f854'
		and staff.open_id = wxuser.open_id



