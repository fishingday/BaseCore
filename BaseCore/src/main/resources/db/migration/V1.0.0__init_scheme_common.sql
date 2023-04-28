create sequence hibernate_sequence start with 1 increment by 1;
create sequence seq_cm_menu start with 1000 increment by 1;
create sequence seq_cm_menu_dtl start with 1000 increment by 1;
create sequence seq_cm_noti_user_map start with 1000 increment by 1;
create sequence seq_cm_role start with 1000 increment by 1;
create sequence seq_cm_role_chg_log start with 1000 increment by 1;
create sequence seq_cm_user start with 1000 increment by 1;
create sequence seq_cm_user_acces_log start with 1000 increment by 1;
create sequence seq_cm_user_alow_ip start with 1000 increment by 1;
create table account (id bigint not null, age integer, email varchar(255), password varchar(255), username varchar(255), primary key (id));
create table cm_cd_dtl (cd varchar(35) not null, grp_cd varchar(35) not null, cd_nm varchar(30) not null, cd_desc varchar(2000), cre_dt timestamp not null, creator_seq bigint not null, del_yn varchar(1) not null, opt_1 varchar(256), opt_2 varchar(256), opt_3 varchar(256), opt_4 varchar(256), opt_5 varchar(256), prnt_ord integer not null, upd_dt timestamp not null, updator_seq bigint not null, primary key (cd, grp_cd));
create table cm_grp_cd (grp_cd varchar(35) not null, cre_dt timestamp not null, creator_seq bigint not null, del_yn varchar(1) not null, grp_cd_desc varchar(2000), grp_cd_nm varchar(30) not null, upd_dt timestamp not null, updator_seq bigint not null, primary key (grp_cd));
create table cm_menu (menu_seq bigint not null, cm_scren_yn varchar(1) not null, cre_dt timestamp, creator_seq bigint, del_yn varchar(1) not null, icon_url varchar(255), menu_desc varchar(2000), menu_nm varchar(30) not null, menu_path varchar(255), prnt_ord integer not null, prnt_yn varchar(1) not null, up_menu_seq bigint, upd_dt timestamp, updator_seq bigint, primary key (menu_seq));
create table cm_menu_dtl (menu_dtl_seq bigint not null, cre_dt timestamp, creator_seq bigint, del_yn varchar(1) not null, menu_dtl_desc varchar(2000), menu_dtl_nm varchar(30) not null, menu_dtl_path varchar(255) not null, menu_seq bigint not null, upd_dt timestamp, updator_seq bigint, primary key (menu_dtl_seq));
create table cm_menu_dtl_role_map (menu_dtl_seq bigint not null, role_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, del_yn varchar(1) not null, upd_dt timestamp not null, updator_seq bigint not null, primary key (menu_dtl_seq, role_seq));
create table cm_noti (noti_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, del_yn varchar(1) not null, noti_titl varchar(200) not null, noti_cont varchar(2000) not null, noti_end_dt timestamp not null, noti_lnk_addr varchar(300), noti_send_dt timestamp not null, noti_send_grd_cd varchar(35) not null, send_media_typ_cd varchar(35) not null, send_yn varchar(1) not null, upd_dt timestamp not null, updator_seq bigint not null, primary key (noti_seq));
create table cm_noti_user_map (noti_user_map_seq bigint not null, close_yn varchar(1) not null, cre_dt timestamp not null, creator_seq bigint not null, del_yn varchar(1) not null, noti_seq bigint not null, qry_dt timestamp, send_media_typ_cd varchar(35) not null, upd_dt timestamp not null, updator_seq bigint not null, user_seq bigint not null, view_cnt integer not null, primary key (noti_user_map_seq));
create table cm_role (role_seq bigint not null, cre_dt timestamp not null, creator_seq bigint not null, del_yn varchar(1) not null, role_cd varchar(35) not null, role_desc varchar(2000), role_nm varchar(30), upd_dt timestamp not null, updator_seq bigint not null, primary key (role_seq));
create table cm_role_chg_log (role_chg_log_seq bigint not null, excutor_seq bigint not null, role_chg_cd varchar(35) not null, role_chg_cont varchar(2000) not null, role_chg_dt timestamp not null, role_chg_reason varchar(2000) not null, role_seq bigint not null, user_seq bigint not null, primary key (role_chg_log_seq));
create table cm_role_menu_map (menu_seq bigint not null, role_seq bigint not null, cre_dt timestamp, creator_seq bigint, del_yn varchar(1) not null, upd_dt timestamp not null, updator_seq bigint not null, primary key (menu_seq, role_seq));
create table cm_user (user_seq bigint not null, acunt_exp_dt date not null, cre_dt timestamp not null, creator_seq bigint not null, del_yn varchar(1) not null, last_login_ip varchar(23), login_dt timestamp, login_fail_cnt integer not null, login_id varchar(255) not null, upd_dt timestamp not null, updator_seq bigint not null, user_nm varchar(30) not null, user_stat_cd varchar(35) not null, user_tel_no varchar(20), primary key (user_seq));
create table cm_user_acces_log (user_acces_log_seq bigint not null, accept varchar(255), accept_charset varchar(255), acces_log_typ_cd varchar(255), cre_dt timestamp, http_method_cd varchar(255) not null, http_stat_no varchar(20), referer varchar(255), req_cont varchar(2000), req_ip varchar(20) not null, req_url varchar(255) not null, sess_id varchar(50), user_agent varchar(255), primary key (user_acces_log_seq));
create table cm_user_alow_ip (user_alow_ip_seq bigint not null, alow_ip varchar(30) not null, alow_ip_desc varchar(2000), cre_dt timestamp, creator_seq bigint, del_yn varchar(1) not null, upd_dt timestamp, updator_seq bigint, user_seq bigint not null, primary key (user_alow_ip_seq));
create table cm_user_bookmark (menu_seq bigint not null, role_seq bigint not null, user_seq bigint not null, book_mark_nm varchar(30) not null, cre_dt timestamp, creator_seq bigint, del_yn varchar(1) not null, upd_dt timestamp, updator_seq bigint, primary key (menu_seq, role_seq, user_seq));
create table cm_user_pwd (user_pwd_seq bigint not null, cre_dt timestamp, creator_seq bigint, del_yn varchar(1) not null, pwd_exp_dt date, upd_dt timestamp, updator_seq bigint, user_pwd varchar(256) not null, user_seq bigint not null, primary key (user_pwd_seq));
create table cm_user_role_map (role_seq bigint not null, user_seq bigint not null, cre_dt timestamp, creator_seq bigint, del_yn varchar(1) not null, upd_dt timestamp, updator_seq bigint, primary key (role_seq, user_seq));
create table resources (resource_id bigint not null, http_method varchar(255), order_num integer, resource_name varchar(255), resource_type varchar(255), primary key (resource_id));
create table role (role_id bigint not null, role_desc varchar(255), role_name varchar(255), primary key (role_id));
create table role_resources (resource_id bigint not null, role_id bigint not null, primary key (resource_id, role_id));
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id));
alter table cm_role add constraint UK_2j7vpbi7ekt471wc03y5kbs5f unique (role_cd);
alter table cm_user add constraint UK_k3f18xcjet2p2id7v534udwsg unique (login_id);
alter table cm_cd_dtl add constraint FKbjefm6avhx0q92i0heapgfk4m foreign key (grp_cd) references cm_grp_cd;
alter table cm_menu_dtl add constraint FKopi00ys66fdc50k0apmvwdyrw foreign key (menu_seq) references cm_menu;
alter table cm_menu_dtl_role_map add constraint FKmj7knsqb5kprqymf9c3mp7j3b foreign key (menu_dtl_seq) references cm_menu_dtl;
alter table cm_menu_dtl_role_map add constraint FKm698ihqh4xl8h3dw418yb4spt foreign key (role_seq) references cm_role;
alter table cm_noti_user_map add constraint FKsf9l4o07nh3vto1cs90bcd490 foreign key (noti_seq) references cm_noti;
alter table cm_noti_user_map add constraint FKdukpiuojj4icww9ihl5y1si9i foreign key (user_seq) references cm_user;
alter table cm_role_menu_map add constraint FK4qrmdqf0t8jybddrmdbds7irj foreign key (menu_seq) references cm_menu;
alter table cm_role_menu_map add constraint FKgkf11p90nko1o74ng9qmk5nji foreign key (role_seq) references cm_role;
alter table cm_user_alow_ip add constraint FKs9aiautlb90y1rqm38clrc5ih foreign key (user_seq) references cm_user;
alter table cm_user_bookmark add constraint FKnfo699ybwb41erscsix773mw5 foreign key (menu_seq) references cm_menu;
alter table cm_user_bookmark add constraint FKqr4pbcdrynxrx3v3m3qw779xs foreign key (role_seq) references cm_role;
alter table cm_user_bookmark add constraint FKacck0ttob0cdiwbti3hq1axyc foreign key (user_seq) references cm_user;
alter table cm_user_pwd add constraint FKm9116j3ixgqvm8l93b3m7ej0x foreign key (user_seq) references cm_user;
alter table cm_user_role_map add constraint FKhvlbfv98praqwf9bbbtrbsygi foreign key (role_seq) references cm_role;
alter table cm_user_role_map add constraint FK4mgsb5kifk6x8enwi10fnyf2t foreign key (user_seq) references cm_user;
alter table role_resources add constraint FK7k960kk6pu1pwsk7ml4hycp53 foreign key (role_id) references role;
alter table role_resources add constraint FK8k22y1jmevnedy4v80tl1yop7 foreign key (resource_id) references resources;
alter table user_roles add constraint FKrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role;
alter table user_roles add constraint FKn6rghlrxo1uta1ffpj0puglmp foreign key (user_id) references account;
