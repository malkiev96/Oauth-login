# EUREKA-SERVER

Eureka Server — это service discovery (обнаружение сервисов) для микросервисов. Клиентские
приложения могут самостоятельно регистрироваться в нем, а другие микросервисы могут обращаться к
Eureka Server для поиска необходимых им микросервисов.

Eureka Server также известен как Discovery Server и содержит такую информацию как IP-адрес и порт
микросервиса.
Eureka Server защищен базовой HTTP аутентификацией

* eureka.client.register-with-eureka - определяет, регистрируется ли сервис как клиент на Eureka Server.
* eureka.client.fetch-registry - получать или нет информацию о зарегистрированных клиентах.
* eureka.client.service-url.defaultZone - определяет адрес Eureka Server, чтобы клиентское приложение могло там зарегистрироваться. 
