package com.kwizera.javaamalitechlab07hospitalmgtsystem.utils;

public class InputValidationUtil {
    // validating names
    public boolean invalidNames(String names) {
        return (!names.matches("[A-Za-z ]*") || names.length() < 2);
    }

    // validating address
    public boolean invalidAddress(String address) {
        return (!address.matches("^[A-Z]{2}\\s\\d{1,4}\\s[A-Za-z]+$"));
    }

    // validating phone number, 250 or 078, 072....
    public boolean invalidPhoneNumber(String address) {
        return (!address.matches("^(25)?(078|072|073|079)\\d{7}$"));
    }

}
