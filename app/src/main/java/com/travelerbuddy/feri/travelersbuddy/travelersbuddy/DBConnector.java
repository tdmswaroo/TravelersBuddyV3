package com.travelerbuddy.feri.travelersbuddy.travelersbuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Asura on 30-Dec-15.
 */
public class DBConnector extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public static final String DATABASE_NAME = "travelBuddyApp.db";
    public static final String TABLE_NAME1 = "potovanje_table";
    public static final String TABLE_NAME2 = "kovcek_table";
    public static final String TABLE_NAME3 = "kovcek_items_table";

    public DBConnector(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //sqlite nima posebi datatipov za čas in datum, zato lahko shranjuje te podatke v tekst
        db.execSQL("CREATE TABLE "+TABLE_NAME1+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,potovanjeOd TEXT,potovanjeDo TEXT,datumOdhoda TEXT,casPotovanja TEXT,tipPrevoza TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_NAME2+"(IDKOVCEK INTEGER PRIMARY KEY AUTOINCREMENT,naziv TEXT,createdOn TEXT,potovanje INTEGER,FOREIGN KEY(potovanje) REFERENCES "+TABLE_NAME1+"(ID))");
        db.execSQL("CREATE TABLE "+TABLE_NAME3+"(IDITEM INTEGER PRIMARY KEY AUTOINCREMENT,vsebina TEXT,checked INTEGER,kovcek INTEGER,FOREIGN KEY(kovcek) REFERENCES "+TABLE_NAME2+"(IDKOVCEK))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            onCreate(db);
            db.needUpgrade(newVersion);
        }
    }

    public boolean openConnection (){
        boolean vzpostavljeno = true;
        try {
            db = this.getWritableDatabase();
            vzpostavljeno = true;
        }catch(Exception e){
            vzpostavljeno = false;
            e.printStackTrace();
        }
        return vzpostavljeno;
    }

    public boolean closeConnection(){
        boolean urejeno = true;
        try{
            this.db.close();
            urejeno = true;
        }catch(Exception e){
            urejeno = false;
            e.printStackTrace();
        }
        return urejeno;
    }

    public SQLiteDatabase getDB(){
        return db;
    }

}
