# Тестовый проект "Справочник пользователей"

1. ###### Порядок развёртывания:

    - Развернуть на СУБД Mysql скрипт /sql/init.sql
    - Hазвернуть на WildFly архив user-list-deployment.ear без запуска
    - Добавить DataSource c JNDI Name java:/ru.jobvms3.examples.userslist/datasource
    - Запустить модуль

2. ###### Реализация REST-Service:

Реализован REST-сервис, обрабатывающий запросы на получение, добавление, удаление, обновление
данных о пользователях. Ниже приведены описание и примеры вызова запросов
из HTTPClient IntellijIdea.

getAll - запрос на получение всех данных о пользователях
Возможен поиск по полям name, secondName, email, birthday

###

GET http://localhost:8080/users-list-web/users/getAll?pageNumber=0&pageSize=1
Accept: application/x-www-form-urlencoded

http://localhost:8080/users-list-web/users/getAll?pageNumber=0&pageSize=3&email=email&name=name1

delete - запрос на удаление пользователя по идентификатору. 

###

DELETE http://localhost:8080/users-list-web/users/delete/
Content-Type: application/x-www-form-urlencoded

id=1

insert - запрос на добавление данных о пользователе

###
PUT http://localhost:8080/users-list-web/users/insert

name=name1&secondName=secondName&email=email&birthday=01.03.2000

update - запрос на добавление данных о пользователе

###

POST http://localhost:8080/users-list-web/users/update/
Content-Type: application/x-www-form-urlencoded

id=3&name=Vasya&secondName=sasdfasdf&email=email&birthday=01.03.2000