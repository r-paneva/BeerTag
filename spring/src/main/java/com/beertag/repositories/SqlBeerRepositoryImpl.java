package com.beertag.repositories;

import com.beertag.models.Beer;
import com.beertag.repositories.base.BeerRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;
import sun.net.www.http.HttpCapture;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlBeerRepositoryImpl implements BeerRepository {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Beer getBeerByID(int id) {
        Beer result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.get(Beer.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void create(Beer item) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("You can't create new beer " + e.getMessage() + "\nHttp Status: " + HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Beer item) {
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

    @Override
    public void delete(Beer item) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            item = session.get(item.getClass(), item.getId());
            session.delete(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List getAll() {
        List result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("from Beer").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

}