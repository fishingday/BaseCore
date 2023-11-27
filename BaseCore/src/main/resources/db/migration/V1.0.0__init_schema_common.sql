create sequence seq_cm_critical_log start with 1000 increment by 1;
create sequence seq_cm_menu start with 10000 increment by 1;
create sequence seq_cm_menu_dtl start with 1000 increment by 1;
create sequence seq_cm_noti_user_map start with 1000 increment by 1;
create sequence seq_cm_org start with 1000 increment by 1;
create sequence seq_cm_role start with 1000 increment by 1;
create sequence seq_cm_role_chg_log start with 1000 increment by 1;
create sequence seq_cm_user start with 1000 increment by 1;
create sequence seq_cm_user_acces_log start with 1000 increment by 1;
create sequence seq_cm_user_alow_ip start with 1000 increment by 1;
create table cm_cd_dtl (cd varchar(35) not null, grp_cd varchar(35) not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, cd_desc varchar(2000), cd_nm varchar(30) not null, del_yn varchar(1) not null, opt_1 varchar(256), opt_2 varchar(256), opt_3 varchar(256), opt_4 varchar(256), opt_5 varchar(256), prnt_ord integer not null, primary key (cd, grp_cd));
create table cm_cd_grp (grp_cd varchar(35) not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, grp_cd_desc varchar(2000), grp_cd_nm varchar(30) not null, primary key (grp_cd));
create table cm_critical_log (critical_log_seq bigint not null, http_status varchar(35), accept varchar(255), accept_charset varchar(255), accept_encoding varchar(255), accept_language varchar(255), cre_dt timestamp not null, log_makr_cd varchar(255) not null, param varchar(4000), referer varchar(255), req_ip varchar(20) not null, req_uri varchar(2000) not null, sess_id varchar(50), user_agent varchar(255), user_seq bigint, primary key (critical_log_seq));
create table cm_menu (menu_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, cm_scren_yn varchar(1) not null, del_yn varchar(1) not null, icon_info varchar(255), menu_desc varchar(2000), menu_nm varchar(30) not null, menu_path varchar(255), prnt_ord integer not null, prnt_yn varchar(1) not null, up_menu_seq bigint, primary key (menu_seq));
create table cm_menu_dtl (menu_dtl_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, menu_dtl_desc varchar(2000), menu_dtl_nm varchar(30) not null, menu_dtl_path varchar(255) not null, menu_seq bigint not null, primary key (menu_dtl_seq));
create table cm_menu_dtl_role_map (menu_dtl_seq bigint not null, role_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, primary key (menu_dtl_seq, role_seq));
create table cm_noti (noti_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, noti_titl varchar(200) not null, noti_cont varchar(2000) not null, noti_end_dt timestamp not null, noti_lnk_addr varchar(300), noti_send_dt timestamp not null, noti_send_grd_cd varchar(35) not null, send_media_typ_cd varchar(35) not null, send_yn varchar(1) not null, primary key (noti_seq));
create table cm_noti_user_map (noti_user_map_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, close_yn varchar(1) not null, del_yn varchar(1) not null, noti_seq bigint not null, qry_dt timestamp, send_media_typ_cd varchar(35) not null, user_seq bigint not null, view_cnt integer not null, primary key (noti_user_map_seq));
create table cm_org (org_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, org_desc varchar(2000), org_nm varchar(30) not null, prnt_ord integer not null, up_org_seq bigint, primary key (org_seq));
create table cm_org_user_map (org_seq bigint not null, user_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, primary key (org_seq, user_seq));
create table cm_role (role_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, def_page varchar(255), del_yn varchar(1) not null, role_desc varchar(2000), role_nm varchar(30) not null, primary key (role_seq));
create table cm_role_chg_log (role_chg_log_seq bigint not null, excutor_seq bigint not null, role_chg_cd varchar(35) not null, role_chg_cont varchar(2000) not null, role_chg_dt timestamp not null, role_chg_reason varchar(2000) not null, role_seq bigint not null, user_seq bigint not null, primary key (role_chg_log_seq));
create table cm_role_menu_map (menu_seq bigint not null, role_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, primary key (menu_seq, role_seq));
create table cm_user (user_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, acunt_exp_dt date not null, del_yn varchar(1) not null, last_login_ip varchar(23), login_dt timestamp, login_fail_cnt integer not null, login_id varchar(255) not null, user_nm varchar(30) not null, user_stat_cd varchar(35) not null, user_tel_no varchar(20), primary key (user_seq));
create table cm_user_acces_log (user_acces_log_seq bigint not null, accept varchar(255), accept_charset varchar(255), accept_encoding varchar(255), accept_language varchar(255), acces_log_typ_cd varchar(255), cre_dt timestamp, http_method varchar(255) not null, http_status varchar(35), param varchar(4000), referer varchar(255), req_ip varchar(20) not null, req_url varchar(255) not null, sess_id varchar(50), user_agent varchar(255), primary key (user_acces_log_seq));
create table cm_user_alow_ip (user_alow_ip_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, alow_ip varchar(30) not null, alow_ip_desc varchar(2000), del_yn varchar(1) not null, user_seq bigint not null, primary key (user_alow_ip_seq));
create table cm_user_bookmark (menu_seq bigint not null, role_seq bigint not null, user_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, book_mark_nm varchar(30) not null, del_yn varchar(1) not null, primary key (menu_seq, role_seq, user_seq));
create table cm_user_pwd (user_pwd_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, pwd_exp_dt date, user_pwd varchar(256) not null, user_seq bigint not null, primary key (user_pwd_seq));
create table cm_user_role_map (role_seq bigint not null, user_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, prnt_ord integer not null, primary key (role_seq, user_seq));
create table cm_user_relat (relator_seq bigint not null, targeter_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, upd_dt timestamp not null, updator_seq bigint not null, del_yn varchar(1) not null, user_relat_cd varchar(35) not null, user_relat_dtl_val varchar(20), relator_calnm varchar(30), targeter_calnm varchar(30), relat_aply_dt timestamp, targeter_agre_yn varchar(1) not null, targeter_agre_dt timestamp, primary key (relator_seq, targeter_seq));


alter table cm_user add constraint UK_k3f18xcjet2p2id7v534udwsg unique (login_id);
alter table cm_cd_dtl add constraint FKexu40blbaqa1ui4wj5t7fet70 foreign key (grp_cd) references cm_cd_grp;
alter table cm_menu_dtl add constraint FKopi00ys66fdc50k0apmvwdyrw foreign key (menu_seq) references cm_menu;
alter table cm_menu_dtl_role_map add constraint FKmj7knsqb5kprqymf9c3mp7j3b foreign key (menu_dtl_seq) references cm_menu_dtl;
alter table cm_menu_dtl_role_map add constraint FKm698ihqh4xl8h3dw418yb4spt foreign key (role_seq) references cm_role;
alter table cm_noti_user_map add constraint FKsf9l4o07nh3vto1cs90bcd490 foreign key (noti_seq) references cm_noti;
alter table cm_noti_user_map add constraint FKdukpiuojj4icww9ihl5y1si9i foreign key (user_seq) references cm_user;
alter table cm_org_user_map add constraint FKo4fe0jo9v7vnu0dw7dkpy7cpa foreign key (org_seq) references cm_org;
alter table cm_org_user_map add constraint FKqdyuotyifkbx9y0nss9uh2und foreign key (user_seq) references cm_user;
alter table cm_role_menu_map add constraint FK4qrmdqf0t8jybddrmdbds7irj foreign key (menu_seq) references cm_menu;
alter table cm_role_menu_map add constraint FKgkf11p90nko1o74ng9qmk5nji foreign key (role_seq) references cm_role;
alter table cm_user_alow_ip add constraint FKs9aiautlb90y1rqm38clrc5ih foreign key (user_seq) references cm_user;
alter table cm_user_bookmark add constraint FKnfo699ybwb41erscsix773mw5 foreign key (menu_seq) references cm_menu;
alter table cm_user_bookmark add constraint FKqr4pbcdrynxrx3v3m3qw779xs foreign key (role_seq) references cm_role;
alter table cm_user_bookmark add constraint FKacck0ttob0cdiwbti3hq1axyc foreign key (user_seq) references cm_user;
alter table cm_user_pwd add constraint FKm9116j3ixgqvm8l93b3m7ej0x foreign key (user_seq) references cm_user;
alter table cm_user_role_map add constraint FKhvlbfv98praqwf9bbbtrbsygi foreign key (role_seq) references cm_role;
alter table cm_user_role_map add constraint FK4mgsb5kifk6x8enwi10fnyf2t foreign key (user_seq) references cm_user;