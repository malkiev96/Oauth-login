# AUTH-SERVER

Spring Authorization Server - сервер авторизации, обеспечивает реализацию спецификаций OAuth 2.0 и
OpenID Connect 1.0

Эндпоинты:

* openID - http://auth-server:9000/.well-known/openid-configuration
* Auth URL - http://auth-server:9000/oauth2/authorize
* Access Token URL - http://auth-server:9000/oauth2/token
* Callback URL - http://127.0.0.1:8080/login/oauth2/code/gateway

Роли:

* ROLE_USER - назначается всем по умолчанию
* ROLE_ADMIN - администратор

JWT claims:

* id - идентификатор пользовател
* sub, username - логин
* firstName - имя
* lastName - фамилия
* email - адрес электронной почты
* roles - список ролей