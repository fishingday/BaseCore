drop table account if exists;

drop table resources if exists;

drop table role if exists;

drop table role_resources if exists;

drop table user_roles if exists;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 1 increment by 1;

create table account (
   id bigint not null,
	age integer,
	email varchar(255),
	password varchar(255),
	username varchar(255),
	primary key (id)
);

create table resources (
   resource_id bigint not null,
	http_method varchar(255),
	order_num integer,
	resource_name varchar(255),
	resource_type varchar(255),
	primary key (resource_id)
);

create table role (
   role_id bigint not null,
	role_desc varchar(255),
	role_name varchar(255),
	primary key (role_id)
);

create table role_resources (
   resource_id bigint not null,
	role_id bigint not null,
	primary key (resource_id, role_id)
);

create table user_roles (
   user_id bigint not null,
	role_id bigint not null,
	primary key (user_id, role_id)
);

alter table role_resources 
   add constraint FK7k960kk6pu1pwsk7ml4hycp53 
   foreign key (role_id) 
   references role;
   
alter table role_resources 
   add constraint FK8k22y1jmevnedy4v80tl1yop7 
   foreign key (resource_id) 
   references resources;
   
alter table user_roles 
   add constraint FKrhfovtciq1l558cw6udg0h0d3 
   foreign key (role_id) 
   references role;
   
alter table user_roles 
   add constraint FKn6rghlrxo1uta1ffpj0puglmp 
   foreign key (user_id) 
   references account;