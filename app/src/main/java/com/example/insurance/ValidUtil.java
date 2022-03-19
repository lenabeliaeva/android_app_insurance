package com.example.insurance;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidUtil {
    public static boolean isValidEmail(CharSequence s){
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    public static boolean isValidPassword(CharSequence s){
        return s.length() != 0;
    }

    public static boolean isValidPassport(CharSequence s){
        Pattern passportPattern = Pattern.compile("^([0-9]{2}\\s{1}[0-9]{2})\\s{1}([0-9]{6})$?");
        return passportPattern.matcher(s).matches();
    }

    public static boolean isValidCarNumber(CharSequence s){
        Pattern numberPattern = Pattern.compile("^[ABEKMHOPCTYX]\\d{3}(?<!000)[ABEKMHOPCTYX]{2}\\d{2,3}$?");
        return numberPattern.matcher(s).matches();
    }
}
