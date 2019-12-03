CREATE TABLE account (
  id serial PRIMARY KEY,
  clientName varchar(255) NOT NULL,
  clientEmail varchar(255) NOT NULL,
  balance numeric(19, 2) NOT NULL,
  bank_id BIGINT NOT NULL,
  FOREIGN KEY (bank_id) REFERENCES bank (id)
);

INSERT INTO account(id, clientName, clientEmail, balance, bank_id) VALUES(1, 'Sylvain Dupuy', 'dupuy.Sylvain@icloud.com', 10000, 3);