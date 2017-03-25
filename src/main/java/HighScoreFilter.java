import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: HighScoreFilter
 * Created: 2017-03-24
 * Description: Class that represents filter criteria for HighScores
 */
public final class HighScoreFilter {
    private final static int HIGHSCORE_DEFAULT_LIMIT = 10;
    private Long mStartTime;
    private Long mEndTime;
    private Integer mLimit;
    private List<User.Office> mOffice = new ArrayList<>();
    private List<String> mNames = new ArrayList<>();

    public HighScoreFilter(Long startTime) {
        mStartTime = startTime;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getEndTime() {
        return mEndTime != null ? mEndTime : System.currentTimeMillis();
    }

    public void setEndTime(String[] endTime) {
        if (endTime != null && endTime.length == 1) {
            this.mEndTime = Long.parseLong(endTime[0]);
        }
    }

    public int getLimit() {
        return mLimit != null ? mLimit : HIGHSCORE_DEFAULT_LIMIT;
    }

    public void setLimit(String[] limit) {
        if (limit != null && limit.length > 0) {
            mLimit = Integer.parseInt(limit[0]);
        }
    }

    public List<User.Office> getOffice() {
        return mOffice;
    }

    public void setOffice(String[] offices) {
        if (offices == null) return;

        for (String office : offices) {
            mOffice.add(User.Office.valueOf(office));
        }
    }

    public List<String> getNames() {
        return new ArrayList<>(mNames);
    }

    public void setName(String[] names) {
        if (names == null) return;
        Collections.addAll(mNames, names);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HighScoreFilter that = (HighScoreFilter) o;

        if (mLimit != that.mLimit) return false;
        if (!mStartTime.equals(that.mStartTime)) return false;
        if (!mEndTime.equals(that.mEndTime)) return false;
        if (mOffice != that.mOffice) return false;
        return mNames.equals(that.mNames);
    }

    @Override
    public int hashCode() {
        int result = mStartTime.hashCode();
        result = 31 * result + mEndTime.hashCode();
        result = 31 * result + mLimit;
        result = 31 * result + mOffice.hashCode();
        result = 31 * result + mNames.hashCode();
        return result;
    }

    /**
     * Uses the filtering values set within the object to return an SQL
     * query string that can be used in a sub query.
     * @return Filtering string
     */
    String getFilterString() {
        if (mNames.size() > 0) {
            return String.format("first_name in (%s) and last_name in (%s) and office in (%s)", getFirstNames(), getLastNames(), getOffices());
        } else {
            return String.format("office in (%s)", getOffices());
        }
    }

    /**
     * Handles parsing of the mNames filed int a properly formatted SQL value
     * string of first names
     * @return SQL-string
     */
    private String getFirstNames() {
        String buff = "";
        for (String name: mNames) {
            buff += String.format("'%s', ", name.split(" ")[0]);
        }

        return StringUtils.stripEnd(buff, ", ");
    }

    /**
     * Handles parsing of the mNames filed int a properly formatted SQL value
     * string of last names
     * @return SQL-string
     */
    private String getLastNames() {
        String buff = "";
        for (String name: mNames) {
            buff += String.format("'%s', ", name.split(" ")[1]);
        }

        return StringUtils.stripEnd(buff, ", ");
    }

    /**
     * Handles parsing of the mOffice filed int a properly formatted SQL value
     * string of offices
     * @return SQL-string
     */
    private String getOffices() {
        String buff = "";
        if (mOffice.size() > 0) {
            for (User.Office office : mOffice) {
                buff += String.format("'%s', ", office.name());
            }
        } else {
            for (User.Office office : User.Office.values()) {
                buff += String.format("'%s', ", office.name());
            }
        }
        return StringUtils.stripEnd(buff, ", ");
    }
}
