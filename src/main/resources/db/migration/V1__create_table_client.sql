create table clients (
id_client serial not null,
address varchar(255),
cpf varchar(255) unique,
email varchar(255) unique,
name varchar(255),
primary key (id_client)
)