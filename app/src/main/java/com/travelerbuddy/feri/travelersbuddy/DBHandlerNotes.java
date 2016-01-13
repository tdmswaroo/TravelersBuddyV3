package com.travelerbuddy.feri.travelersbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Asura on 08-Jan-16.
 */
public class DBHandlerNotes extends AppCompatActivity {

    DBConnector dbConnector;
    SQLiteDatabase mydb;
    private Context context;

    public DBHandlerNotes(){}
    public DBHandlerNotes(Context context){this.context = context;}

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public boolean insertNovKovcek(String naziv, String createdOn, int idPotovanje){

        boolean brezNapak = true;
        dbConnector = new DBConnector(this.getContext());

        try{
            dbConnector.openConnection();

            mydb = dbConnector.getDB();
            ContentValues values = new ContentValues();
            values.put("naziv",naziv);
            values.put("createdOn",createdOn);
            values.put("potovanje",idPotovanje);

            long result = mydb.insert("kovcek_table", null, values);

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

    public Cursor getAllNotes(){
        dbConnector = new DBConnector(this.getContext());
        Cursor result = null;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            result = mydb.rawQuery("SELECT * FROM kovcek_table",null);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //dbConnector.closeConnection();
        }

        return result;
    }

    public Cursor getAllNotesZaPotovanje(int idPotovanje){
        dbConnector = new DBConnector(this.getContext());
        Cursor result = null;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            result = mydb.rawQuery("SELECT * FROM kovcek_table WHERE potovanje="+idPotovanje,null);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbConnector.closeConnection();
        }

        return result;
    }

    public boolean checkNote(int idNote){
        dbConnector = new DBConnector(this.getContext());
        boolean result = true;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            mydb.rawQuery("UPDATE kovcek_items_table SET checked = 1 WHERE ID="+idNote,null);

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }finally {
            dbConnector.closeConnection();
        }

        return result;
    }

    public boolean unCheckNote(int idNote){
        dbConnector = new DBConnector(this.getContext());
        boolean result = true;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            mydb.rawQuery("UPDATE kovcek_items_table SET checked = 0 WHERE ID="+idNote,null);

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }finally {
            dbConnector.closeConnection();
        }

        return result;
    }

}
