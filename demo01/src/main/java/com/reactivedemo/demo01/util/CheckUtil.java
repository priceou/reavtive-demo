package com.reactivedemo.demo01.util;

import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Stream;

public class CheckUtil {

    private static final String[] INVALID_NAMES = {"admin", "administrator"};

    public static void checNames(String value){
        Stream.of(INVALID_NAMES).filter(name -> name.equalsIgnoreCase(value))
                .findAny().ifPresent(name -> {
            try {
                throw new Exception("name:"+name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
