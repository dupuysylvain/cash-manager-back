CREATE TABLE app_user (
  id serial PRIMARY KEY,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL
);

INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (1, 'John', 'Doe', '$2a$10$2blctA2bsftVtyOte5nxp.yh9uDYYM19H17ScCjpHb3bm7034Q7OO', 'john.doe');
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (2, 'Admin', 'Admin', '$2a$10$2blctA2bsftVtyOte5nxp.yh9uDYYM19H17ScCjpHb3bm7034Q7OO', 'admin.admin');