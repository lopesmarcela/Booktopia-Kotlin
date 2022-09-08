create table books (
id serial not null,
title varchar(100) not null,
description varchar(255) not null,
release_date date not null,
category varchar(100) not null,
author varchar(100) not null,
price decimal(8,2) not null,
status varchar(100) not null,
inventory int not null,
primary key (id)
)