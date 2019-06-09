package edu.nju.cinemasystem.data.vo;

import javax.validation.constraints.NotNull;

public class Response {
    private boolean success;
    private int statusCode;
    private String message;
    private Object content;

    private Response(){

    }

    public Response(@NotNull boolean success, int statusCode, String message, Object content) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    private void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static Response fail(){
        Response response = new Response();
        response.setSuccess(false);
        return response;
    }

    public static Response success(){
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response fail(String message){
        Response response = fail();
        response.setMessage(message);
        return response;
    }

    public static Response success(String message){
        Response response = success();
        response.setMessage(message);
        return response;
    }
}
