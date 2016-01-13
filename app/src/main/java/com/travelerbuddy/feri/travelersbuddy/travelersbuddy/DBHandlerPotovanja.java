package com.travelerbuddy.feri.travelersbuddy.travelersbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Asura on 30-Dec-15.
 */
public class DBHandlerPotovanja extends AppCompatActivity {

    DBConnector dbConnector;
    SQLiteDatabase mydb;
    private Context context;

    public DBHandlerPotovanja(){}
    public DBHandlerPotovanja(Context context){this.context = context;}

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public boolean insertPotovanje(String potovanjeOd, String potovanjeDo, String datumOdhoda, String casPotovanja, String tipPrevoza){
        boolean brezNapak = true;
        dbConnector = new DBConnector(this.getContext());

        try{
            dbConnector.openConnection();

            mydb = dbConnector.getDB();
            ContentValues values = new ContentValues();
            values.put("potovanjeOd",potovanjeOd);
            values.put("potovanjeDo",potovanjeDo);
            values.put("datumOdhoda",datumOdhoda);
            values.put("casPotovanja",casPotovanja);
            values.put("tipPrevoza",tipPrevoza);

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

        dbConnector = new DBConnector(this.getContext());
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
