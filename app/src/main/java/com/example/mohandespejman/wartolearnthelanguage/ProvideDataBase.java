package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.ParcelUuid;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by MohandesPejman on 2/9/2018.
 */

public class ProvideDataBase extends SQLiteAssetHelper {

    private static String DATABASE_NAME="dbinformation.db" ;
    private static int DATABASE_VERSION= 1;

    public ProvideDataBase(Context context)
    {


        super(context , DATABASE_NAME , null  , DATABASE_VERSION);

    }
}
