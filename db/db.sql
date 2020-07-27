create database mapping;

create table if not exists enginesxml
(
  id   serial primary key,
  type varchar(30) not null
);

create table if not exists carsxml
(
  id        serial primary key,
  brand     varchar(20) not null,
  model     varchar(20) not null,
  engine_id integer     not null,
  foreign key (engine_id) references enginesxml (id)
);

create table if not exists driversxml
(
  id     serial primary key,
  name   varchar(20) not null,
  car_id integer     not null,
  foreign key (car_id) references carsxml (id)
);

create table if not exists history_owner
(
  id        serial primary key,
  driver_id integer not null references driversxml (id),
  car_id    integer not null references carsxml (id)
);