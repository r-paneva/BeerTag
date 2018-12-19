package com.beertag.repositories;

import com.beertag.models.MyBeers;
import com.beertag.models.MyBeersIdentity;
import com.beertag.repositories.base.MyBeersRepository;
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
public class SqlMyBeersRepositoryImpl implements MyBeersRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List getAll() {
        List result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("from MyBeers").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public MyBeers getRatingVoteByUsersVoterAndVotedFor(MyBeersIdentity myBeersId) {
        List myBeers;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();

            myBeers = session.createQuery("from MyBeers as RV where RV.myBeersIdentity.userId=:userId and RV.myBeersIdentity.beerId=:beerId")
                    .setParameter("userId",myBeersId.getUserId())
                    .setParameter("beerId", myBeersId.getBeerId())
                    .list();

            session.getTransaction().commit();
        }
        if (myBeers.isEmpty()) {
            return null;
        } else {
            return (MyBeers) myBeers.get(0);
        }
    }

    @Override
    public float getAverageRatingForBeerByBeerId(int votedForBeerId) {
        float averageRating = 0;
        List<MyBeers> ratingVotes = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            ratingVotes = session.createQuery("from MyBeers as MB where MB.myBeersIdentity.beerId=:votedForBeerId")
                    .setParameter("votedForBeerId", votedForBeerId)
                    .list();
            int sum = 0;
            for (MyBeers rv : ratingVotes
            ) {
                sum+=rv.getVote();
            }
            averageRating = (float) sum / ratingVotes.size();

            session.getTransaction().commit();
        }
        return averageRating;
    }

    @Override
    public List getBeersByUserId(MyBeersIdentity myBeersIdentity) {
        List result = new ArrayList();
        int userId = myBeersIdentity.getUserId();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("from MyBeers where userId = :userId")
                    .setParameter("userId", userId).list();

            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public MyBeers getMyBeersById(MyBeersIdentity id) {
        return null;
    }

    @Override
    public void create(MyBeers mybeer) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            session.save(mybeer);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("You can't create new MyBeer " + e.getMessage() + "\nHttp Status: " + HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(MyBeers myBeers) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(myBeers);
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
    public void delete(MyBeers myBeers) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            myBeers = session.get(myBeers.getClass(), myBeers.getMyBeersIdentity());
            session.delete(myBeers);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}