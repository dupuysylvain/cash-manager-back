CREATE TABLE app_user (
  id serial PRIMARY KEY,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL
);

INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (1, 'John', 'Doe', '{bcrypt}$2y$12$GI8KtTogdI6juRJhwFF6E.6eRgZTqgPMEhz7m7/eFaJwwvgW8.QoC', 'john.doe');
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (2, 'Admin', 'Admin', '{bcrypt}$2y$12$GI8KtTogdI6juRJhwFF6E.6eRgZTqgPMEhz7m7/eFaJwwvgW8.QoC', 'admin.admin');