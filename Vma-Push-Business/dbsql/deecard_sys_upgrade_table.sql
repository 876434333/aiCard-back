/*--3.补丁升级  */
drop table IF EXISTS t_sys_app;						/**APP类型表 */
drop table IF EXISTS t_sys_app_version;				/**APP版本表 */

/*--3.1升级包:应用程序  */
CREATE TABLE t_sys_app(
    id              varchar(36)         	NOT NULL,       		/*-- 应用ID(appId) */
    appname         varchar(100)        	NULL,           		/*-- 英文名 */
    name        	varchar(100)        	NULL,           		/*-- 中文名 */
    lastversionid   bigint         		NULL,           		/*-- 最新版本号 */
    constraint PK_SYS_APP primary key (id)
);

/*--3.2升级包:历史版本  */
CREATE TABLE t_sys_app_version(
    id              bigint              NOT NULL,       		/*-- 安装包编码(versionId) */
    appid           bigint              not null,       		/*-- 所属APP=t_sys_app.id*/
    version         varchar(10)         NOT NULL,       		/*-- 版本号  */
    releasetime     datetime            NOT NULL,       		/*-- 发布时间  */
    downloadtimes   int                 NULL,           		/*-- 下载次数  */
    notes           varchar(256)        NULL,           		/*-- 备注说明  */
    constraint PK_SYS_APP_VERSION primary key (id)
);
create index idx_sys_appversion ON t_sys_app_version(appid,version);
