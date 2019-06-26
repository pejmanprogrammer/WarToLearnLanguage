package com.example.mohandespejman.wartolearnthelanguage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

/**
 * Created by MohandesPejman on 1/28/2018.
 */

public class ActivitySelectPlayer extends AppCompatActivity {


    RecyclerView recycleplayer ;
    TextView txtenergy;

    ImageView imgcardgame;
    ImageView imgplaygame;

    ImageView player1;
    ImageView player2;
    ImageView player3;
    ImageView player4;
    ImageView imgrefresh;
    ImageView imgseevidio;

    boolean twentyfivepercent = false;
    int energy;

    GetDataBase dataBase;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_player);


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

        setrecycle();

        if (!(sharedPreferences.contains("helpselectplayer")))
            sethelp();



    }

    public void setfindviews()
    {

        recycleplayer = (RecyclerView) findViewById(R.id.recycleplayers);
        txtenergy = (TextView) findViewById(R.id.txtenergy);
        imgcardgame = (ImageView) findViewById(R.id.imgcardgame);

        player1 = (ImageView) findViewById(R.id.player1);
        player2 = (ImageView) findViewById(R.id.player2);
        player3 = (ImageView) findViewById(R.id.player3);
        player4 = (ImageView) findViewById(R.id.player4);
        imgrefresh = (ImageView) findViewById(R.id.imgrefresh);
        imgplaygame = (ImageView) findViewById(R.id.imgplaygame);
        imgseevidio = (ImageView) findViewById(R.id.imgseevidio);

    }

    public void setearlyinformation()
    {

        sharedPreferences = getSharedPreferences("insidedata" , Context.MODE_PRIVATE);

        Tapsell.initialize(ActivitySelectPlayer.this ,
                "pifqkhnhljogiqmkshhrckbnoldrtdmfjbsoefcbcsgkessporjdhckfifhmnarmsrsajj");


        dataBase = new GetDataBase(ActivitySelectPlayer.this);
        energy = dataBase.selectinfint("information" , 1 , "energy");
        txtenergy.setText(energy+"");

    }


    public void setrecycle()
    {


        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivitySelectPlayer.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recycleplayer.setLayoutManager(layoutManager);

       AdaptorRecyclePlayer adaptorRecyclePlayer = new AdaptorRecyclePlayer(ActivitySelectPlayer.this , player1 , player2 , player3 , player4 , imgrefresh , txtenergy
        , twentyfivepercent);
        recycleplayer.setAdapter(adaptorRecyclePlayer);



    }


    public void setclickviews()
    {

        imgcardgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivitySelectPlayer.this , ActivityCards.class);
                intent.putExtra("customcard" , false);
                startActivity(intent);

            }
        });

        imgplaygame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(G.playersrobat.size() == 4)
                {
                    startActivity(new Intent(ActivitySelectPlayer.this , ActivityTest.class ));
                    finish();
                }
                else
                {
                    TastyToast.makeText(getApplicationContext() , "تعداد جنگجویان کمتر از 4 تا است"  , TastyToast.LENGTH_SHORT , TastyToast.INFO);
                }

            }
        });

        imgseevidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySelectPlayer.this);
                builder.setView(R.layout.dialog_layut);
                dialog = builder.create();
                dialog.show();

                TextView txtmessage = (TextView) dialog.findViewById(R.id.txtdialog);
                ImageView imgdialog = (ImageView) dialog.findViewById(R.id.imgdialog);
                Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
                Button btnno = (Button) dialog.findViewById(R.id.btnno);

                txtmessage.setText("20% تخفیف قیمت کارت ها با مشاهده تبلیغ کوتاه");
                imgdialog.setImageResource(R.drawable.seevidio);
                btnno.setVisibility(View.VISIBLE);



                btnyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Handler handler = new Handler();
                        final ShowVidioTapsell vidioTapsell = new ShowVidioTapsell(ActivitySelectPlayer.this);
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
                                            TastyToast.makeText(getApplicationContext(), "تخفیف برای شما اعمال شد", TastyToast.LENGTH_LONG , TastyToast.SUCCESS);
                                            twentyfivepercent = true;
                                            setrecycle();
                                        }

                                    }
                                });
                            }
                        }).start();



                        dialog.dismiss();
                    }
                });

                btnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });





    }

    private void sethelp()
    {

        View[] views = {imgcardgame , imgseevidio , imgplaygame};
        String[] titles = { "کارت های مراحل " , "بخش تخفیف" , "یادت نره"};
        String[] descriptions = {"قبل از شروع هر مرحله کارت ها را از اینجا بخوان تا کامل اماده بشی  با برد هر مرحله چند کارت جدید میگیری" ,
                "در این قسمت هر بار میتونی 20% تخفیف برای خرید یا ارتقا کارت های جنگجویان با مشاهده تبلیغ به دست بیاری" ,
                "هر کارت جنگجو دارای سه ویژگی که به ترتیب مهارت جنجگو و انرژِی جنگجو و جادوی جنگجو که با جادو میتونی باهاش گزینه ها را حذف کنی"
        };
        HelpView helpView = new HelpView(ActivitySelectPlayer.this);
        helpView.showhelp(views , titles , descriptions);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkhelpmain" , true);
        editor.putBoolean("helpselectplayer" , true);
        editor.apply();


    }






}
