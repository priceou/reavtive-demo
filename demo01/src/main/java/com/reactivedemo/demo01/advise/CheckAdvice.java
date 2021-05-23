package com.reactivedemo.demo01.advise;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class CheckAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity handleBindException(WebExchangeBindException e){
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    private String toStr(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                .map(e -> e.getField()+":"+e.getDefaultMessage())
                .reduce("",(s1, s2) -> s1+"\n"+s2);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    public void addInitBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @ModelAttribute(name="maMessage")
    public String globalModelAttribute(){
        System.out.println("model attribute execute");
        return "this is from model attribute";
    }


}
