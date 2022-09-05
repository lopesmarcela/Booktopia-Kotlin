create table rents (
id serial not null,
fine decimal(8,2),
total_value decimal(8,2),
rental_date varchar(10) not null,
return_date varchar(10),
status varchar(100) not null,
client_id int not null,
book_id int not null,
primary key (id),
constraint fk_client foreign key (client_id) references clients(id)
)