package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MohandesPejman on 2/9/2018.
 */

public class GetDataBase  {

    Context context;
    ProvideDataBase provideDataBase;
    SQLiteDatabase database;

    public GetDataBase(Context context)
    {


            try
            {


                this.context = context;
                provideDataBase = new ProvideDataBase(this.context);
                database = provideDataBase.getReadableDatabase();


            }
            catch (Exception e)
            {

            }








    }


    public String selectinfstring(String table , int id , String column)
    {



        String myinf="";
        try
        {

            Cursor cursor =database.rawQuery(" Select * From '" + table +"' WHERE id=" + id  , null);
            while (cursor.moveToNext())
            {

                myinf = cursor.getString(cursor.getColumnIndex(column));

            }





        }
        catch (Exception e)
        {

        }



            return myinf;




    }



    public int selectinfint(String table , int id , String column)
    {



        int myinf=0;
        try
        {

            Cursor cursor =database.rawQuery(" Select * From '" + table +"' WHERE id=" + id  , null);
            while (cursor.moveToNext())
            {

                myinf = cursor.getInt(cursor.getColumnIndex(column));
            }




        }
        catch (Exception e)
        {

        }



        return myinf;



    }


    public List<Integer> getlistdata(String table , String column )
    {

        List<Integer> list = new ArrayList<>();
        try
        {

            int i =0;
            Cursor cursor =database.rawQuery(" Select * From '" + table +"'"   , null);
            while (cursor.moveToNext())
            {

                int field= cursor.getInt(cursor.getColumnIndex(column));
                list.add(i , field);
                i++;

            }
        }
        catch (Exception e)
        {

        }


        return list;

    }

    public List<Integer> getlistdata(String table , String column  , String mycoloum , int myinf , String twoocoloum ,int mylevel)
    {

        List<Integer> list = new ArrayList<>();
        try
        {

            int i =0;
            Cursor cursor =database.rawQuery(" Select * From '" + table +"' Where " + mycoloum + " = " + myinf + " AND " +
                    twoocoloum + " <= " + mylevel, null);
            while (cursor.moveToNext())
            {


                int field= cursor.getInt(cursor.getColumnIndex(column));
                list.add(i , field);
                i++;

            }
        }
        catch (Exception e)
        {

        }


        return list;

    }








    public void insert(String table , String word , String trans , int like)
    {

        try
        {
            database.execSQL("INSERT INTO " + table +" (word , trans , like)  VALUES ('"+word+"','"+trans+"',0 )");
        }
        catch (Exception e)
        {
            Log.i("Log" , e.toString());
        }


    }

    public void updateint(String table , String coloum , int mount , int id)
    {
        try
        {
            database.execSQL("UPDATE " + table + " Set "+coloum+" = " + mount + " Where id = " + id + "");
        }
        catch (Exception e)
        {
            Log.i("Log" , e.toString());
        }
    }

    public void updatestring(String table , String coloum , String mount , int id)
    {
        try
        {
            database.execSQL("UPDATE " + table + " Set "+coloum+" = '"+mount+"' Where id = " + id + "");
        }
        catch (Exception e)
        {
            Log.i("Log" , e.toString());
        }
    }







    public void delete(String table , int id)
    {

            Log.i("Log" , id+"");
            database.execSQL("DELETE From " + table + " Where id = " + id + "");

    }



}
