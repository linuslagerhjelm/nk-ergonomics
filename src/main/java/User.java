/**
 * Author: Linus Lagerhjelm
 * File: User
 * Created: 2017-03-12
 * Description: Holds user information
 */
public class User {
    public enum Office {
        SKELLEFTEÅ, STOCKHOLM, LA
    }

    private final int mId;
    private final String mFirstName;
    private final String mLastName;
    private final Office mOffice;

    public User(int mId, String mFirstName, String mLastName, Office mOffice) {
        this.mId = mId;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mOffice = mOffice;
    }

    int getId() {

        return mId;
    }

    String getFirstName() {
        return mFirstName;
    }

    String getLastName() {
        return mLastName;
    }

    Office getOffice() {
        return mOffice;
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
