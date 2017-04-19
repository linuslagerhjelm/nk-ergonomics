/**
 * Author: Linus Lagerhjelm
 * File: NoSuchUserException
 * Created: 2017-03-20
 * Description: Exception to indicate that no user where found that matched
 * the criteria.
 */
class NoSuchUserException extends Exception {
    NoSuchUserException() {}
    public NoSuchUserException(String msg) {super(msg);}
}
