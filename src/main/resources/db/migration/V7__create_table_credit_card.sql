CREATE TABLE credit_card (
  id serial PRIMARY KEY,
  nfc_id varchar(255) NOT NULL,
  account_id BIGINT NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account (id)
);

insert into credit_card values (1, '00ABE3E4', '1');