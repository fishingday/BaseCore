insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (1, null, '일찍잠들기', null, '확인자가 체크', null, 'SLEEP', null, 'DAILY', null, 3000, 1, 'CHECKER', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (2, 1,    '11시 이전에 잠들기',     null, '확인자가 체크', '23:00', 'SLEEP', 1, null, null, 3000, 1, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (3, 1,    '11시 30분 이전에 잠들기', null, '확인자가 체크', '23:30', 'SLEEP', 2, null, null, 1000, 1, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (4, 1,    '12시 이전에 잠들기',     null,	'확인자가 체크', '0:0',   'SLEEP', 3, null, null,  500, 1, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (5, null, '일찍일어나기',         null, '영어문제 풀기', null,    'WAKEUP', null, 'DAILY', null, 2000, 1, 'TIME', null, '2024-01-01', '9999-12-31', '05:00', '07:00', 'Y', 'ENG_WORD', 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (6, 5,    '6시 전에 일어나기',      null,	'영어문제 풀기', '6:00',  'WAKEUP', 1,	null, null, 2000, 1, null, null, '2024-01-01', '9999-12-31', '05:00', '06:00', 'Y', 'ENG_WORD', 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (7, 5,    '7시 전에 일어나기',      null,	'영어문제 풀기', '7:00',  'WAKEUP', 2,	null, null, 1000, 1, null, null, '2024-01-01', '9999-12-31', '06:00', '07:00', 'Y', 'ENG_WORD', 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (8, null, '영어일기',    '제목, 날씨, 내용',	'영어 3백자 이상', '300', 'DIARY', null, 'DAILY', null, 1000, 1, 'ENG_CNT', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values ( 9, null, '예의바른 SH', null, '아침인사, 마중인사, 반말안하기 등등의 예의바른 모습 유지하기', null, 'ETIQUET', null, 'DAILY', null, 1000, 1, 'CHECKER', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (10, null, '방청소', null, '미션 제안자가 확인' ,null, 'CLEAN', null, 'DAILY', null, 1000, 1, 'CHECKER', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (11, null, '운동', null, '춤, 산책, 자전거, 줄넘기, 달리기 등등 30분 단위로 인정', 30, 'EXERCISE', null, 'DIRECT', null, 500, 4, 'MERGE_TIME', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (12, null, '독서', null, '가사가 있는 노래, 동영상 등을 듣거나 시청하지 않아야 하며, 30분 단위로 인정', 30, 'READING', null, 'DIRECT', null, 500, 4, 'MERGE_TIME', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (13, null, '학습', null, '가사가 있는 노래, 동영상 등을 듣지 않아야 하며, 30분 단위로 인정', 30, 'LEARNING', null, 'DIRECT', null, 500, 4, 'MERGE_TIME', null, '2024-01-01', '9999-12-31' ,null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (14, null, '박서현의완벽한출석', null, '완벽한 출석 (수학, 영어, 플라톤) : 일주일학원출석', null, 'PRESENT', null, 'WEEK', 'SATURDAY', 3000, 1, 'CHECKER', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (15, null, '박수현의완벽한출석', null, '완벽한 출석 (수학, 영어, 플라톤, 키즈돌핀) : 일주일학원출석', null, 'PRESENT', null, 'WEEK', 'SATURDAY', 4000, 1, 'CHECKER', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (16, null, '모의고사/월말평가', null, '학원/학교에서 실시하는 모의고사 및 월말 평가, 중간기말 점수', null, 'EXAM', null, 'DIRECT', null, 3000, null, 'CHECKER', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (17, 16,   '100점 시험',    null, '학원/학교에서 실시하는 모의고사 및 월말 평가, 중간기말 점수', 100, 'EXAM', 1, null, null, 3000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (18, 16,   '90점 시험',     null, '학원/학교에서 실시하는 모의고사 및 월말 평가, 중간기말 점수', 90, 'EXAM', 2, null, null, 1000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (19, 16,   '80점 시험',     null, '학원/학교에서 실시하는 모의고사 및 월말 평가, 중간기말 점수', 80, 'EXAM', 3, null, null, 500, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (20, null, '중간고사/기말고사평균', null, '평균점수', null, 'EXAM', null, 'DIRECT', null, 100000, null, 'CHECKER', null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (21, 20,   '평균-100',     null, '평균점수 100' ,100, 'EXAM', 1, null, null, 100000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (22, 20,   '평균-99',      null, '평균점수 99' ,99, 'EXAM', 2, null, null, 50000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (23, 20,   '평균-98',      null, '평균점수 98' ,98, 'EXAM', 3, null, null, 30000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (24, 20,   '평균-97',      null, '평균점수 97' ,97, 'EXAM', 4, null, null, 20000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (25, 20,   '평균-96',      null, '평균점수 96' ,96, 'EXAM', 5, null, null, 15000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);
insert into td_todo (todo_seq, up_todo_seq, todo_titl, todo_tmp_cont, todo_desc, complet_condi_val, todo_typ_cd, aplyto_ord, todo_cre_cd, todo_cre_dtl_val, todo_point, date_limit_cnt, confm_meth_cd, confm_dtl_val, post_begin_date, post_end_date, limit_begin_tm, limit_end_tm, quiz_use_yn, quiz_typ_cd, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
values (26, 20,   '평균-95',      null, '평균점수 95' ,95, 'EXAM', 6, null, null, 10000, null, null, null, '2024-01-01', '9999-12-31', null, null, 'N', null, 'N', NOW(), 100, NOW(), 100);


INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(1, 102,  'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(5, 102,  'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(8, 102,  'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(9, 102,  'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(10, 102, 'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(11, 102, 'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(12, 102, 'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(13, 102, 'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(14, 102, 'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(15, 102, 'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(16, 102, 'N', 100, NOW(), 100, NOW());
INSERT INTO td_checker_map(todo_seq, checker_seq, del_yn, creator_seq, cre_dt, updator_seq, upd_dt)VALUES(20, 102, 'N', 100, NOW(), 100, NOW());


INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 1, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 5, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 8, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 9, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(10, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(11, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(12, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(13, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(14, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(16, 103, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(20, 103, 'Y', 'N', 100, NOW(), 100, NOW());


INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 1, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 5, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 8, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES( 9, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(10, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(11, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(12, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(13, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(15, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(16, 104, 'Y', 'N', 100, NOW(), 100, NOW());
INSERT INTO td_worker_map (todo_seq, worker_seq, worker_agre_yn, del_yn, creator_seq, cre_dt, updator_seq, upd_dt) VALUES(20, 104, 'Y', 'N', 100, NOW(), 100, NOW());