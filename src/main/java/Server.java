
import static spark.Spark.*;

/**
 * Author: Linus Lagerhjelm
 * File: Server
 * Created: 2017-03-06
 * Description: The main server class
 */
public class Server {
    public static void main(String[] args) {
        APIFacade api = new APIFacade();
        get("/api/highscores", api::getAllHighScores);
        get("/api/startGame", api::startGame);
        get("/api/getUsers", api::getUsers);
        post("/api/postScores", api::postScores);
        post("/api/createUser", api::createUser);

    }
}