
import static spark.Spark.*;

/**
 * Author: Linus Lagerhjelm
 * File: Server
 * Created: 2017-03-06
 * Description:
 */
public class Server {
    public static void main(String[] args) {
        APIFacade api = new APIFacade();
        get("/hello", (a, b) -> "Hello world");
        get("/api/highscores", api::getAllHighScores);
        get("/api/startGame", api::startGame);
        post("/api/scores", api::postScores);
        post("/api/createUser", api::createUser);
    }
}