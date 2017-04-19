/**
 * Author: Linus Lagerhjelm
 * File: HttpHelper
 * Created: 2017-03-22
 * Description: Contains helper-functions for sending responses to client
 */
class HttpHelper {

    /**
     * Returns a html page that contains the provided error message
     * @param message the error message
     * @param status the status code for the error
     * @return html page
     */
    static String getError(String message, int status) {
        return String.format("{\"status\": %d, \"message\":\"%s\"}", status, message);
    }

    /**
     * Returns an status ok page
     * @return the html page
     */
    static String getOk() {
        return String.format("{\"status\": %d, \"message\":\"%s\"}", 200, "ok");
    }
}
