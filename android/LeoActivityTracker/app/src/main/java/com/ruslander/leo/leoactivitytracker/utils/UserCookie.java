package com.ruslander.leo.leoactivitytracker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class UserCookie {
    private static final String USER_COOKIE_PREF_NAME = "USER_COOKIE";

    public static void writeCookie(Context context, User user, boolean dirty) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_COOKIE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("firstName", user.getFirstName());
        editor.putString("lastName", user.getLastName());
        editor.putBoolean("dirty", dirty);
        editor.apply();
    }

    public static User readCookie(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_COOKIE_PREF_NAME, Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("dirty", true)) {
            User user = new User();
            user.setId(sharedPref.getInt("id", -1));
            user.setEmail(sharedPref.getString("email", null));
            user.setFirstName(sharedPref.getString("firstName", null));
            user.setLastName(sharedPref.getString("lastName", null));

            return user;
        } else {
            return null;
        }
    }

    public static boolean isCookieDirty(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_COOKIE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean("dirty", true);
    }

    public static void setCookieDirty(Context context, boolean dirty) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_COOKIE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("dirty", dirty);
        editor.apply();
    }
}
