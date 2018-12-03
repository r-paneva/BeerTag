package com.beertag.repositories;

import com.beertag.models.Tag;
import com.beertag.repositories.base.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlTagRepositoryImpl implements TagRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Tag getTagByID(int id) {
        Tag result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.get(Tag.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("SELECT t FROM Tag t").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void create(Tag tag) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            session.save(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tag getByName(String name) {
        Tag result = null;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = (Tag) session.createQuery("from Tag where name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    public void update(int id, Tag item) {
        Tag object = null;
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
