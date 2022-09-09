create table admin_roles (
    admin_id int not null,
    role varchar(50) not null,
    foreign key (admin_id) references admins(id)
)