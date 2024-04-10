package com.udacity.jwdnd.course1.cloudstorage.entity;

public class Result {

    private boolean isSuccessful;
    private String message;
    private String redirectTo;



    public Result(boolean isSuccessful, String message, String redirectTo) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.redirectTo = redirectTo;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }
}
