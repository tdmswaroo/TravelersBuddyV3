package com.travelerbuddy.feri.travelersbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    public boolean insertNovItem(String vsebina, String checked, int idKovcek){

        boolean brezNapak = true;
        dbConnector = new DBConnector(this.getContext());

        try{
            dbConnector.openConnection();

            mydb = dbConnector.getDB();
            ContentValues values = new ContentValues();
            values.put("vsebina",vsebina);
            values.put("checked",checked);
            values.put("kovcek",idKovcek);

            long result = mydb.insert("kovcek_items_table", null, values);

            if(result == -1)
                brezNapak = false;

        }catch (Exception e){
            brezNapak = false;
            e.printStackTrace();
        }finally {
            //dbConnector.closeConnection();
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

    public Cursor getNoteById(int id){
        dbConnector = new DBConnector(this.getContext());
        Cursor result = null;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            result = mydb.rawQuery("SELECT * FROM kovcek_table WHERE IDKOVCEK="+id,null);

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

    public Cursor getAllItemsZaKovcek(int idKovcek){
        dbConnector = new DBConnector(this.getContext());
        Cursor result = null;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            result = mydb.rawQuery("SELECT * FROM kovcek_items_table WHERE kovcek="+idKovcek,null);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //dbConnector.closeConnection();
        }

        return result;
    }

    public long checkNote(int idNote, ContentValues values){
        dbConnector = new DBConnector(this.getContext());
        int result = 0;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            mydb.execSQL("UPDATE kovcek_items_table SET checked = 'true' WHERE IDITEM = " + idNote);
            Log.d("CheckNote", String.valueOf(values));
            result = 1;
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }finally {
            //dbConnector.close();
        }

        return result;
    }

    public long unCheckNote(int idNote, ContentValues values){
        dbConnector = new DBConnector(this.getContext());
        long result = 0;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            mydb.execSQL("UPDATE kovcek_items_table SET checked = 'false' WHERE IDITEM = "+idNote);
            Log.d("UnCheckNote", String.valueOf(values));
            result = 1;
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }finally {
            //dbConnector.close();
        }

        return result;
    }

    public long updateItem(int idNote, String vsebina){
        dbConnector = new DBConnector(this.getContext());
        long result = 0;

        try {

            dbConnector.openConnection();
            mydb = dbConnector.getDB();
            mydb.execSQL("UPDATE kovcek_items_table SET vsebina = '"+vsebina+"' WHERE IDITEM = "+idNote);
            Log.d("UpdateNote", vsebina);
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }finally {
            //dbConnector.close();
        }

        return result;
    }

}
