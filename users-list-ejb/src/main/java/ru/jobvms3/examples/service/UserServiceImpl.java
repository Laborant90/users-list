package ru.jobvms3.examples.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ru.jobvms3.examples.api.Page;
import ru.jobvms3.examples.api.User;
import ru.jobvms3.examples.api.UserService;
import ru.jobvms3.examples.service.dao.UserDao;

/**
 * Сервис получения информации из справочника пользователей.
 */
@Stateless
public class UserServiceImpl implements UserService {

    /**
     * DAO-объект для доступа к данным о пользователях.
     */
    @Inject
    private UserDao userDao;

    @Override
    public Page<User> getUsersByParam(
            int pageNumber,
            int pageSize,
            String name,
            String secondName,
            String email,
            Date birthday) {

        Long count = userDao.getUsersCount(pageNumber, pageSize, name, secondName, email, birthday);

        if (count != 0) {
            List<User> users = userDao.getUsers(pageNumber, pageSize, name, secondName, email, birthday)
                    .stream()
                    .map(this::buildUser)
                    .collect(Collectors.toList());
            return new Page<>(pageNumber, Long.valueOf(count/pageSize).intValue(), users);
        }

        return new Page<>(0, 0, Collections.emptyList());
    }

    @Override
    public void updateUser(Long id, String name, String secondName, String email, Date birthday) {
        userDao.updateUser(buildUserEntity(id, name, secondName, email, birthday));
    }

    @Override
    public Long insertUser(String name, String secondName, String email, Date birthday) {
        return userDao.addUser(buildUserEntity(null, name, secondName, email, birthday));
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    private ru.jobvms3.examples.entity.User buildUserEntity(
            Long id,
            String name,
            String secondName,
            String email,
            Date birthday) {
        ru.jobvms3.examples.entity.User userEntity = new ru.jobvms3.examples.entity.User();
        userEntity.setId(id);
        userEntity.setName(name);
        userEntity.setSecondName(secondName);
        userEntity.setBirthday(birthday);
        userEntity.setEmail(email);
        return userEntity;
    }

    private User buildUser(ru.jobvms3.examples.entity.User entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setSecondName(entity.getSecondName());
        user.setBirthday(entity.getBirthday());
        user.setEmail(entity.getEmail());
        return user;
    }

}
