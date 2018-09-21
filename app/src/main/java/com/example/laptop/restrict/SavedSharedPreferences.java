package com.example.laptop.restrict;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedSharedPreferences {

    static final String PREF_API_TOKEN= "prefapitoken";
    static final String PREF_AVATAR="prefavatar";
    static final String PREF_USERID="prefuserid";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void clearSharedPreferences(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();

    }
    public static void setAPIToken(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_API_TOKEN, userName);
        editor.commit();
    }

    public static String getAPIToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_API_TOKEN, "");
    }

    public static void setPrefAvatar(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_AVATAR, userName);
        editor.commit();
    }

    public static String getPrefAvatar(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_AVATAR, "");
    }
    public static void setPrefUserid(Context ctx, int userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USERID, userName);
        editor.commit();
    }

    public static int getPrefUserid(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_USERID, 0);
    }
}
