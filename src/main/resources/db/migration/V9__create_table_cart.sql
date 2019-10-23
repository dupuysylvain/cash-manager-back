CREATE TABLE cart (
  id serial PRIMARY KEY,
  user_id BIGINT,
  status char(1),
  FOREIGN KEY (user_id) REFERENCES app_user (id)
);

INSERT INTO cart(id, user_id, status) VALUES (1, 1, 'N')