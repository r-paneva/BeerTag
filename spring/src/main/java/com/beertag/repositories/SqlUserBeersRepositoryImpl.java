package com.beertag.repositories;

import com.beertag.models.UserBeers;
import com.beertag.models.UserBeersIdentity;
import com.beertag.repositories.base.UserBeersRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlUserBeersRepositoryImpl implements UserBeersRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List getAll() {
        List result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("from UserBeers").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public UserBeers getRatingVoteByUsersVoterAndVotedFor(UserBeersIdentity userBeersId) {
        List userBeers;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();

            userBeers = session.createQuery("from UserBeers as RV where RV.userBeersIdentity.userId=:userId and RV.userBeersIdentity.beerId=:beerId")
                    .setParameter("userId",userBeersId.getUserId())
                    .setParameter("beerId", userBeersId.getBeerId())
                    .list();

            session.getTransaction().commit();
        }
        if (userBeers.isEmpty()) {
            return null;
        } else {
            return (UserBeers) userBeers.get(0);
        }
    }

    @Override
    public float getAverageRatingForBeerByBeerId(int votedForBeerId) {
        float averageRating = 0;
        List<UserBeers> ratingVotes = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            ratingVotes = session.createQuery("from UserBeers as MB where MB.userBeersIdentity.beerId=:votedForBeerId")
                    .setParameter("votedForBeerId", votedForBeerId)
                    .list();
            int sum = 0;
            for (UserBeers rv : ratingVotes
            ) {
                sum+=rv.getVote();
            }
            averageRating = (float) sum / ratingVotes.size();

            session.getTransaction().commit();
        }
        return averageRating;
    }

    @Override
    public List getBeersByUserId(UserBeersIdentity userBeersIdentity) {
        List result = new ArrayList();
        int userId = userBeersIdentity.getUserId();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("from UserBeers where userId = :userId")
                    .setParameter("userId", userId).list();

            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public UserBeers getUserBeersById(UserBeersIdentity id) {
        return null;
    }

    @Override
    public void create(UserBeers userbeer) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            session.save(userbeer);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("You can't create new Beer " + e.getMessage() + "\nHttp Status: " + HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(UserBeers userBeers) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(userBeers);
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
    public void delete(UserBeers userBeers) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            userBeers = session.get(userBeers.getClass(), userBeers.getUserBeersIdentity());
            session.delete(userBeers);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}