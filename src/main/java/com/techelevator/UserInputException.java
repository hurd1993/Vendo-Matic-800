package com.techelevator;

public class UserInputException extends Throwable{
    public UserInputException() {
        super();
    }
    public UserInputException(String message) {
        super(message);
    }
    public UserInputException(String message, Exception cause) {
        super(message, cause);
    }
}
