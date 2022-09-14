create table rents (
    id serial not null,
    fine decimal (8,2),
    totalValue decimal (8,2),
    rentalDate date not null,
    returnDate date,
    status varchar(100) not null,
    client_id int not null,
    book_id int not null
)