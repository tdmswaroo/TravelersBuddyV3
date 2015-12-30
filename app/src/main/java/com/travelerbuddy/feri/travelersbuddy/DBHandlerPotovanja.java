package com.travelerbuddy.feri.travelersbuddy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Asura on 30-Dec-15.
 */
public class DBHandlerPotovanja extends AppCompatActivity {

    DBConnector dbConnector;
    SQLiteDatabase mydb;

    public boolean insertPotovanje(String potovanjeOd, String potovanjeDo, String datumOdhoda, String casPotovanja, String tipPrevoza){
        boolean brezNapak = true;
        dbConnector = new DBConnector(this);

        try{
            dbConnector.openConnection();

            mydb = dbConnector.getDB();
            ContentValues values = new ContentValues();
            values.put("2",potovanjeOd);
            values.put("3",potovanjeDo);
            values.put("4",datumOdhoda);
            values.put("5",casPotovanja);
            values.put("6",tipPrevoza);

            long result = mydb.insert("potovanje_table", null, values);

            if(result == -1)
                brezNapak = false;

        }catch (Exception e){
            brezNapak = false;
            e.printStackTrace();
        }finally {
            dbConnector.closeConnection();
        }

        return brezNapak;

    }

    public Cursor getAllPotovanja(){

        dbConnector = new DBConnector(this);
        Cursor result = null;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            result = mydb.rawQuery("SELECT * FROM potovanje_table",null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
