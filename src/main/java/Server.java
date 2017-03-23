
import static spark.Spark.*;

/**
 * Author: Linus Lagerhjelm
 * File: Server
 * Created: 2017-03-06
 * Description: The main server class
 */
public class Server {
    private static void printWelcome() {
        System.out.println("========================");
        System.out.println("NK-ERGONOMICS");
        System.out.println("========================");
        System.out.println("Server listening on port: " + SPARK_DEFAULT_PORT);
    }

    public static void main(String[] args) {
        // Server.printWelcome();
        APIFacade api = new APIFacade();
        get("/api/highscores", api::getAllHighScores);
        get("/api/startGame", api::startGame);
        get("/api/getUsers", api::getUsers);
        post("/api/postScores", api::postScores);
        post("/api/createUser", api::createUser);

    }
}