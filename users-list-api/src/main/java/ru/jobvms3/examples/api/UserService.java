package ru.jobvms3.examples.api;

import javax.ejb.Remote;
import java.util.Date;

/**
 * EJB-интерфейс модуля Справочник пользователей.
 */
@Remote
public interface UserService {

    /**
     * Метод поиска пользователей по различным полям. Поиск осуществляется по одному или нескольким опциональным
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
    Page<User> getUsersByParam(
            int pageNumber,
            int pageSize,
            String name,
            String secondName,
            String email,
            Date birthday
    );

    /**
     * Обновляет данные о существующем пользователе.
     *
     * @param name       - Имя пользователя (опциональный параметр).
     * @param secondName - Фамилия пользователя (опциональный параметр).
     * @param email      - email пользователя (опциональный параметр).
     * @param birthday   - день рождения пользователя (опциональный параметр).
     */
    void updateUser(Long id, String name, String secondName, String email, Date birthday);

    /**
     * Добавляет нового пользователя.
     *
     * @param name       - Имя пользователя.
     * @param secondName - Фамилия пользователя.
     * @param email      - email пользователя.
     * @param birthday   - день рождения пользователя.
     *
     * @return - Идентификатор нового пользователя.
     */
    Long insertUser(String name, String secondName, String email, Date birthday);

    /**
     * Удаляет пользователя.
     *
     * @param id - Идентификатор удаляемого пользователя.
     */
    void deleteUser(Long id);

}
