package fr.satysko.cernun.models;

public class RestResponse<T> {

    private T content;
    private int statusHttp;
    private String error;

    public RestResponse(T content) {
        this.content = content;
        this.statusHttp = 200;
    }

    public RestResponse(Throwable throwable, int statusHttp){
        this.error = throwable.getMessage();
        this.statusHttp = statusHttp;
    }


    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getStatusHttp() {
        return statusHttp;
    }

    public void setStatusHttp(int statusHttp) {
        this.statusHttp = statusHttp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
