CREATE PROCEDURE pro_test_EnterPartList(
	IN enid VARCHAR(36)		CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
)
BEGIN
	SET @sTempChd = enid;
	SET @count=1;
	delete from child_dept_enter where enterprise_id=enid;
	insert into child_dept_enter(id,enterprise_id) VALUE(enid,enid);
	WHILE @count>0 DO
		insert into child_dept_enter(id,enterprise_id) 
		select id,enid 
		from enterprise 
		where FIND_IN_SET(parent_id,@sTempChd);

		select count(1) into @count 
		from enterprise 
		where FIND_IN_SET(parent_id,@sTempChd);	

		select group_concat(id) into @sTempChd 
		from enterprise 
		where FIND_IN_SET(parent_id,@sTempChd);	
		
	END WHILE;
END;


