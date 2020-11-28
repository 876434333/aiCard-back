CREATE FUNCTION getAllStaffOfDepart(orgid INT)
RETURNS VARCHAR(4000) 		CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
BEGIN
  DECLARE oTemp VARCHAR(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  DECLARE oTempChild VARCHAR(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  SET oTemp = '';
  SET oTempChild = CAST(orgid AS CHAR);

  WHILE oTempChild IS NOT NULL
  DO
    SET oTemp = CONCAT(oTemp,',',oTempChild);
    SELECT GROUP_CONCAT(id) INTO oTempChild FROM department WHERE FIND_IN_SET(parent_id, oTempChild) > 0;
  END WHILE;
  RETURN oTemp;
END