package com.beertag.repositories;

import com.beertag.models.Country;
import com.beertag.models.User;
import com.beertag.repositories.base.CountryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlCountryRepositoryImpl implements CountryRepository {


    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Country getCountryByID(int id) {
        Country result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.get(Country.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("SELECT t FROM Country t").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Country getByName(String name) {
        Country result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = (Country) session.createQuery("from Country where name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    public void update(int id, Country item) {
        Country object = null;
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
