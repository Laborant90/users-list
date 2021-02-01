package ru.jobvms3.examples.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Пользователь.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Фамилия пользователя.
     */
    private String secondName;

    /**
     * Дата рождения.
     */
    private Date birthday;

    /**
     * email пользователя.
     */
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
