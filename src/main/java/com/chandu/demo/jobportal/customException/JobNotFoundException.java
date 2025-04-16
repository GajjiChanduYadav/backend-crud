package com.chandu.demo.jobportal.customException;

public class JobNotFoundException extends RuntimeException{


public JobNotFoundException(String message){

    super(message);
}

}
