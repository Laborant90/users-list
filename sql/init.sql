-- Скрипт инициализации объектов БД.
create table users (
    id         int auto_increment primary key,
    name       varchar(1024) null,
    secondName varchar(256)  not null,
    email      varchar(512)  null,
    birthday   date          null
)
comment 'Пользователи';

create index users_id_index
	on users (id);
-- Индексы по полям, по которым предполагается поиск.
create index name_index
	on users (name);

create index secondName_index
	on users (secondName);

create index email_index
	on users (email);

create index email_birthday
	on users (birthday);