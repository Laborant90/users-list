package ru.jobvms3.examples.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import javax.validation.constraints.NotNull;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.jobvms3.examples.api.Page;
import ru.jobvms3.examples.api.User;
import ru.jobvms3.examples.api.UserService;

/**
 * REST-контроллер для работы с сущностью "Пользователь".
 */
@ApplicationPath("/")
@Path("users")
@Stateless
@LocalBean
public class UsersRestService extends Application {

    private Logger LOG = Logger.getLogger(UsersRestService.class.getName());

    /**
     * Сервис для работы с пользователями.
     */
    @EJB
    private UserService userService;

    /**
     * Запрашивает данные о пользователе. В случае наличия обязательного параметра - используется для фильтрации
     * по условию равенства.
     *
     * @param pageNumber - Номер запрашиваемой страницы.
     * @param pageSize   - Размер страницы.
     * @param name       - Имя пользователя (необязательный параметр).
     * @param secondName - Фамилия пользователя (необязательный параметр).
     * @param email      - email пользователя (необязательный параметр).
     * @param birthday   - Дата рождения пользователя в формате "ДД.ММ.ГГГГ".
     *
     * @return - HTTP ответ со статусом OK и информацию о найденных пользователях в JSON.
     */
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(
            @NotNull @QueryParam("pageNumber") int pageNumber,
            @NotNull @QueryParam("pageSize") int pageSize,
            @QueryParam("name") String name,
            @QueryParam("secondName") String secondName,
            @QueryParam("email") String email,
            @QueryParam("birthday") String birthday) {
        try {
            Page<User> users = userService.getUsersByParam(
                    pageNumber,
                    pageSize,
                    name,
                    secondName,
                    email,
                    parseBirthday(birthday)
            );

            JsonObjectBuilder pageBuilder = Json.createObjectBuilder();
            pageBuilder.add("size", users.getSize());
            pageBuilder.add("pageNumber", users.getPageNumber());
            pageBuilder.add("pagesCount", users.getPagesCount());

            JsonArrayBuilder usersArrayBuilder = Json.createArrayBuilder();

            for (User user : users.getElements()) {
                JsonObjectBuilder userObjectBuilder =
                        Json.createObjectBuilder()
                            .add("id", user.getId())
                            .add("name", user.getName())
                            .add("secondName", user.getSecondName())
                            .add("email", user.getEmail());

                if (user.getBirthday() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    userObjectBuilder.add("birthday", sdf.format(user.getBirthday()));
                }
                usersArrayBuilder.add(userObjectBuilder);
            }
            pageBuilder.add("users", usersArrayBuilder);
            return Response.ok(pageBuilder.build()).build();
        } catch (Exception e) {
            LOG.log(
                    Level.SEVERE,
                    "Произошла ошибка при получении данных о пользователях "
                    + " pageNumber=" + pageNumber
                    + " pageSize=" + pageSize
                    + " name=" + name
                    + " secondName=" + secondName
                    + " email=" + email
                    + " birthday=" + birthday
                    + " Сообщение: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    /**
     * Добавляет данные о пользователе.
     *
     * @param name       - Имя пользователя (необязательный параметр).
     * @param secondName - Фамилия пользователя.
     * @param email      - email пользователя.
     * @param birthday   - Дата рождения пользователя в формате "ДД.ММ.ГГГГ".
     *
     * @return - HTTP ответ со статусом OK и информацию об идентификаторе добавленного пользователя.
     */
    @PUT
    @Path("insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
    public Response insert(
            @NotNull @QueryParam("name") String name,
            @NotNull @QueryParam("secondName") String secondName,
            @NotNull @QueryParam("email") String email,
            @NotNull @QueryParam("birthday") String birthday) {
        try {
            Long id = userService.insertUser(
                        name,
                        secondName,
                        email,
                        parseBirthday(birthday)
            );

            return Response.ok(Json.createObjectBuilder().add("id", id).build())
                           .build();
        } catch (Exception e) {
            LOG.log(
                    Level.SEVERE,
                    "Произошла ошибка при вставке данные пользователя"
                            + " name=" + name
                            + " secondName=" + secondName
                            + " email=" + email
                            + " birthday=" + birthday
                            + " Сообщение: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    /**
     * Обновляет данные о пользователе.
     *
     * @param id         - Идентификатор пользователя.
     * @param name       - Имя пользователя.
     * @param secondName - Фамилия пользователя.
     * @param email      - email пользователя.
     * @param birthday   - Дата рождения пользователя в формате "ДД.ММ.ГГГГ".
     *
     * @return - HTTP ответ со статусом OK и информацию о найденных пользователях в JSON.
     */
    @POST
    @Path("update")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
    public Response update(
            @NotNull @QueryParam("id") Long id,
            @QueryParam("name") String name,
            @QueryParam("secondName") String secondName,
            @QueryParam("email") String email,
            @QueryParam("birthday") String birthday) {
        try {
            userService.updateUser(id, name, secondName, email, parseBirthday(birthday));
            return Response.status(Response.Status.OK).entity("Пользователь удалён успешно").build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Произошла ошибка при удалении пользователя id=" + id + " Сообщение: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    /**
     * Удаляет данные о пользователе.
     *
     * @param id - Идентификатор пользователя.
     *
     * @return - HTTP ответ со статусом OK в случае успешного удаления.
     */
    @DELETE
    @Path("delete")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
    public Response delete(@NotNull @QueryParam("id") Long id) {
        try {
            userService.deleteUser(id);
            return Response.status(Response.Status.OK).entity("Пользователь удалён успешно").build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Произошла ошибка при удалении пользователя id=" + id + " Сообщение: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    private Date parseBirthday(String date) throws ParseException {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.parse(date);
    }

}
