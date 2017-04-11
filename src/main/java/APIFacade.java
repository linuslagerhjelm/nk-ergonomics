
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Author: Linus Lagerhjelm
 * File: APIFacade
 * Created: 2017-03-06
 * Description: Provides a facade for the background api
 */
public class APIFacade {

    private Gson mParser = new Gson();
    private Database mDb = Database.getInstance();


    Object startGame(Request request, Response response) {
        return null;
    }

    /**
     * Handler that uses the request arguments to insert scores into database
     * @param request the request from client
     * @param response the response writer
     * @return ok/error page
     */
    Object postScores(Request request, Response response) {
        Score[] scores;
        try {
            scores = mParser.fromJson(request.body(), Score[].class);

        } catch (JsonSyntaxException e) {
            response.status(400);
            return HttpHelper.getError("Invalid format", 400);
        }

        for (Score score : scores) {
            if (!score.valid()) {
                response.status(400);
                return HttpHelper.getError("Invalid format", 400);
            }
        }
        mDb.insertScores(scores);
        response.status(200);
        return HttpHelper.getOk();
    }

    /**
     * Creates a new user based on data from request
     * @param request the request
     * @param response the response writer
     * @return html-string
     */
    Object createUser(Request request, Response response) {
        User user;
        try {
            user = mParser.fromJson(request.body(), User.class);

        } catch (JsonSyntaxException e) {
            response.status(400);
            return HttpHelper.getError("Invalid format", 400);
        }

        if (user.valid()) {
            User returnUser = new User(mDb.getNextUserId(), user.getFirstName(), user.getLastName(), user.getOffice());
            mDb.insertUser(user);
            response.status(200);
            return mParser.toJson(returnUser);
        }
        response.status(400);
        return HttpHelper.getError("Invalid user format", 400);
    }

    /**
     * Returns a JSON-list of matched users
     * @param request the provided request containing arguments for reading users
     * @param response response writer
     * @return json formatted string
     */
    Object getUsers(Request request, Response response) {
        Map<String, String[]> values = request.queryMap().toMap();
        List<User> returnUsers = new ArrayList<>();
        String[] officeUsers = values.get("office");
        String[] namedUsers = values.get("name");
        String[] uId = values.get("id");

        if (officeUsers != null) {
            Arrays.stream(officeUsers).forEach(office -> {
                returnUsers.addAll(mDb.getUsersByOffice(User.Office.valueOf(office)));
            });
        }

        if (namedUsers != null) {
            Arrays.stream(namedUsers).forEach(name -> {
                String[] names = name.split(" ");
                try {
                    returnUsers.addAll(mDb.getUsersByFullName(names[0], names[1]));
                } catch (NoSuchUserException e) {}
            });
        }

        if (uId != null) {
            Arrays.stream(uId).forEach(id -> {
                try {
                    returnUsers.addAll(mDb.getUsersById(Integer.parseInt(id)));
                } catch (NoSuchUserException ignore) {}
            });
        }

        return mParser.toJson(returnUsers);
    }

    Object getHighScores(Request request, Response response) {
        Map<String, String[]> values = request.queryMap().toMap();
        String[] startTime = values.get("startTime");

        if (startTime == null || startTime.length != 1) {
            response.status(400);
            return HttpHelper.getError("startTime is required", 400);
        }

        HighScoreFilter filter = new HighScoreFilter(Long.parseLong(startTime[0]));
        filter.setEndTime(values.get("endTime"));
        filter.setLimit(values.get("limit"));
        filter.setOffice(values.get("office"));
        filter.setName(values.get("name"));

        return mParser.toJson(mDb.getScoresFromFilter(filter));
    }
}
