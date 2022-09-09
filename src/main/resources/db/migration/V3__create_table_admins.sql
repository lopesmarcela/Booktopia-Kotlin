create table admins (
id serial not null,
cpf varchar(14) not null,
name varchar(100) not null,
email varchar(100) not null,
password varchar(255) not null,
status varchar(100) not null,
primary key (id)
)