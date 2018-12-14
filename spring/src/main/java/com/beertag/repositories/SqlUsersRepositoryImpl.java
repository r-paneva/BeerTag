package com.beertag.repositories;

import com.beertag.models.User;
import com.beertag.repositories.base.UsersRepository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlUsersRepositoryImpl implements UsersRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getByUserName(String userName) {
        User result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = (User) session.createQuery("from User where userName = :userName")
                    .setParameter("userName", userName)
                    .getSingleResult();
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    public List getAll() {
        List result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void update(User item) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(item);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}