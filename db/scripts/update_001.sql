drop table if exists engine;
drop table if exists car;
drop table if exists owner;
drop table if exists history_owner;

create table engine (
    id          serial primary key,
    name        varchar(200) not null
);

insert into engine values
    (1, 'Carburetor'),
    (2, 'Diesel'),
    (3, 'Electric');

create table car (
    id          serial primary key,
    name        varchar(200) not null,
    engine_id   int not null references engine(id)
);

create table owner (
    id          serial primary key,
    name        varchar(200) not null
);

create table history_owner (
    id          serial primary key,
    car_id      int not null references car(id),
    owner_id    int not null references owner(id)
);