package com.proximus.societychat.societychat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ray on 8/3/18.
 */

public class DataHandle
{
    public static boolean check_saved_login(Context context) {
        DManager dm = getD(context);
        String logged=dm.get_setting("login");
        if(logged==null) return false;
        if(logged.equals("true"))
        {
            return true;
        }
        return false;

    }
    public static void change_saved_login(Context context, boolean status) {
        DManager dm = getD(context);
        if(status) dm.save_setting("login", "true");
        else dm.save_setting("login", "false");
    }
    private static DManager getD(Context c)
    {
        return new DManager(c, "db1.db", null, 1);
    }
    public static boolean create_school(Context context,String name) {
        DManager dm = new DManager(context, "db1.db", null, 1);
        SQLiteDatabase db = dm.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("id", 0);
        db.insert("school", (String)null, cv);
        return false;
    }
    public static JSONArray school_list(Context context) throws Exception {
        DManager dm = getD(context);
        SQLiteDatabase db = dm.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from school", null);
        JSONArray js = new JSONArray();
        if(cursor.moveToFirst())
        {
            do{
                JSONObject jo = new JSONObject();
                jo.put("lid", cursor.getString(0));
                jo.put("id", cursor.getString(1));
                jo.put("name", cursor.getString(2));
                js.put(jo);
            }while (cursor.moveToNext());
        }

        return  js;
    }
}
