create table rent_book (
    rent_id int not null,
    book_id int not null,
    foreign key (rent_id) references rents(id),
    foreign key (book_id) references books(id),
    primary key (rent_id,book_id)
)