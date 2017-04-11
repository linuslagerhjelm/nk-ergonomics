package com.example.jenny.androidapplication.Model;


/**
 * Author: Linus Lagerhjelm
 * File: Score
 * Created: 2017-03-22
 * Description: Represents a HighScore. Produces immutable objects
 */
public final class Score {

    private final int mValue;

    private final long mTimestamp;

    private final User mUser;


    public Score (int value, long timestamp, User user) {
        mValue = value;
        mTimestamp = timestamp;
        mUser = user;

    }

    /**
     * Checks if the score is valid
     * @return true/false accordingly
     */
    boolean valid() {
        return mUser != null && mTimestamp != 0;
    }

    int getValue() {
        return mValue;
    }

    long getTimestamp() {
        return mTimestamp;
    }

    User getUser() {
        return mUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (mValue != score.mValue) return false;
        if (mTimestamp != score.mTimestamp) return false;
        return mUser.equals(score.mUser);
    }

    @Override
    public int hashCode() {
        int result = mValue;
        result = 31 * result + (int) (mTimestamp ^ (mTimestamp >>> 32));
        result = 31 * result + mUser.hashCode();
        return result;
    }
}

