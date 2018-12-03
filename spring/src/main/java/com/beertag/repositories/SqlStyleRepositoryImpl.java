package com.beertag.repositories;

import com.beertag.models.Style;
import com.beertag.repositories.base.StyleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlStyleRepositoryImpl implements StyleRepository {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Style getStyleByID(int id) {
        Style result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.get(Style.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Style> getAllStyles() {
        List<Style> result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("SELECT t FROM Style t").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Style getByName(String name) {
        Style result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = (Style) session.createQuery("from Style where name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    public void update(int id, Style item) {
        Style object = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            object = session.get(item.getClass(), id);
            session.update(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}