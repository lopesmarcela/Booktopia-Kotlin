create table clients (
    id serial not null,
    cpf varchar(14) not null,
    name varchar(100) not null,
    email varchar(100) not null,
    address_id int not null,
    status varchar(100) not null,
    primary key (id),
    foreign key (address_id) references adresses(id)
)