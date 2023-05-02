drop table if exists account CASCADE ; 
drop table if exists cm_cd_dtl CASCADE ;
drop table if exists cm_grp_cd CASCADE ;
drop table if exists cm_important_log CASCADE ;
drop table if exists cm_menu CASCADE ;
drop table if exists cm_menu_dtl CASCADE ;
drop table if exists cm_menu_dtl_role_map CASCADE ;
drop table if exists cm_noti CASCADE ;
drop table if exists cm_noti_user_map CASCADE ;
drop table if exists cm_role CASCADE ;
drop table if exists cm_role_chg_log CASCADE ;
drop table if exists cm_role_menu_map CASCADE ;
drop table if exists cm_user CASCADE ;
drop table if exists cm_user_acces_log CASCADE ;
drop table if exists cm_user_alow_ip CASCADE ;
drop table if exists cm_user_bookmark CASCADE ;
drop table if exists cm_user_pwd CASCADE ;
drop table if exists cm_user_role_map CASCADE ;
drop table if exists resources CASCADE ;
drop table if exists role CASCADE ;
drop table if exists role_resources CASCADE ;
drop table if exists user_roles CASCADE ;
drop sequence if exists hibernate_sequence;
drop sequence if exists seq_cm_important_log;
drop sequence if exists seq_cm_menu;
drop sequence if exists seq_cm_menu_dtl;
drop sequence if exists seq_cm_noti_user_map;
drop sequence if exists seq_cm_role;
drop sequence if exists seq_cm_role_chg_log;
drop sequence if exists seq_cm_user;
drop sequence if exists seq_cm_user_acces_log;
drop sequence if exists seq_cm_user_alow_ip;

DROP TABLE QRTZ_FIRED_TRIGGERS;
DROP TABLE QRTZ_CRON_TRIGGERS;
DROP TABLE QRTZ_SIMPROP_TRIGGERS;
DROP TABLE QRTZ_SIMPLE_TRIGGERS;
DROP TABLE QRTZ_SCHEDULER_STATE;
DROP TABLE QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE QRTZ_TRIGGERS;
DROP TABLE QRTZ_JOB_DETAILS;
DROP TABLE QRTZ_CALENDARS;
DROP TABLE QRTZ_BLOB_TRIGGERS;
DROP TABLE QRTZ_LOCKS;

DROP TABLE "flyway_schema_history"
