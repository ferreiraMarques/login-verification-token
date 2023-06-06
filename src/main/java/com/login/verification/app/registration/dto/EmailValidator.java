package com.login.verification.app.registration.dto;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String t) {
        
        return true;
    }

}
