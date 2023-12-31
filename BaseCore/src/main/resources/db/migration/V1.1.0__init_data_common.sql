-- 역할
insert into cm_role(ROLE_SEQ, ROLE_NM, DEF_PAGE, ROLE_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(100, '어드민', '/dashboard/admin',   '어드민', 'N', NOW(), 100, NOW(), 100);
insert into cm_role(ROLE_SEQ, ROLE_NM, DEF_PAGE, ROLE_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(101, '관리자', '/dashboard/manager', '관리자', 'N', NOW(), 100, NOW(), 100);
insert into cm_role(ROLE_SEQ, ROLE_NM, DEF_PAGE, ROLE_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(102, '사용자', '/dashboard/user',    '사용자', 'N', NOW(), 100, NOW(), 100);
insert into cm_role(ROLE_SEQ, ROLE_NM, DEF_PAGE, ROLE_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(103, '확인자', '/dashboard/checker', '확인자', 'N', NOW(), 100, NOW(), 100);
insert into cm_role(ROLE_SEQ, ROLE_NM, DEF_PAGE, ROLE_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(104, '작업자', '/dashboard/worker',  '작업자', 'N', NOW(), 100, NOW(), 100);

-- 사용자/패스워드/역할/ 조직
insert into cm_user(USER_SEQ, LOGIN_ID, USER_NM, USER_STAT_CD, USER_TEL_NO, ACUNT_EXP_DT, LOGIN_FAIL_CNT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 'onetime@gmail.com', '어드민', 'ENABLED', '010-9659-3359', adddate(now(), 365), 0, 'N', NOW(), 100, NOW(), 100);
insert into cm_user_pwd(USER_PWD_SEQ, USER_SEQ, USER_PWD, PWD_EXP_DT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(101, 101, '{bcrypt}$2a$10$ErKoq2mRDOVizhm8MDqRnebaDfqRwU47ixtZM58aEIMbdJX3jjxYy', adddate(now(), 100), 'N', NOW(), 100, NOW(), 100);
insert into cm_user_role_map(ROLE_SEQ, USER_SEQ, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(100, 101, 1, 'N', NOW(), 100, NOW(), 100);

insert into cm_user(USER_SEQ, LOGIN_ID, USER_NM, USER_STAT_CD, USER_TEL_NO, ACUNT_EXP_DT, LOGIN_FAIL_CNT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (102, 'fishingday@gmail.com', 'K군', 'ENABLED', '010-9659-3359', adddate(now(), 365), 0, 'N', NOW(), 100, NOW(), 100);
insert into cm_user_pwd(USER_PWD_SEQ, USER_SEQ, USER_PWD, PWD_EXP_DT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(102, 102, '{bcrypt}$2a$10$ErKoq2mRDOVizhm8MDqRnebaDfqRwU47ixtZM58aEIMbdJX3jjxYy', adddate(now(), 100), 'N', NOW(), 100, NOW(), 100);
insert into cm_user_role_map(ROLE_SEQ, USER_SEQ, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(101, 102, 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_user_role_map(ROLE_SEQ, USER_SEQ, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(103, 102, 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_user_role_map(ROLE_SEQ, USER_SEQ, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(104, 102, 2, 'N', NOW(), 100, NOW(), 100);

insert into cm_user(USER_SEQ, LOGIN_ID, USER_NM, USER_STAT_CD, USER_TEL_NO, ACUNT_EXP_DT, LOGIN_FAIL_CNT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 'makewaves.1st@gmail.com', '박서현', 'ENABLED', '010-1234-5678', adddate(now(), 365), 0, 'N', NOW(), 100, NOW(), 100);
insert into cm_user_pwd(USER_PWD_SEQ, USER_SEQ, USER_PWD, PWD_EXP_DT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(103, 103, '{bcrypt}$2a$10$ErKoq2mRDOVizhm8MDqRnebaDfqRwU47ixtZM58aEIMbdJX3jjxYy', adddate(now(), 100), 'N', NOW(), 100, NOW(), 100);
insert into cm_user_role_map(ROLE_SEQ, USER_SEQ, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(104, 103, 1, 'N', NOW(), 100, NOW(), 100);

insert into cm_user(USER_SEQ, LOGIN_ID, USER_NM, USER_STAT_CD, USER_TEL_NO, ACUNT_EXP_DT, LOGIN_FAIL_CNT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 'makewaves.2nd@gmail.com', '박수현', 'ENABLED', '010-1234-5678', adddate(now(), 365), 0, 'N', NOW(), 100, NOW(), 100);
insert into cm_user_pwd(USER_PWD_SEQ, USER_SEQ, USER_PWD, PWD_EXP_DT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(104, 104, '{bcrypt}$2a$10$ErKoq2mRDOVizhm8MDqRnebaDfqRwU47ixtZM58aEIMbdJX3jjxYy', adddate(now(), 100), 'N', NOW(), 100, NOW(), 100);
insert into cm_user_role_map(ROLE_SEQ, USER_SEQ, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(104, 104, 1, 'N', NOW(), 100, NOW(), 100);

insert into cm_user_relat(RELATOR_SEQ, TARGETER_SEQ, USER_RELAT_CD, RELAT_APLY_DT, TARGETER_AGRE_YN, TARGETER_AGRE_DT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(102, 103, 'CHILD', NOW(), 'Y', NOW(), 'N', NOW(), 100, NOW(), 100);
insert into cm_user_relat(RELATOR_SEQ, TARGETER_SEQ, USER_RELAT_CD, RELAT_APLY_DT, TARGETER_AGRE_YN, TARGETER_AGRE_DT, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES(102, 104, 'CHILD', NOW(), 'Y', NOW(), 'N', NOW(), 100, NOW(), 100);



-- 메뉴 관리
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES ( 100, null, '/common/login', '로그인', '공통', 'Y', 'N', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES ( 110, null, '/common/join_us', '가입', '가입', 'Y', 'N', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (1000, null, '/dashboard', '대시보드', '대시보드', 'Y', 'N', 1, 'N', NOW(), 100, NOW(), 100);

insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2000, null, null, 'TODO', 'TODO', 'N', 'Y', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2110, 2000, '/checker/today_plan',   '작업계획', '작업계획', 'N', 'Y', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2120, 2000, '/checker/settle', '할일정산', '할일정산', 'N', 'Y', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2130, 2000, '/checker/todo_mgt','할일관리', '할일관리', 'N', 'Y', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2140, 2000, '/checker/quiz_mgt','문제관리', '문제관리', 'N', 'Y', 4, 'N', NOW(), 100, NOW(), 100);

insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2210, 2000, '/worker/today_work', '오늘의 할일', '오늘의 할일', 'N', 'Y', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2220, 2000, '/worker/work_mgt',   '할일관리', '할일관리', 'N', 'Y', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2230, 2000, '/worker/history',    '작업이력', '작업이력', 'N', 'Y', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (2240, 2000, '/worker/settle',     '정산이력', '정산이력', 'N', 'Y', 4, 'N', NOW(), 100, NOW(), 100);


insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (7000, null, null, '놀이', '놀이', 'N', 'Y', 7, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (7900, 7000, null, 'WPI', 'WPI', 'N', 'Y', 7, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (7910, 7900, '/play/wpi/test', 'WPI 테스트', 'WPI 테스트', 'N', 'Y', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (7920, 7900, '/play/wpi/result', 'WPI 결과', 'WPI 결과', 'N', 'Y', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (8000, null, null, '사용자', '사용자', 'N', 'Y', 8, 'N', NOW(), 100, NOW(), 100);

insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (8010, 8000, '/user/user_info', '사용자 정보', '사용자 정보', 'N', 'Y', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (8020, 8000, '/user/bookmark', '북마크', '북마크', 'N', 'Y', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (8030, 8000, '/user/chg_pwd', '패스워드 변경', '패스워드 변경', 'N', 'Y', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (8040, 8000, '/user/allow_ip', '허용 IP', '허용 IP', 'N', 'Y', 4, 'N', NOW(), 100, NOW(), 100);

insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9000, null, null, '시스템', '시스템', 'N', 'Y', 9, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9010, 9000, '/system/user_mgt', '사용자 관리', '사용자 관리', 'N', 'Y', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9020, 9000, '/system/org_mgt',  '조직 관리', '조직 관리', 'N', 'Y', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9030, 9000, '/system/role_mgt', '역할 관리', '역할 관리', 'N', 'Y', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9040, 9000, '/system/menu_mgt', '메뉴 관리', '메뉴 관리', 'N', 'Y', 4, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9050, 9000, '/system/code_mgt', '코드 관리', '코드 관리', 'N', 'Y', 5, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9060, 9000, '/system/noti_mgt', '공지 관리', '공지 관리', 'N', 'Y', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9070, 9000, '/system/schedule_mgt', '스케줄러 관리', '스케줄러 관리', 'N', 'Y', 6, 'N', NOW(), 100, NOW(), 100);
insert into cm_menu (MENU_SEQ, UP_MENU_SEQ, MENU_PATH, MENU_NM, MENU_DESC, CM_SCREN_YN, PRNT_YN, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (9080, 9000, '/system/env_mgt', '환경 관리', '환경 관리', 'N', 'Y', 7, 'N', NOW(), 100, NOW(), 100);


-- 역할 메뉴 맵핑
-- 어드민
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 8010, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 8020, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 8030, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 8040, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9010, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9020, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9030, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9040, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9050, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9060, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9070, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (100, 9080, 'N', NOW(), 100, NOW(), 100);

-- 관리자 
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 8010, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 8020, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 8030, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 8040, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 9010, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 9020, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 9030, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 9040, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (101, 9060, 'N', NOW(), 100, NOW(), 100);

-- 확인자 
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 8010, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 8020, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 8030, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 8040, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 2110, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 2120, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 2130, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (103, 2140, 'N', NOW(), 100, NOW(), 100);

-- 작업자
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 8010, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 8020, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 8030, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 8040, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 2210, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 2220, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 2230, 'N', NOW(), 100, NOW(), 100);
insert into cm_role_menu_map(ROLE_SEQ, MENU_SEQ, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) VALUES (104, 2240, 'N', NOW(), 100, NOW(), 100);

-- 그룹코드와 코드상세
-- 그룹코드는 컬럼명이 되며, 코드 상세는 그 컬럼에 들어갈 수 있는 값을 의미
insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('ACCES_LOG_TYP_CD', '접근 로그 구분 코드', '접근 로그 구분', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('ACCES_LOG_TYP_CD', 'REQ', '요청', 'Request', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('ACCES_LOG_TYP_CD', 'RES', '응답', 'Response', 2, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('NOTI_SEND_GRD_CD', '공지 발송 등급 코드', '공지 발송 등급 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('NOTI_SEND_GRD_CD', 'INFO', '정보', '정보', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('NOTI_SEND_GRD_CD', 'IMPORTANT', '중요', '중요', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('NOTI_SEND_GRD_CD', 'EMERGENCY', '긴급', '긴급', 3, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('ROLE_CHG_CD', '역할 변경 코드', '역할 변경 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('ROLE_CHG_CD', 'ADD', '추가', '추가', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('ROLE_CHG_CD', 'REMOVE', '제거', '제거', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('ROLE_CHG_CD', 'UPDATE', '변경', '변경', 3, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('SEND_MEDIA_TYP_CD', '발송 매체 구분 코드', '발송 매체 구분 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('SEND_MEDIA_TYP_CD', 'EMAIL', '이메일', '이메일', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('SEND_MEDIA_TYP_CD', 'NOTI', '알림', '알림', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('SEND_MEDIA_TYP_CD', 'SMS', 'SMS', 'SMS', 3, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_STAT_CD', '사용자 상태 코드', '사용자 상태 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_STAT_CD', 'ENABLED', '정상', '로그인 가능 상태', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_STAT_CD', 'LOCKED', '로그인잠김', '로그인 잠김 상태', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_STAT_CD', 'DISABLED', '비활성', '계정 정지 상태', 3, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', '기록 생산자 코드', '사용자 상태 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'LOGIN_SUCCESS_FORM', '로그인 성공(form)', '로그인 성공(form)', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'LOGIN_FAIL_FORM', '로그인 실패', '로그인 실패', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'LOGIN_SUCCESS_AJAX', '로그인 성공(ajax)', '로그인 성공(ajax)', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'LOGIN_FAIL_AJAX', '로그인 실패(ajax)', '로그인 실패(ajax)', 4, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'ACCESS_DENIED', '접근 거부', '접근 거부', 5, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'LOGOUT_SUCCESS', '로그아웃 성공', '로그아웃 성공', 6, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'INTERNAL_SERVER_ERROR', '서버 내부 오류', '서버 내부 오류', 7, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WRITE_MAKR_CD', 'OTHER_ERRROR', '기타 오류', '기타 오류', 8, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('YN', '여부', 'Yes or No', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('YN', 'Y', '예', '예', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('YN', 'N', '아니오', '아니오', 2, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WORK_STAT_CD', '작업 상태 코드', '작업 상태 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WORK_STAT_CD', 'READY', '준비', '준비', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WORK_STAT_CD', 'ONGOING', '진행중', '진행중', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WORK_STAT_CD', 'FAIL', '실패', '실패', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('WORK_STAT_CD', 'DONE', '완료', '완료', 4, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('BATCH_JOB_TYPE', '배치 작업 구분', '배치 작업 구분', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('BATCH_JOB_TYPE', 'SIMPLE', 'Simple', 'Simple', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('BATCH_JOB_TYPE', 'CRON', 'Cron', 'Cron', 2, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('POINT_USE_CD', '포인트 사용 코드', '포인트 사용 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('POINT_USE_CD', 'USE_NETWORK', '네트워크사용', '네트워크사용', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('POINT_USE_CD', 'CASH', '현금', '현금', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('POINT_USE_CD', 'BANK', '계좌이체', '계좌이체', 3, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('QUIZ_TYP_CD', '퀴즈 유형 코드', '퀴즈 유형 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('QUIZ_TYP_CD', 'ENG_WORD', '영어단어', '영어단어', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('QUIZ_TYP_CD', 'ENG_IDOIOM', '영어숙어', '영어숙어', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('QUIZ_TYP_CD', 'ENG_SENTENCE', '영어문장', '영어문장', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('QUIZ_TYP_CD', 'MATH', '수학', '수학', 4, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_CRE_CD', '할일 생성 코드', '할일 생성 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_CRE_CD', 'DAILY', '매일', '매일', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_CRE_CD', 'WEEK', '매주', '매주', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_CRE_CD', 'MONTH', '매월', '매월', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_CRE_CD', 'DIRECT', '직접', '직접', 4, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_CRE_CD', 'BATCH', '크론배치', '크론배치', 5, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', '할일 유형 코드', '할일 유형 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'WAKEUP', '기상', '기상', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'SLEEP', '취침', '취침', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'CLEAN', '청소', '청소', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'DIARY', '일기', '일기', 4, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'EXERCISE', '운동', '운동', 5, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'READING', '독서', '독서', 6, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'EXAM', '시험', '시험', 7, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('TODO_TYP_CD', 'SOME', '이것저것', '이것저것', 8, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_grp(GRP_CD, GRP_CD_NM, GRP_CD_DESC, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_RELAT_CD', '사용자 관계 코드', '사용자 관계 코드', 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_RELAT_CD', 'GRAND', '조부모', '조부모', 1, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_RELAT_CD', 'PARENT', '부모', '부모', 2, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_RELAT_CD', 'RELAT', '친척', '친척', 3, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_RELAT_CD', 'CHILD', '자녀', '자녀', 4, 'N', NOW(), 100, NOW(), 100);
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
VALUES ('USER_RELAT_CD', 'SELF', '본인', '본인', 5, 'N', NOW(), 100, NOW(), 100);
