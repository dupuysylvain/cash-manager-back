CREATE TABLE bank (
  id serial PRIMARY KEY,
  name varchar (255) NOT NULL
);

INSERT INTO bank(id, name) VALUES(1, 'BNP Paribas');
INSERT INTO bank(id, name) VALUES(2, 'LCL');
INSERT INTO bank(id, name) VALUES(3, 'Societe general');
INSERT INTO bank(id, name) VALUES(4, 'Boursorama');