package com.helmes.apashkovich.exception;

public class CityNotFoundException extends RuntimeException {
    CityNotFoundException(Long id) {
        super("Could not find city with id: " + id);
    }
}
