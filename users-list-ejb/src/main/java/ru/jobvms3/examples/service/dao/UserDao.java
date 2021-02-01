package ru.jobvms3.examples.service.dao;

import java.util.Date;
import java.util.List;

import ru.jobvms3.examples.entity.User;

/**
 * DAO-интерфейс для работы с сущностями User.
 */
public interface UserDao {

    /**
     * Добавляет нового пользователя.
     *
     * @param user - Описание нового пользователя.
     *
     * @return - Идентификатор нового пользователя.
     */
    Long addUser(User user);

    /**
     * Обновляет данные о существующем пользователе.
     *
     * @param user - Описание пользователя.
     */
    void updateUser(User user);

    /**
     * Получает информацию о пользователю по идентификатору.
     *
     * @param id - Идентификатор пользователя.
     *
     * @return - Информация о пользователе.
     */
    User getUser(Long id);

    /**
     * Удаляет пользователя.
     *
     * @param id - Идентификатор удаляемого пользователя.
     */
    void deleteUser(Long id);

    /**
     * Метод ищет пользователей по различным полям. Поиск осуществляется по одному или нескольким опциональным
     * параметрам.
     *
     * @param pageNumber - Номер страницы.
     * @param pageSize   - Размер страницы.
     * @param name       - Имя пользователя (опциональный параметр).
     * @param secondName - Фамилия пользователя (опциональный параметр).
     * @param email      - email пользователя (опциональный параметр).
     * @param birthday   - день рождения пользователя (опциональный параметр).
     *
     * @return Страница с информацией о пользователях.
     */
    List<User> getUsers(int pageNumber, int pageSize, String name, String secondName, String email, Date birthday);

    /**
     * Метод возвращает количество пользователей найденных по различным полям. Поиск осуществляется по одному или
     * нескольким опциональным параметрам.
     *
     * @param pageNumber - Номер страницы.
     * @param pageSize   - Размер страницы.
     * @param name       - Имя пользователя (опциональный параметр).
     * @param secondName - Фамилия пользователя (опциональный параметр).
     * @param email      - email пользователя (опциональный параметр).
     * @param birthday   - день рождения пользователя (опциональный параметр).
     *
     * @return Количество найденных записей.
     */
    Long getUsersCount(int pageNumber, int pageSize, String name, String secondName, String email, Date birthday);

}
