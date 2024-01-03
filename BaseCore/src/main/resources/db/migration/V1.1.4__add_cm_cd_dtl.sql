delete from cm_cd_dtl where GRP_CD = 'TODO_TYP_CD' and CD = 'SOME';
 
insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
values ('TODO_TYP_CD', 'ETIQUET', '예절', '예절', 8, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
values ('TODO_TYP_CD', 'LEARNING', '학습', '학습', 9, 'N', NOW(), 100, NOW(), 100);

insert into cm_cd_dtl(GRP_CD, CD, CD_NM, CD_DESC, PRNT_ORD, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ)
values ('TODO_TYP_CD', 'PRESENT', '출석', '출석', 10, 'N', NOW(), 100, NOW(), 100);