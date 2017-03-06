use cataloguer;
drop table names;
drop table users_activity;
drop table users;
drop table roles;
drop table music;
drop table movies;
drop table books;
drop table documents;

create table roles(
  id_role int not null auto_increment,
  role varchar(20) not null,
  primary key(id_role)
);

create table users(
  id_user int not null auto_increment,
  login varchar(40) not null,
  password varchar(40) not null,
  id_role int not null,
  primary key(id_user),
  foreign key(id_role) references roles(id_role)
);

create table names(
  id_user int not null,
  name varchar(50),
  foreign key (id_user) references users(id_user)
);

create table music(
  id int not null auto_increment primary key,
  name varchar(100) not null,
  resource longblob
);

create table movies(
  id int not null auto_increment primary key,
  name varchar(100) not null,
  resource longblob
);

create table books(
  id int not null auto_increment primary key,
  name varchar(100) not null,
  resource longblob
);

create table documents(
  id int not null auto_increment primary key,
  name varchar(100) not null,
  resource longblob
);

create table users_activity(
  id_user int not null,
  last_update date,
  traffic int,
  foreign key(id_user) references users(id_user)
);


create table emails(
  id_user int not null,
  email varchar(60) not null,
  foreign key(id_user) references users(id_user)
);