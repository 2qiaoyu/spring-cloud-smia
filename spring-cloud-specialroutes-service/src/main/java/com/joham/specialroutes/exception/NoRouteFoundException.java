package com.joham.specialroutes.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author joham
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRouteFoundException extends RuntimeException {

}
