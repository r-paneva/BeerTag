package com.beertag.android.models;

public class RatingVote {

    private Integer vote;
    private String voterUsername;
    private String votedForBeer;

    public RatingVote(Integer vote, String voter, String votedForBeer) {
        this.vote = vote;
        this.voterUsername = voter;
        this.votedForBeer = votedForBeer;
    }

    public RatingVote() {
    }

    public Integer getRatingVoted() {
        return vote;
    }

    public void setRatingVoted(Integer ratingVoted) {
        this.vote = ratingVoted;
    }

    public String getVoterUsername() {
        return voterUsername;
    }

    public void setVoterUsername(String voterUsername) {
        this.voterUsername = voterUsername;
    }

    public String getVotedForBeer() {
        return votedForBeer;
    }

    public void setVotedForBeer(String votedForBeer) {
        this.votedForBeer = votedForBeer;
    }
}
