DROP TABLE USER_ROLES;
DROP TABLE ROLE_RESOURCES;
DROP TABLE ROLE;
DROP TABLE RESOURCES;
DROP TABLE ACCOUNT;

DROP TABLE CM_USER_ROLE_MAP CASCADE CONSTRAINTS;
DROP TABLE CM_CD_DTL CASCADE CONSTRAINTS;
DROP TABLE CM_GRP_CD CASCADE CONSTRAINTS;
DROP TABLE CM_USER_ACCES_LOG CASCADE CONSTRAINTS;
DROP TABLE CM_USER_PWD CASCADE CONSTRAINTS;
DROP TABLE CM_USER_ALOW_IP CASCADE CONSTRAINTS;
DROP TABLE CM_MENU_DTL_ROLE_MAP CASCADE CONSTRAINTS;
DROP TABLE CM_MENU_DTL CASCADE CONSTRAINTS;
DROP TABLE CM_ROLE_CHG_LOG CASCADE CONSTRAINTS;
DROP TABLE CM_USER_BOOKMARK CASCADE CONSTRAINTS;
DROP TABLE CM_ROLE_MENU_MAP CASCADE CONSTRAINTS;
DROP TABLE CM_MENU CASCADE CONSTRAINTS;
DROP TABLE CM_ROLE CASCADE CONSTRAINTS;
DROP TABLE CM_NOTI_USER_MAP CASCADE CONSTRAINTS;
DROP TABLE CM_NOTI CASCADE CONSTRAINTS;
DROP TABLE CM_USER CASCADE CONSTRAINTS;

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
drop table "flyway_schema_history";
drop sequence hibernate_sequence;