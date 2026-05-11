package com.budgetapp.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtil {
    private static ResourceBundle bundle;
    private static Locale currentLocale = new Locale("en");

    static {
        bundle = ResourceBundle.getBundle("lang.String", currentLocale);
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("lang.String", currentLocale);
    }

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    public static ResourceBundle getResourceBundle() {
        return bundle;
    }
}