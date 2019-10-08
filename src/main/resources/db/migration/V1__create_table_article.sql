create table article (
    barcode varchar(255) not null,
    name varchar(255) not null,
    price numeric(19, 2) not null,
    image varchar(255),
    primary key (barcode)
);

insert into article(barcode, name, price, image) values('12345678', 'laptop', 750, 'https://urlz.fr/aJfb');
insert into article(barcode, name, price, image) values('12345687', 'robot vacuum', 450, 'https://urlz.fr/aJfe');
insert into article(barcode, name, price, image) values('12435678', 'ping pong table', 400, 'https://urlz.fr/aJfg');