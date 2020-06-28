package com.ironhack.MidtermProject.handler;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalHandler {

    /**
     * Exception Handler to handle exceptions.
     * @param e Receives an Exception.
     * @param response Receives an HTTP Response.
     * @throws IOException
     */
    @ExceptionHandler(DataNotFoundException.class)
    public void handleDataIdNotFoundException(DataNotFoundException e, HttpServletResponse response) throws IOException{

        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());}
}
