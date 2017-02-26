create database cataloguer;
use cataloguer;

create table roles(
	id_role int not null,
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
    primary key(id_user)
); 