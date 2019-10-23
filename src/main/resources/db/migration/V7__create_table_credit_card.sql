CREATE TABLE credit_card (
  id serial PRIMARY KEY,
  nfcId varchar(255) NOT NULL,
  account_id BIGINT NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account (id)
);
