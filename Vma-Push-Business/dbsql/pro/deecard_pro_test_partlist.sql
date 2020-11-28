CREATE PROCEDURE pro_test_partlist(
	IN deptid VARCHAR(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
	IN enid VARCHAR(36)   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
)
BEGIN
	SET @sTempChd = deptid;
	SET @count=1;

	delete from child_dept_enter
	where department_id=deptid;

	insert into child_dept_enter(id,department_id) VALUE(deptid,deptid);

	WHILE @count>0 DO
		insert into child_dept_enter(id,department_id)
		select id,deptid from department where FIND_IN_SET(parent_id,@sTempChd) and enterprise_id=enid;

		select group_concat(id) into @sTempChd
		from department
		where FIND_IN_SET(parent_id,@sTempChd) and enterprise_id=enid;

		select count(1) into @count
		from department
		where FIND_IN_SET(parent_id,@sTempChd) and enterprise_id=enid;
	END WHILE;
END;

