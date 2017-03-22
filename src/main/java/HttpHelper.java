/**
 * Author: Linus Lagerhjelm
 * File: HttpHelper
 * Created: 2017-03-22
 * Description: Contains helper-functions for sending responses to client
 */
public class HttpHelper {

    /**
     * Returns a html page that contains the provided error message
     * @param message the error message
     * @return html page
     */
    public static String getErrorPage(String message) {
        return "<html><body><h1>" + message + "</h1></body></html>";
    }

    /**
     * Returns an status ok page
     * @return the html page
     */
    public static String getOkPage() {
        return "<html><body><h1>OK</h1></body></html>";
    }
}
