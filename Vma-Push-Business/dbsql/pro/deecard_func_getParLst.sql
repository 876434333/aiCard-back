CREATE FUNCTION getParLst ( deptid VARCHAR(128), enid VARCHAR(128) )
RETURNS varchar(4000)
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);

	SET sTemp = '$';
	SET sTempChd = deptid;

	WHILE sTempChd is not NULL DO
		SET sTemp = CONCAT(sTemp,',',sTempChd);

		SELECT group_concat(id) INTO sTempChd FROM department
		where enterprise_id=CONVERT(enid USING utf8) COLLATE utf8_unicode_ci and FIND_IN_SET(parent_id,CONVERT(sTempChd USING utf8) COLLATE utf8_unicode_ci)>0;
	END WHILE;

	return SUBSTRING(sTemp,3) ;
END


