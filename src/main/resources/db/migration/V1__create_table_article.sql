create table article (
    barcode varchar(255) not null,
    name varchar(255) not null,
    price numeric(19, 2) not null,
    primary key (barcode)
);

insert into article(barcode, name, price) values('12345678', 'laptop', 750);
insert into article(barcode, name, price) values('12345687', 'robot vacuum', 450);
insert into article(barcode, name, price) values('12435678', 'ping pong table', 400);