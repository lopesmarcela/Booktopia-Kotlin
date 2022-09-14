create table admins (
id serial not null,
cpf varchar(14) not null,
name varchar(100) not null,
email varchar(100) not null,
password varchar(255) not null,
status varchar(100) not null,
primary key (id)
);

create table admin_roles (
    admin_id int not null,
    role varchar(50) not null,
    foreign key (admin_id) references admins(id)
);