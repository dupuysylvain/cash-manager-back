
# Cash Manager API (java)


## Database configuration

You need a postgres database. You can setup it on file `src/main/resources/application.properties` :

```
# ===============================
# DATABASE
# ===============================
spring.datasource.url=jdbc:postgresql://localhost:5432/cashmanager
spring.datasource.username=postgres
spring.datasource.password=password
```

### default users :
* john.doe / password (STANDARD)
* admin.admin / password (ADMIN)

## Routes

```
- authentication (JWT)
    POST /auth/authenticate

- articles
    GET /api/articles

- users (admin only)
    GET /api/users
    POST /api/users

- cart
    GET /api/cart (get current cart)
    POST /api/cart/{articleId}/{quantity} (add article to cart)
    DELETE /api/cart/{articleId}/{quantity} (remove article from cart)

- swagger
    /swagger-ui.html
```

## Team

Charles LAVALARD
Oussama OUZZINE
Samy LEMAISSI
Sylvain DUPUY