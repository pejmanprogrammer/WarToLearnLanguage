package com.example.mohandespejman.wartolearnthelanguage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by MohandesPejman on 2/5/2018.
 */

public class ActivityAnswerTest extends AppCompatActivity {

    RecyclerView recyclefriend;
    RecyclerView recycleenemy;
    Integer[] numsshans = new Integer[G.playersrobat.size()];
    Integer[] numsshansenemy = new Integer[G.playersrobat.size()];


    GetDataBase dataBase;
    int QusTrue = 0;
    int Falsechoose=0;
    int percendheroenemy=0;

    SharedPreferences sharedPreferences;
    AlertDialog.Builder builder;
    MediaPlayer mediaPlayer;

    Handler handler ;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_answer_test);

        try
        {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        catch (Exception e)
        {

        }

        sharedPreferences = getSharedPreferences("insidedata" , Context.MODE_PRIVATE);
        dataBase = new GetDataBase(this);

        handler = new Handler();
        numsshans =setarrayrandom();
        numsshansenemy = setarrayrandom();

        setearlyinformation();
        setfindviews();


        setdialgs();

        LinearLayoutManager managerfriend = new LinearLayoutManager(ActivityAnswerTest.this);
        managerfriend.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager managerenemy = new LinearLayoutManager(ActivityAnswerTest.this);
        managerenemy.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclefriend.setLayoutManager(managerfriend);
        recycleenemy.setLayoutManager(managerenemy);

        AdaptorRecycleAnswer adpRecycleAnsFriend = new AdaptorRecycleAnswer(ActivityAnswerTest.this  , QusTrue , Falsechoose ,true , numsshans , numsshansenemy , percendheroenemy);
        AdaptorRecycleAnswer adpRecycleAnsEnemy = new AdaptorRecycleAnswer(ActivityAnswerTest.this  , QusTrue ,Falsechoose, false , numsshans , numsshansenemy , percendheroenemy);

        recyclefriend.setAdapter(adpRecycleAnsFriend);
        recycleenemy.setAdapter(adpRecycleAnsEnemy);



    }

    private void setearlyinformation()
    {

        try
        {

            Bundle bundle = getIntent().getExtras();
            QusTrue = bundle.getInt("numans");
            Falsechoose = bundle.getInt("falsechoose");


        }
        catch (Exception e)
        {

        }

    }

    private void setfindviews()
    {

        recyclefriend = (RecyclerView) findViewById(R.id.recyclefriend);
        recycleenemy =(RecyclerView) findViewById(R.id.recycleenemy);

    }

    private Integer[] setarrayrandom()
    {

        Integer[] shansdata = new Integer[G.playersrobat.size()];

        Random random = new Random();
        for (int i = 0; i <G.playersrobat.size() ; i++)
        {
            int shans = random.nextInt(6);
            shansdata[i] = shans;
        }

        int surface=dataBase.selectinfint("information" , 1 , "surface");
        int level =dataBase.selectinfint("information" , 1 , "level");

        if(surface == 1 && level <=4 )
            percendheroenemy = random.nextInt(10)+1;
        else
            percendheroenemy = random.nextInt(50)+1;


        return shansdata;

    }

    private void setdialgs()
    {
        int skillfriend=0;
        int speedfriend=0;
        int skillenemy=0;
        int speedenemy=0;

        skillfriend += (QusTrue * 10) + Falsechoose;
        skillenemy += (50 + percendheroenemy);


        for (int i = 0; i <G.playersrobat.size() ; i++)
        {

            int id = G.playersrobat.get(i);
            int skillf =dataBase.selectinfint("infrobat" , id + 1 , "skill");
            int speedf = dataBase.selectinfint("infrobat" , id + 1 , "speed");
            int skille =dataBase.selectinfint("infrobatenemy" , i+1  , "skill");
            int speede = dataBase.selectinfint("infrobatenemy" , i+1 , "speed");

            skillfriend += (skillf + numsshans[i]);
            speedfriend += (speedf + numsshans[i]);
            skillenemy  += (skille + numsshansenemy[i]);
            speedenemy  += (speede + numsshansenemy[i]);



        }



        builder = new AlertDialog.Builder(this);




        builder.setTitle("شیاطین" + "       VS      " + "تیم شما" + "\n\n");
        builder.setMessage("              "+skillfriend + "        VS      " + skillenemy + "\n\n" + "              " + speedfriend
        +  "        VS      " + speedenemy );
        if(skillfriend > skillenemy &&  speedfriend > speedenemy)
        {
            mediaPlayer =MediaPlayer.create(this , R.raw.win);
            mediaPlayer.start();
            builder.setIcon(R.drawable.happy);

        }
        else
        {
            mediaPlayer =MediaPlayer.create(this , R.raw.sad);
            mediaPlayer.start();
            builder.setIcon(R.drawable.sad);
        }

        builder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



                AlertDialog dialog = builder.create();
                dialog.show();




            if(G.mylevel == G.level && G.mysurface == G.surface)
            {

                int newnumprg = 0;
                int numprg =0;
                if(sharedPreferences.contains("numprg"))
                {
                    numprg = sharedPreferences.getInt("numprg" , 0);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(skillenemy > skillfriend && QusTrue != 10 || speedenemy > speedfriend && QusTrue != 10)
                    newnumprg = numprg + 20;
                else if(skillenemy > skillfriend && QusTrue == 10 || speedenemy > speedfriend && QusTrue == 10)
                    newnumprg = numprg + 25;
                else if(QusTrue == 10)
                    newnumprg = numprg + 40;
                else
                    newnumprg = numprg + 30;

                if(newnumprg >= 100)
                    editor.putInt("numprg" , 0 );
                else
                    editor.putInt("numprg" , newnumprg);

                editor.apply();

                if(newnumprg > 100)
                    newnumprg = 100;



                final ProgressDialog progressDialog = new ProgressDialog(this , R.style.AppCompatAlertDialogStyle);
                progressDialog.setCancelable(false);
                progressDialog.setIcon(R.drawable.chestclose);
                progressDialog.setTitle("صندوق جایزه");
                progressDialog.setMax(100);
                if(newnumprg == 100)
                {
                    progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "دریافت جایزه", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(ActivityAnswerTest.this , ActivityPrice.class));
                        }
                    });
                }
                else
                {
                    progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "باشه", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }


                progressDialog.setProgress(numprg);
                progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.progresschest));
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();

                final int last = numprg;
                final int now = newnumprg;

                final Thread t = new Thread() {
                    @Override
                    public void run() {
                        int jumpTime = last;

                        while(jumpTime < now) {
                            try {
                                sleep(150);
                                jumpTime += 1;
                                progressDialog.setProgress(jumpTime);


                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {



                                if(now == 100)
                                {
                                    MediaPlayer player = MediaPlayer.create(ActivityAnswerTest.this , R.raw.openchest);
                                    player.start();
                                    progressDialog.setIcon(R.drawable.chestopen);
                                }








                            }
                        });
                    }
                };
                t.start();



                if(skillfriend> skillenemy  && speedfriend > speedenemy )
                {

                    G.level++;
                    int mylevel = G.level;
                    dataBase.updateint("information" , "level" , mylevel , 1);

                }



            }












    }


}
