CREATE TABLE cheque (
  id serial PRIMARY KEY,
  qr_code varchar(255) NOT NULL,
  value numeric(19, 2) NOT NULL,
  account_id BIGINT NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account (id)
);

insert into cheque values (1, 'ceciestleqrcode', 50, 1)