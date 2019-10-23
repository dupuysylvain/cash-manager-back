CREATE TABLE cheque (
  id serial PRIMARY KEY,
  qrCode varchar(255) NOT NULL,
  value numeric(19, 2) NOT NULL,
  account_id BIGINT NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account (id)
);
