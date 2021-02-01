package ru.jobvms3.examples.service.dao;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import ru.jobvms3.examples.entity.User;

/**
 * Реализация DAO для работы с пользователями.
 */
@ApplicationScoped
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName="usersPU")
    private EntityManager em;

    @Override
    public Long addUser(User user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(user);
            em.flush();
            transaction.commit();
            return user.getId();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void updateUser(User user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.merge(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public User getUser(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void deleteUser(Long id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.remove(getUser(id));
            em.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<User> getUsers(
            int pageNumber,
            int pageSize,
            String name,
            String secondName,
            String email,
            Date birthday) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> userCriteria = cb.createQuery(User.class);
        Root<User> userRoot = userCriteria.from(User.class);
        userCriteria.select(userRoot);
        if (name != null || secondName != null || email != null || birthday != null) {
            userCriteria.where(addCriteria(cb, userRoot, name, secondName, email, birthday));
        }

        Query q = em.createQuery(userCriteria);
        if (pageNumber > 0) {
            q.setFirstResult(pageNumber * pageSize);
        }
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    @Override
    public Long getUsersCount(
            int pageNumber,
            int pageSize,
            String name,
            String secondName,
            String email,
            Date birthday) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        criteria.select(cb.count(criteria.from(User.class)));
        if (name != null || secondName != null || email != null || birthday != null) {
            Root<User> userRoot = criteria.from(User.class);
            criteria.where(addCriteria(cb, userRoot, name, secondName, email, birthday));
        }
        TypedQuery<Long> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    private Expression<Boolean> addCriteria(
            CriteriaBuilder cb,
            Root<User> userRoot,
            String name,
            String secondName,
            String email,
            Date birthday) {
        Expression<Boolean> restriction = null;
        if (name != null) {
            restriction = cb.like(userRoot.get("name"), name);
        }
        if (secondName != null) {
            Expression<Boolean> loginExpression = cb.like(userRoot.get("secondName"), secondName);
            restriction = restriction != null ? cb.and(restriction, loginExpression) : loginExpression;
        }
        if (email != null) {
            Expression<Boolean> emailExpression = cb.like(userRoot.get("email"), email);
            restriction = restriction != null ? cb.and(restriction, emailExpression) : emailExpression;
        }
        if (birthday != null) {
            Expression<Boolean> birthdayExpression = cb.equal(userRoot.get("birthday"), birthday);
            restriction = restriction != null ? cb.and(restriction, birthdayExpression) : birthdayExpression;
        }
        return restriction;
    }

}
