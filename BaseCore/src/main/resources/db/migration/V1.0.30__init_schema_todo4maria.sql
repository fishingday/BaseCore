
create table td_checker_map (
    todo_seq bigint not null,
    checker_seq bigint not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (checker_seq, todo_seq)
) engine=InnoDB;

create table td_quiz (
    quiz_seq bigint not null,
    quiz_typ_cd enum ('ENG_WORD','ENG_IDOIOM','ENG_SENTENCE','MATH') not null,
    quiz_titl varchar(200) not null,
    quiz_cont varchar(2000) not null,
    quiz_answer varchar(128) not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (quiz_seq)
) engine=InnoDB;

create table td_quiz_work_use (
    quiz_seq bigint not null,
    user_seq bigint not null,
    work_seq bigint not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (quiz_seq, work_seq)
) engine=InnoDB;

create table td_setle (
    setle_seq bigint not null,
    acount_seq bigint not null,
    worker_seq bigint not null,
    setle_dt datetime(6),
    accumult_point integer not null,
    total_setle_point integer not null,
    setle_desc varchar(255),
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (setle_seq)
) engine=InnoDB;

create table td_todo (
    todo_seq bigint not null,
    up_todo_seq bigint,    
    todo_titl varchar(200) not null,
    todo_tmp_cont varchar(2000) not null,
    todo_desc varchar(2000),
    complet_condi_val varchar(128) not null,
    todo_typ_cd enum ('WAKEUP','SLEEP','CLEAN','DIARY','EXERCISE','READING','EXAM','ETIQUET','PRESENT') not null,
    aplyto_ord integer, 
    
    todo_cre_cd enum ('DAILY','WEEK','MONTH','DIRECT') not null,
    todo_cre_dtl_val varchar(128),
    date_limit_cnt integer not null,
    todo_point integer not null,
    confm_meth_cd enum ('SCORE','ENG_CNT','WORD_CNT','INTIME','EYE_CHECK','CHECKER') not null,
    confm_dtl_val varchar(20),
        
    post_begin_date date not null,
    post_end_date date not null,
    limit_begin_tm time(6),
    limit_end_tm time(6),
    quiz_use_yn VARCHAR(1) not null,
    quiz_typ_cd enum ('ENG_WORD','ENG_IDOIOM','ENG_SENTENCE','MATH'),
    
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (todo_seq)
) engine=InnoDB;

create table td_work (
    work_seq bigint not null,
    todo_seq bigint not null,
    up_work_seq bigint,
    worker_seq bigint not null,
    work_titl varchar(200),
    work_cont varchar(4000),
    work_dt datetime(6),
    file_grp_seq bigint,
    work_begin_dt datetime(6),
    work_end_dt datetime(6),
    work_rslt_val varchar(128),
    work_stat_cd enum ('READY','ONGOING','LACK','FAIL','DONE') not null,
    
    work_poss_begin_dt datetime(6) not null,
    work_poss_end_dt datetime(6) not null,
    
    checker_seq bigint,
    confm_dt datetime(6),
    confm_cont varchar(2000),
    gain_point integer,
    
    setle_yn VARCHAR(1) not null,
    setle_seq bigint,
    
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (work_seq)
) engine=InnoDB;

create table td_worker_map (
    todo_seq bigint not null,
    worker_seq bigint not null,
    worker_agre_yn VARCHAR(1) not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (todo_seq, worker_seq)
) engine=InnoDB;

create table td_point (
    point_seq bigint not null,
    setle_seq bigint,
    user_seq bigint not null,    
    aplyto_point integer not null,
    last_point integer not null,
    point_cont varchar(2000),
    point_aplyto_cd enum ('USE','ADD') not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (point_seq)
) engine=InnoDB;
   
alter table if exists td_checker_map 
   add constraint FK_td_checker_map
   foreign key (todo_seq) 
   references td_todo (todo_seq);
   
alter table if exists td_quiz_work_use
   add constraint FK_td_quiz_work_use1
   foreign key (quiz_seq) 
   references td_quiz (quiz_seq);
   
alter table if exists td_quiz_work_use 
   add constraint FK_td_quiz_work_use2
   foreign key (work_seq) 
   references td_work (work_seq);
   
alter table if exists td_work 
   add constraint FK_td_work1
   foreign key (setle_seq) 
   references td_setle (setle_seq);
   
alter table if exists td_work 
   add constraint FK_td_work2
   foreign key (todo_seq) 
   references td_todo (todo_seq);
   
alter table if exists td_worker_map 
   add constraint FK_td_worker_map
   foreign key (todo_seq) 
   references td_todo (todo_seq);