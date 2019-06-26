package com.example.mohandespejman.wartolearnthelanguage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.vincan.rotatecircleimageview.RotateCircleImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import miaoyongjun.pagetransformer.MagicTransformer;
import miaoyongjun.pagetransformer.TransitionEffect;

/**
 * Created by MohandesPejman on 2/12/2018.
 */

public class ActivitySelectHero extends AppCompatActivity {



    public static TransitionEffect transitionEffect = TransitionEffect.Flip;


    Integer[] imageUrls = new Integer[]{
            R.drawable.hero1 , R.drawable.hero2 , R.drawable.hero3 , R.drawable.hero4 , R.drawable.hero5 ,
            R.drawable.hero6 , R.drawable.hero7 , R.drawable.hero8 , R.drawable.hero9 ,R.drawable.hero10
            };
    List<View> mData = new ArrayList<>();


    RotateCircleImageView imgstart;
    RotateCircleImageView imgfreeenergy;
    RotateCircleImageView imgbuyenergy;

    ViewPager viewPager;

    int surface;
    int energy ;
    SharedPreferences sharedPreferences ;
    GetDataBase dataBase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selecthero);



        try {

            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }
        catch (Exception e) {

        }

        sharedPreferences = getSharedPreferences("insidedata" , Context.MODE_PRIVATE);
        dataBase = new GetDataBase(this);

        setsurfacefromdata();

        setfindviews();

        setviewpager();

        checkpriceday();


        checkshownotification();

        setclickviews();









    }



    private void setsurfacefromdata()
    {


        surface=dataBase.selectinfint("information" , 1 , "surface");
        G.surface = surface;


    }


    //set find
    private void setfindviews()
    {


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgstart = (RotateCircleImageView) findViewById(R.id.imgstart);
        imgfreeenergy = (RotateCircleImageView) findViewById(R.id.imgfreeenergy);
        imgbuyenergy = (RotateCircleImageView) findViewById(R.id.imgbuyenergy);




    }


    //set pager
    private void setviewpager()
    {


        getLayoutData(mData);
        viewPager.setAdapter(new MyAdapter(mData));
        viewPager.setPageTransformer(true, MagicTransformer.getPageTransformer(transitionEffect));
        viewPager.setOffscreenPageLimit(imageUrls.length);



    }



    private void checkpriceday()
    {

        if(sharedPreferences.contains("checkprice"))
        {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean price = sharedPreferences.getBoolean("checkprice" , false);
            if(price)
            {


                setdialogpriceday();
                editor.putBoolean("checkprice" , false);
                editor.apply();



            }

        }
    }


    private void setdialogpriceday()
    {



        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySelectHero.this);
        builder.setView(R.layout.dialog_layut);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();

        TextView txtmessage = (TextView) dialog.findViewById(R.id.txtdialog);
        ImageView imgdialog = (ImageView) dialog.findViewById(R.id.imgdialog);
        Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
        //Button btnno = (Button) dialog.findViewById(R.id.btnno);


        txtmessage.setText( "جایزه شما 25 سکه");
        imgdialog.setImageResource(R.drawable.coin);

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                energy = dataBase.selectinfint("information" , 1 , "energy");
                dataBase.updateint("information" , "energy" , energy + 25 , 1 );


                final AlertDialog dialog1;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivitySelectHero.this);
                builder1.setView(R.layout.dialog_layut);
                builder1.setCancelable(false);
                dialog1 = builder1.create();
                dialog1.show();

                TextView txtmessage1 = (TextView) dialog1.findViewById(R.id.txtdialog);
                ImageView imgdialog1 = (ImageView) dialog1.findViewById(R.id.imgdialog);
                Button btnyes1 = (Button) dialog1.findViewById(R.id.btnyes);
                Button btnno = (Button) dialog1.findViewById(R.id.btnno);

                txtmessage1.setText("مژده  مژده  میتونی با دیدن یک تبلیغ جایزه امروزت را دوبرابر (2x) کنی");
                imgdialog1.setImageResource(R.drawable.happy);
                btnno.setVisibility(View.VISIBLE);



                btnyes1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Handler handler = new Handler();
                        final ShowVidioTapsell vidioTapsell = new ShowVidioTapsell(ActivitySelectHero.this);
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

                                            energy = dataBase.selectinfint("information" , 1 , "energy");
                                            dataBase.updateint("information" , "energy" , energy + 25 , 1 );
                                            dialog1.dismiss();
                                            TastyToast.makeText(ActivitySelectHero.this , "سکه دریافتی امروز شما دوبرابر شد" ,  TastyToast.LENGTH_LONG , TastyToast.SUCCESS);

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
                        dialog1.dismiss();
                    }
                });


            }
        });





    }

    private void checkshownotification()
    {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!(sharedPreferences.contains("numshow")))
        {


            editor.putInt("numshow" , 0);
            editor.apply();

        }


        if(!(sharedPreferences.contains("checknotif")))
        {


            editor.putBoolean("checknotif" , true);
            editor.apply();
            setnotification();

        }

    }



    private void setnotification()
    {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH , 1);


        long time = calendar.getTimeInMillis();

        Intent intent = new Intent(getApplicationContext() , BroadCastShowNotification.class);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(this , 0 , intent , 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP , time , AlarmManager.INTERVAL_DAY , pendingIntent);


    }







    //set click
    private void setclickviews()
    {



        imgstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivitySelectHero.this , ActivityMain.class);
                G.mysurface = surface;
                startActivity(intent);


            }
        });

        imgbuyenergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ActivitySelectHero.this , ActivityBuy.class));

            }
        });

        imgfreeenergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ActivitySelectHero.this, ActivityFreeEnergy.class));

            }
        });


    }










    //پر کردن view در List
    private void getLayoutData(List<View> data) {
        View view = LayoutInflater.from(ActivitySelectHero.this).inflate(R.layout.layout_itemselecthero, null);
        for (int i = 0; i < imageUrls.length ; i++) {


            mData.add(view);

        }
    }





    //Set Adaptor
    private class MyAdapter extends PagerAdapter {

        List<View> mList = null;


        MyAdapter(List<View> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {


            container.removeView((View) object);

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {



            View view = LayoutInflater.from(ActivitySelectHero.this).inflate(R.layout.layout_itemselecthero,null);
            ImageView imagemain = (ImageView) view.findViewById(R.id.imgcardselethero);
            ImageView imageback = (ImageView) view.findViewById(R.id.imgbackcardselethero);
            ImageView imagefront = (ImageView) view.findViewById(R.id.imgfrontcardselethero);
            ImageView imagelock = (ImageView) view.findViewById(R.id.imglock);

            CardView cardback = (CardView) view.findViewById(R.id.cartback);
            CardView cardfront = (CardView) view.findViewById(R.id.cartfront);

            imagemain.setBackgroundResource(imageUrls[position]);
            if(position == 0)
            {
                cardback.setVisibility(View.INVISIBLE);
            }
            else
            {

                cardback.setVisibility(View.VISIBLE);
                imageback.setBackgroundResource(imageUrls[position - 1]);
            }

            if(position == imageUrls.length - 1)
            {
                cardfront.setVisibility(View.INVISIBLE);
            }
            else
            {

                cardfront.setVisibility(View.VISIBLE);
                imagefront.setBackgroundResource(imageUrls[position + 1]);
            }




            if(position > surface - 1)
            {

                imagelock.setImageResource(R.drawable.lock);


            }
            else
            {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Intent intent = new Intent(ActivitySelectHero.this , ActivityMain.class);
                        G.mysurface = position + 1;
                        startActivity(intent);

                    }
                });
            }





            container.addView(view);
            return view;



        }




    }

    @Override
    protected void onRestart() {
        if(G.surface > G.mysurface)
        {

            setsurfacefromdata();
            setviewpager();
            setclickviews();

        }
        super.onRestart();
    }
}
