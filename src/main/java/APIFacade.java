
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

    Gson mParser = new Gson();
    Database mDb = Database.getInstance();

    Object getAllHighScores(Request req, Response res) {
        return null;
    }

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
            return HttpHelper.getErrorPage("400 Invalid format");
        }

        for (Score score : scores) {
            if (!score.valid()) {
                response.status(400);
                return HttpHelper.getErrorPage("400 Invalid format");
            }
        }
        mDb.insertScores(scores);
        response.status(200);
        return HttpHelper.getOkPage();
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
            return HttpHelper.getErrorPage("400 Invalid format");
        }

        if (user.valid()) {
            mDb.insertUser(user);
            response.status(200);
            return HttpHelper.getOkPage();
        }
        response.status(400);
        return HttpHelper.getErrorPage("400 Invalid user format");
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

        return mParser.toJson(returnUsers);
    }
}
