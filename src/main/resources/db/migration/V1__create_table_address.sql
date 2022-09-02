create table adresses (
id serial not null,
street varchar(255) not null,
number int,
district varchar(255) not null,
city varchar(255) not null,
cep varchar(9) not null,
primary key (id)
)