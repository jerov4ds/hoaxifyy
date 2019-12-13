package com.hoaxify.hoaxify.shared;


public class GenericResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GenericResponse(String message){
        this.message = message;
    }

    public GenericResponse(){
        
    }
}
