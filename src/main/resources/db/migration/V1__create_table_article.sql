create table article (
    id serial PRIMARY KEY,
    barcode varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    price numeric(19, 2) NOT NULL,
    quantity int NOT NULL,
    image varchar(255)
);

insert into article(barcode, name, price, quantity, image) values('22324424', 'Coca 33cl', 1, 1000, 'https://urlz.fr/b7py');
insert into article(barcode, name, price, quantity, image) values('12345678', 'laptop', 750, 10, 'https://urlz.fr/aJfb');
insert into article(barcode, name, price, quantity, image) values('12345687', 'robot vacuum', 450, 12, 'https://urlz.fr/aJfe');
insert into article(barcode, name, price, quantity, image) values('12435678', 'ping pong table', 400, 5, 'https://urlz.fr/aJfg');