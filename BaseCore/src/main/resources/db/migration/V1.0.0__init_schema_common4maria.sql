create table cm_cd_dtl (
    cd varchar(35) not null,
    grp_cd varchar(35) not null,
    cd_nm varchar(30) not null,
    cd_desc varchar(2000),
    prnt_ord integer not null,
    opt_1 varchar(256),
    opt_2 varchar(256),
    opt_3 varchar(256),
    opt_4 varchar(256),
    opt_5 varchar(256),
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (cd, grp_cd)
) engine=InnoDB;

create table cm_cd_grp (
    grp_cd varchar(35) not null,
    grp_cd_nm varchar(30) not null,
    grp_cd_desc varchar(2000),
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (grp_cd)
) engine=InnoDB;

create table cm_menu (
    menu_seq bigint not null,
    up_menu_seq bigint,
    prnt_ord integer not null,
    menu_nm varchar(30) not null,
    menu_desc varchar(2000),
    icon_info varchar(255),
    menu_path varchar(255),
    cm_scren_yn VARCHAR(1) not null,
    prnt_yn VARCHAR(1) not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (menu_seq)
) engine=InnoDB;

create table cm_menu_dtl (
    menu_dtl_seq bigint not null,
    menu_seq bigint not null,
    menu_dtl_nm varchar(30) not null,
    menu_dtl_desc varchar(2000),
    menu_dtl_path varchar(255) not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (menu_dtl_seq)
) engine=InnoDB;

create table cm_menu_dtl_role_map (
    menu_dtl_seq bigint not null,
    role_seq bigint not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (menu_dtl_seq, role_seq)
) engine=InnoDB;

create table cm_noti (
    noti_seq bigint not null,
    noti_titl varchar(200) not null,
    noti_cont varchar(2000) not null,
    noti_lnk_addr varchar(300),
    noti_end_dt datetime(6) not null,
    noti_send_dt datetime(6) not null,
    noti_send_grd_cd enum ('INFO','IMPORTANT','EMERGENCY') not null,
    send_media_typ_cd enum ('EMAIL','NOTI','SMS') not null,
    send_yn VARCHAR(1) not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (noti_seq)
) engine=InnoDB;

create table cm_noti_user_map (
    noti_user_map_seq bigint not null,
    noti_seq bigint not null,
    user_seq bigint not null,
    send_media_typ_cd enum ('EMAIL','NOTI','SMS') not null,
    qry_dt datetime(6),
    view_cnt integer not null,
    close_yn VARCHAR(1) not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (noti_user_map_seq)
) engine=InnoDB;

create table cm_role (
    role_seq bigint not null,
    role_nm varchar(30) not null,
    role_desc varchar(2000),
    def_page varchar(255),
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (role_seq)
) engine=InnoDB;

create table cm_role_chg_log (
    role_chg_log_seq bigint not null,
    role_seq bigint not null,
    user_seq bigint not null,
    role_chg_cd enum ('ADD','REMOVE','UPDATE') not null,
    role_chg_cont varchar(2000) not null,
    role_chg_reason varchar(2000) not null,
    excutor_seq bigint not null,
    role_chg_dt datetime(6) not null,
    primary key (role_chg_log_seq)
) engine=InnoDB;

create table cm_role_menu_map (
    menu_seq bigint not null,
    role_seq bigint not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (menu_seq, role_seq)
) engine=InnoDB;

create table cm_user (
    user_seq bigint not null,
    login_id varchar(255) not null,
    user_nm varchar(30) not null,
    user_tel_no varchar(20),
    acunt_exp_dt date not null,
    login_fail_cnt integer not null,
    login_dt datetime(6),
    last_login_ip varchar(23),
    user_stat_cd enum ('ENABLED','LOCKED','DISABLED') not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (user_seq)
) engine=InnoDB;

create table cm_user_alow_ip (
    user_alow_ip_seq bigint not null,
    user_seq bigint not null,
    alow_ip varchar(30) not null,
    alow_ip_desc varchar(2000),
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (user_alow_ip_seq)
) engine=InnoDB;

create table cm_user_pwd (
    user_pwd_seq bigint not null,
    user_seq bigint not null,
    user_pwd varchar(256) not null,
    pwd_exp_dt date,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (user_pwd_seq)
) engine=InnoDB;

create table cm_user_relat (
    targeter_seq bigint not null,
    relator_seq bigint not null,
    user_relat_cd enum ('GRAND','PARENT','RELAT','CHILD','SELF') not null,
    user_relat_dtl_val varchar(20) not null,
    relator_calnm varchar(30),
    targeter_calnm varchar(30),
    relat_aply_dt datetime(6),
    targeter_agre_dt datetime(6),
    targeter_agre_yn VARCHAR(1) not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (relator_seq, targeter_seq)
) engine=InnoDB;

create table cm_user_role_map (
    role_seq bigint not null,
    user_seq bigint not null,
    prnt_ord integer not null,
    del_yn VARCHAR(1) not null,
    creator_seq bigint not null,
    cre_dt datetime(6) not null,
    updator_seq bigint not null,
    upd_dt datetime(6) not null,
    primary key (role_seq, user_seq)
) engine=InnoDB;

alter table if exists cm_user 
   add constraint UK_cm_user unique (login_id);
   
alter table if exists cm_cd_dtl 
   add constraint FK_cm_cd_dtl
   foreign key (grp_cd) 
   references cm_cd_grp (grp_cd);
   
alter table if exists cm_menu_dtl 
   add constraint FK_cm_menu_dtl
   foreign key (menu_seq) 
   references cm_menu (menu_seq);
   
alter table if exists cm_menu_dtl_role_map 
   add constraint FK_cm_menu_dtl_role_map1
   foreign key (menu_dtl_seq) 
   references cm_menu_dtl (menu_dtl_seq);
   
alter table if exists cm_menu_dtl_role_map 
   add constraint FK_cm_menu_dtl_role_map2
   foreign key (role_seq) 
   references cm_role (role_seq);
   
alter table if exists cm_noti_user_map 
   add constraint FK_cm_noti_user_map1
   foreign key (noti_seq) 
   references cm_noti (noti_seq);
   
alter table if exists cm_noti_user_map 
   add constraint FK_cm_noti_user_map2
   foreign key (user_seq) 
   references cm_user (user_seq);
   
alter table if exists cm_role_menu_map 
   add constraint FK_cm_role_menu_map1
   foreign key (menu_seq) 
   references cm_menu (menu_seq);
   
alter table if exists cm_role_menu_map
   add constraint FK_cm_role_menu_map2
   foreign key (role_seq) 
   references cm_role (role_seq);
   
alter table if exists cm_user_alow_ip 
   add constraint FK_cm_user_alow_ip
   foreign key (user_seq) 
   references cm_user (user_seq);
   
alter table if exists cm_user_pwd 
   add constraint FK_cm_user_pwd
   foreign key (user_seq) 
   references cm_user (user_seq);
   
alter table if exists cm_user_role_map 
   add constraint FK_cm_user_role_map1
   foreign key (role_seq) 
   references cm_role (role_seq);
   
alter table if exists cm_user_role_map 
   add constraint FK_cm_user_role_map2
   foreign key (user_seq) 
   references cm_user (user_seq);