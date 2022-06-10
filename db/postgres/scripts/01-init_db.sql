-- create user
create user rd_user;

-- create database
create database rd_app;

-- change password
alter user rd_user with password 'rd_user';

-- grant access rights of the created user to the database
grant all privileges on database rd_app to rd_user;