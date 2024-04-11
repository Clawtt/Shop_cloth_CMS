package com.example.shop_karolherzogbanasik.adviceController;

import com.example.shop_karolherzogbanasik.exceptions.NoElementFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorHandling {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoElementFoundException.class)
    @ResponseBody
    Map<String, String> handleNoElementFoundException(NoElementFoundException exception) {
        HashMap<String, String> exMap = new HashMap<>();
        exMap.put("status", HttpStatus.NOT_FOUND.toString());
        exMap.put("message", exception.getMessage());
        return exMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, String> handleConstraintViolationException(MethodArgumentNotValidException exception) {
        HashMap<String, String> exMap = new HashMap<>();
        List<String> errors = exception.getBindingResult()
                .getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        exMap.put("status", HttpStatus.BAD_REQUEST.toString());
        exMap.put("message", errors.get(0));
        return exMap;
    }
}

