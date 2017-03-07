insert into roles(id_role, role) values
(1, "ADMIN"),
(2, "DEFAULT");

insert into users(login, password, id_role) values
("gleb.streltsov@gmail.com", "44447777", 1),
("vi@gmail.com", "4477", 2);

insert into names(id_user, name) values
(1, "Gleb Streltsov"),
(2, "Veronika Sanko");

insert into users_activity(id_user) values
(1),
(2);

insert into emails(id_user, email) values
(1, "gleb.streltsov.4by@gmail.com"),
(2, "vi@gmail.com");