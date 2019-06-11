package edu.nju.cinemasystem.web.controller;

import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class ExceptionController {

    private final GlobalMsg globalMsg;

    public ExceptionController(GlobalMsg globalMsg) {
        this.globalMsg = globalMsg;
    }

    @ResponseBody
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Response handleDataIntegrityViolationException() {
        return Response.fail(globalMsg.getWrongParam());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
