package com.beertag.repositories;

import com.beertag.models.Beer;
import com.beertag.models.MyBeers;
import com.beertag.models.MyBeersIdentity;
import com.beertag.repositories.base.MyBeersRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<MyBeers> ratingVoters;
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();

            ratingVoters = session.createQuery("from MyBeers as RV") //where RV.voter=:voterUserId and RV.votedForBeer.beerId=:votedForBeerId")
                    //.setParameter("myBeersId", myBeersId)
                    .list();

            session.getTransaction().commit();
        }
        if (ratingVoters.isEmpty()) {
            return null;
        } else {
            return ratingVoters.get(0);
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
//                sum+=rv.getRatingVoted();
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
}