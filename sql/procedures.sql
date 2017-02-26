drop procedure get_user;
delimiter //
create procedure get_user(IN _login varchar(40), IN _password varchar(40),
 OUT _id_user int, OUT _role varchar(20), OUT _name varchar(50))
 comment "returns user according login and password and then returns its id_user and role"
 begin
	select id_user into _id_user from users where login = _login and password = _password;
    select id_role into @role from users where id_user = _id_user;
    select role into _role from roles where id_role = @role;
    select name into _name from names where id_user = _id_user;
 end
//