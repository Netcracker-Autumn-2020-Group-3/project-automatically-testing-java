package ua.netcracker.group3.automaticallytesting.exception;

public class UserNotFound extends Exception{

    public UserNotFound(long id){
        super("user with id " + id + " not found");
    }
}
