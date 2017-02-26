insert into roles(id_role, role) values
(1, "ADMIN"),
(2, "DEFAULT");

insert into users(login, password, id_role) values
("gleb.streltsov@gmail.com", "44447777", 1),
("vi@gmail.com", "4477", 2);

insert into names(id_user, name) values
(1, "Стрельцов Глеб"),
(2, "Санько Вероника");