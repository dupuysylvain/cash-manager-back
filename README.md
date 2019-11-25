
# Cash Manager API (java)


## Database configuration

You need a postgres database. You can setup it on file `src/main/resources/application.properties` :

```
# ===============================
# DATABASE
# ===============================
spring.datasource.url=jdbc:postgresql://db:5432/cashmanager
spring.datasource.username=postgres
spring.datasource.password=password
```

> To work locally, add this to your hosts file ([find file](https://www.wistee.fr/configuration-nom-domaine/modifier-fichier-hosts.html)) :

```
127.0.0.1       db
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
    POST /api/articles (admin only)

- payment
    POST /api/payment/purchase/{paymentMethod}/{paymentId}
        paymentMethod : creditCard / cheque
        paymentId : nfcID / qrCode

- users
    GET /api/users/me (get current user)
    GET /api/users (admin only)
    POST /api/users (admin only)

- cart
    GET /api/cart (get current cart)
    POST /api/cart/{articleId}/{quantity} (add article to cart)
    DELETE /api/cart/{articleId}/{quantity} (remove article from cart)
    DELETE /api/cart (cancel current cart)

- swagger
    /swagger-ui.html
```

## Team

Charles LAVALARD
Oussama OUZZINE
Samy LEMAISSI
Sylvain DUPUY