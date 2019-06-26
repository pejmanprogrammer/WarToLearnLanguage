package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by MohandesPejman on 2/7/2018.
 */

public class ActivityFreeEnergy extends AppCompatActivity {


    RelativeLayout laypointapp;
    RelativeLayout layshowvidio;

    GetDataBase dataBase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_freeenergy);




        try
        {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        catch (Exception e)
        {

        }


        setfindviews();

        setearlyinformation();

        setclickviews();

    }

    private void setearlyinformation()
    {

        dataBase = new GetDataBase(ActivityFreeEnergy.this);
        sharedPreferences = getSharedPreferences("insidedata" , Context.MODE_PRIVATE);

    }


    private void setfindviews()
    {


        laypointapp = (RelativeLayout) findViewById(R.id.pointfree);
        layshowvidio = (RelativeLayout) findViewById(R.id.showvidio);

    }

    private void setclickviews()
    {



        laypointapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try
                {




                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(Uri.parse("bazaar://details?id=" + "com.example.mohandespejman.wartolearnthelanguage"));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);

                    if(!(sharedPreferences.contains("point")))
                    {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("point" , true);
                        editor.apply();

                        int energy= dataBase.selectinfint("information" , 1 , "energy");
                        dataBase.updateint("information" , "energy" , energy + 75 , 1);

                    }



                }
                catch (Exception e)
                {

                }


            }
        });


        layshowvidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final Handler handler = new Handler();
                final ShowVidioTapsell vidioTapsell = new ShowVidioTapsell(ActivityFreeEnergy.this);
                vidioTapsell.providevidio();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!(vidioTapsell.showendvidio) && !(vidioTapsell.showerrorvidio))
                        {
                            try {

                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(vidioTapsell.showendvidio)
                                {

                                    int energy= dataBase.selectinfint("information" , 1 , "energy");
                                    dataBase.updateint("information" , "energy" , energy + 50 , 1);
                                }

                            }
                        });
                    }
                }).start();







            }
        });


    }



    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }



}
