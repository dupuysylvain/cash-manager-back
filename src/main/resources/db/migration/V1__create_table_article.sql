create table article (
    id serial PRIMARY KEY,
    barcode varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    price numeric(19, 2) NOT NULL,
    quantity int NOT NULL,
    image varchar(255)
);

insert into article(barcode, name, price, quantity, image) values('22324424', 'Coca 33cl', 1, 1000, 'https://www.sushimarket35.com/153-large_default/a1-coca-light-zero.jpg');
insert into article(barcode, name, price, quantity, image) values('12345678', 'laptop', 750, 10, 'https://zdnet1.cbsistatic.com/hub/i/r/2019/04/17/1f68c3a6-495e-4325-bc16-cc531812f0ec/thumbnail/770x433/84ff4194826e8303efb771cd377a854f/chuwi-herobook-header.jpg');
insert into article(barcode, name, price, quantity, image) values('12345687', 'robot vacuum', 450, 12, 'https://mobileimages.lowes.com/product/converted/885155/885155013194.jpg?size=xl');
insert into article(barcode, name, price, quantity, image) values('12435678', 'ping pong table', 400, 5, 'https://fr.cornilleau.com/725-xlarge_default/sport-500-table-de-ping-pong-interieure.jpg');