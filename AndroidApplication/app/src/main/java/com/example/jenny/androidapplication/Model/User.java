package com.example.jenny.androidapplication.Model;


/**
 * Author: Linus Lagerhjelm
 * File: User
 * Created: 2017-03-12
 * Description: Holds user information. This class produces immutable objects
 */
public final class User {

    private final int mId;

    private final String mFirstName;

    private final String mLastName;

    private final Office mOffice;

    public User(int id, String firstName, String lastName, Office office) {
        this.mId = id;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mOffice = office;
    }

    public int getId() {
        return mId;
    }

    /**
     * Determines weather the current instance of the class is valid.
     * A User object is considered valid iff first name, last name and
     * office fields are all set
     * @return true/false accordingly
     */
    boolean valid() {
        return mFirstName != null && mLastName != null && mOffice != null;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    Office getOffice() {
        return mOffice;
    }

    /**
     * Returns a copy of the current user object with the data provided
     * @param firstName the new First Name
     * @param lastName the new Last Name
     * @param office the new Office
     * @return a copy of the user object with the new values
     */
    User getModifiedCopy(String firstName, String lastName, Office office) {
        return new User(this.mId, firstName, lastName, office);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (mId != user.mId) return false;
        if (!mFirstName.equals(user.mFirstName)) return false;
        if (!mLastName.equals(user.mLastName)) return false;
        return mOffice == user.mOffice;
    }

    @Override
    public int hashCode() {
        int result = mId;
        result = 31 * result + mFirstName.hashCode();
        result = 31 * result + mLastName.hashCode();
        result = 31 * result + mOffice.hashCode();
        return result;
    }
}

