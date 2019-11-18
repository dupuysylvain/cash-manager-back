CREATE TABLE article_cart (
  article_id BIGINT NOT NULL,
  cart_id BIGINT NOT NULL,
  quantity int NOT NULL,
  FOREIGN KEY (article_id) REFERENCES article (id),
  FOREIGN KEY (cart_id) REFERENCES cart (id)
);

INSERT INTO article_cart(article_id, cart_id, quantity) VALUES (1, 1, 3);