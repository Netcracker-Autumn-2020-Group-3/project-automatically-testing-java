package ua.netcracker.group3.automaticallytesting.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(long id) {
        super("user with id " + id + " not found");
    }
}
