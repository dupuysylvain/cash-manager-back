CREATE TABLE app_role (
  id serial PRIMARY KEY,
  description varchar(255) DEFAULT NULL,
  role_name varchar(255) DEFAULT NULL
);

INSERT INTO app_role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');