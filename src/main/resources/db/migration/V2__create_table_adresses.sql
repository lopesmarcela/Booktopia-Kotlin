create table adresses (
    id serial not null,
    street varchar(100) not null,
    number int not null,
    district varchar(100) not null,
    city varchar(100) not null,
    cep varchar(9) not null,
    status varchar(100) not null,
primary key (id)
)