package ru.jobvms3;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import ru.jobvms3.examples.api.Page;
import ru.jobvms3.examples.api.User;
import ru.jobvms3.examples.service.UserServiceImpl;
import ru.jobvms3.examples.service.dao.UserDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Тест для класса UserServiceImpl.
 */
public class UserServiceImplTest {

    /**
     *  Проверяет возврат страницы при наличии данных и отсутсвии параметров.
     */
    @Test
    public void getUsersByParam_withoutParams_allData() throws ParseException {
        UserDao dao = PowerMockito.mock(UserDao.class);

        PowerMockito.when(dao.getUsersCount(1, 2,null, null, null, null ))
                    .thenReturn(2L);
        List<ru.jobvms3.examples.entity.User> users = new ArrayList<>();
        users.add(buildUserEntity(
                1L,
                "FirstUser",
                "FirstUserSurname",
                "email1@ya.ru",
                new SimpleDateFormat("dd.MM.yyyy").parse("10.08.1990")
        ));
        users.add(buildUserEntity(
                2L,
                "SecondUser",
                "SecondUserSurname",
                "email2@ya.ru",
                new SimpleDateFormat("dd.MM.yyyy").parse("10.08.1991")
        ));
        PowerMockito.when(dao.getUsers(1, 2,null, null, null, null ))
                    .thenReturn(users);
        UserServiceImpl userService = new UserServiceImpl(dao);

        Page<User> page = userService.getUsersByParam(1, 2,null, null, null, null );

        Assert.assertEquals(1, page.getPagesCount());
        Assert.assertEquals(2, page.getSize());
        Assert.assertEquals(2, page.getElements().size());
        User user = page.getElements().get(0);

        Assert.assertEquals(Long.valueOf(1), user.getId());
        Assert.assertEquals("FirstUser", user.getName());
        Assert.assertEquals("FirstUserSurname", user.getSecondName());
        Assert.assertEquals("email1@ya.ru", user.getEmail());
        Assert.assertEquals(
                new SimpleDateFormat("dd.MM.yyyy").parse("10.08.1990"),
                user.getBirthday()
        );

    }

    /**
     * Проверяет возврат страницы при отсутствии данных.
     */
    @Test
    public void getUsersByParam_withoutParams_noData() {
        UserDao dao = PowerMockito.mock(UserDao.class);

        PowerMockito.when(dao.getUsersCount(1, 2,null, null, null, null ))
                .thenReturn(0L);

        PowerMockito.when(dao.getUsers(1, 2,null, null, null, null ))
                .thenReturn(Collections.emptyList());
        UserServiceImpl userService = new UserServiceImpl(dao);

        Page<User> page = userService.getUsersByParam(1, 2,null, null, null, null );

        Assert.assertEquals(0, page.getPagesCount());
        Assert.assertEquals(0, page.getSize());
        Assert.assertEquals(0, page.getElements().size());
    }

    private ru.jobvms3.examples.entity.User buildUserEntity(Long id, String name, String secondName, String email, Date birthday) {
        ru.jobvms3.examples.entity.User user = new ru.jobvms3.examples.entity.User();
        user.setId(id);
        user.setName(name);
        user.setSecondName(secondName);
        user.setEmail(email);
        user.setBirthday(birthday);
        return user;
    }

}
