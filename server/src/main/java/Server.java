
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static spark.Spark.*;

/**
 * Author: Linus Lagerhjelm
 * File: Server
 * Created: 2017-03-06
 * Description: The main server class
 */
public class Server {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger.getLogger("org").setLevel(Level.WARN);
        Logger.getLogger("akka").setLevel(Level.WARN);

        APIFacade api = new APIFacade();
        get("/api/startGame", api::startGame);
        get("/api/getUsers", api::getUsers);
        get("/api/getHighScores", api::getHighScores);

        post("/api/postScores", api::postScores);
        post("/api/createUser", api::createUser);

    }
}