package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.Random;

/**
 * Created by Pejman on 3/23/2018.
 */

public class ActivityPrice extends AppCompatActivity {

    ImageView Chest1;
    ImageView Chest2;
    ImageView Chest3;
    ImageView Chest4;
    ImageView Chest5;
    ImageView Chest6;
    boolean selectprice = false;

    TextView txtnumberenergy;

    Integer[] dataprice = {50 , 100 , 120 , 20 , 250 , 150};
    Integer[] dataselectprice = new Integer[dataprice.length];

    int countershans = 0;

    MediaPlayer mediaPlayer;
    GetDataBase dataBase ;




    @Override
    protected void onCreate( Bundle savedInstanceState) {

        setContentView(R.layout.activityprice);
        super.onCreate(savedInstanceState);


        try
        {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        catch (Exception e)
        {

        }

        mediaPlayer = MediaPlayer.create(this ,R.raw.openchest);

        setfindviews();
        setearlyinformation();
        setclickviews();

    }


    private void setfindviews()
    {

        Chest1 = (ImageView) findViewById(R.id.chest1);
        Chest2 = (ImageView) findViewById(R.id.chest2);
        Chest3 = (ImageView) findViewById(R.id.chest3);
        Chest4 = (ImageView) findViewById(R.id.chest4);
        Chest5 = (ImageView) findViewById(R.id.chest5);
        Chest6 = (ImageView) findViewById(R.id.chest6);

        txtnumberenergy = (TextView) findViewById(R.id.txtnumberenergy);

    }

    private void setearlyinformation()
    {

        dataBase = new GetDataBase(ActivityPrice.this);
        int energy =dataBase.selectinfint("information" , 1 , "energy");
        txtnumberenergy.setText(energy+"");


    }

    private void setclickviews()
    {

        ImageView[] imgchest = {Chest1 , Chest2 , Chest3 ,Chest4 , Chest5 , Chest6};

        for (final ImageView view : imgchest)
        {

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mediaPlayer.start();
                    view.setImageResource(R.drawable.chestopen);
                    view.setEnabled(false);

                    Random random = new Random();
                    boolean replynum = false;
                    int shans = 0;

                    do
                    {


                        shans = random.nextInt(6);

                        try
                        {
                            for (int num : dataselectprice)
                            {
                                if(num == shans)
                                    replynum = true;
                                else
                                    replynum = false;
                            }


                        }catch (Exception e)
                        {

                        }


                    }while (replynum);

                    dataselectprice[countershans] = shans;
                    countershans++;



                    final AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrice.this);
                    builder.setView(R.layout.dialog_layut);
                    builder.setCancelable(false);
                    dialog = builder.create();
                    dialog.show();

                    TextView txtmessage = (TextView) dialog.findViewById(R.id.txtdialog);
                    ImageView imgdialog = (ImageView) dialog.findViewById(R.id.imgdialog);
                    Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
                    //Button btnno = (Button) dialog.findViewById(R.id.btnno);

                    if(dataprice[shans] == 250)
                        txtmessage.setText("صندوق ویژه" + dataprice[shans] + "سکه "
                        );
                     else
                        txtmessage.setText( dataprice[shans] + "سکه ");
                    imgdialog.setImageResource(R.drawable.coin);

                    btnyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();


                            if(!(selectprice))
                            {

                                final AlertDialog dialog1;
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityPrice.this);
                                builder1.setView(R.layout.dialog_layut);
                                builder1.setCancelable(false);
                                dialog1 = builder1.create();
                                dialog1.show();

                                TextView txtmessage1 = (TextView) dialog1.findViewById(R.id.txtdialog);
                                ImageView imgdialog1 = (ImageView) dialog1.findViewById(R.id.imgdialog);
                                Button btnyes1 = (Button) dialog1.findViewById(R.id.btnyes);
                                Button btnno = (Button) dialog1.findViewById(R.id.btnno);

                                txtmessage1.setText("مژده  مژده  میتونی با دیدن یک تبلیغ شانس خودت را دوباره امتحان کنی و برنده صندوق ویژه شوی");
                                imgdialog1.setImageResource(R.drawable.happy);
                                btnno.setVisibility(View.VISIBLE);



                                btnyes1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        final Handler handler = new Handler();
                                        final ShowVidioTapsell vidioTapsell = new ShowVidioTapsell(ActivityPrice.this);
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

                                                            dialog1.dismiss();
                                                        }

                                                    }
                                                });
                                            }
                                        }).start();




                                    }
                                });

                                btnno.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });


                            }
                            else
                                finish();

                            selectprice = true;


                        }
                    });


                    int energy = dataBase.selectinfint("information" , 1 , "energy");
                    dataBase.updateint("information" , "energy" , energy + dataprice[shans] , 1 );


                    setearlyinformation();







                }
            });
        }

    }



}
