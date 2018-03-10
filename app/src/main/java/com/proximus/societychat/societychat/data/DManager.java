package com.proximus.societychat.societychat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;


public final class DManager extends SQLiteOpenHelper {
     
    public String getDatabaseName() {
        String var10000 = super.getDatabaseName();
        return var10000;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase var10000 = super.getWritableDatabase();
        return var10000;
    }

     
    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase var10000 = super.getReadableDatabase();
        return var10000;
    }

    public void onConfigure(  SQLiteDatabase db) {
        super.onConfigure(db);
    }

    public void onDowngrade(  SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void onOpen(  SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void onCreate(  SQLiteDatabase db) {

        db.execSQL("create table usersettings(setno INTEGER primary key,prop TEXT,val TEXT)");
        db.execSQL("create table school(lid INTEGER primary key, id INTEGER,name TEXT)");
        db.execSQL("create table student(lid INTEGER primary key, id INTEGER,name TEXT, schid INTEGER, roll_no INTEGER)");
        db.execSQL("create table teacher(lid INTEGER primary key, id INTEGER,name TEXT, schid INTEGER, roll_no INTEGER, lectureids TEXT)");
        db.execSQL("create table mgroup(lid INTEGER primary key, id INTEGER,name TEXT, schid INTEGER, sids TEXT)");
        db.execSQL("create table lecture(lid INTEGER primary key, id INTEGER,name TEXT, time_from TEXT, duration )");

    }


    public final long create_class(  String className) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", className);
        return db.insert("classes", (String)null, values);
    }

    public final long create_student(  String name,   String email,   String mobileNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("mobile", mobileNo);
        return db.insert("students", (String)null, values);
    }

    public final long create_period(  String name,   String email,   String mobileNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("mobile", mobileNo);
        return db.insert("students", (String)null, values);
    }

    public String get_setting(String key) {
        return settings.get(key);
    }
    public void save_setting(String key, String  value){
        SQLiteDatabase db = getWritableDatabase();
        if(settings.containsKey(key))
        {
            db.execSQL("update usersettings set val='"+value+"' where prop='"+key+"'");
        }
        else{
            ContentValues values = new ContentValues();
            values.put("val", value);
            values.put("prop", key);
            db.insert("usersettings", (String)null, values);
        }
        update_settings();
    }
    HashMap<String, String> settings;
    public DManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        update_settings();
    }
    void update_settings()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        settings= new HashMap<String, String>();
        Cursor cs = db.rawQuery("select * from usersettings", (String[])null);
        if(cs.moveToFirst()) {
            do {
                settings.put(cs.getString(1), cs.getString(2));
            } while(cs.moveToNext());
        }
    }
    public static void set_cookie(String cookie,Context context){
        /*cookie=sessionid=zl3xq7meyhpm4wykevp91vl4kz0qln1y; expires=Wed, 21-Mar-2018 14:31:59 GMT; HttpOnly; Max-Age=1209600; Path=/*/
        Log("Got Cookie:"+cookie);
        String old_cookie = get_cookie(context);//, new_cookie=new String(cookie);
        {
            if(old_cookie==null)old_cookie="";
            String[]
                    cp = cookie.split(";"),
                    ocp = old_cookie.split(";");

                    for(int i=0;i<ocp.length;i++)
                    {
                        boolean found=false;
                        String ocpp = ocp[i].split("=")[0];
                        for(int j=0;j<cp.length;j++)
                        {
                            if(ocpp.contains(cp[j].split("=")[0]))
                            {
                                found=true;
                                break;
                            }
                        }
                        if(!found){
                            cookie += "; "+ocp[i];
                        }
                    }

        }
        Log("old_cookie:"+old_cookie);
        Log("saving_cookie:"+cookie);
        DManager dm = new DManager(context, "db1.db", null, 1);
        dm.save_setting("cookie", cookie);

    }
    public static String get_cookie(Context context)
    {
        DManager dm = new DManager(context, "db1.db", null, 1);
        return dm.get_setting("cookie");
    }
    public static void Log(String data)
    {
        Log.d("HBOOKSCHOOL", "DManager:_: "+data);
    }

}
