package com.utm.stanislav.parkingapp.validators;

public interface ValidationChain<T> extends Validator<T>{
    Integer getOrder();
}
