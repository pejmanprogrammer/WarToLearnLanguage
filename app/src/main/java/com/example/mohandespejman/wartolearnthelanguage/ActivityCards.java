package com.example.mohandespejman.wartolearnthelanguage;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bakerj.infinitecards.AnimationTransformer;
import com.bakerj.infinitecards.CardItem;
import com.bakerj.infinitecards.InfiniteCardView;
import com.bakerj.infinitecards.ZIndexTransformer;
import com.bakerj.infinitecards.transformer.DefaultCommonTransformer;
import com.bakerj.infinitecards.transformer.DefaultTransformerToBack;
import com.bakerj.infinitecards.transformer.DefaultTransformerToFront;
import com.bakerj.infinitecards.transformer.DefaultZIndexTransformerCommon;
import com.github.florent37.materialtextfield.MaterialTextField;

import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MohandesPejman on 1/30/2018.
 */

public class ActivityCards extends AppCompatActivity {

    boolean night = false;
    boolean customcard = false;
    boolean addcard = false;
    boolean seetrans = true;
    boolean clickpartlike = false;
    boolean cardlike = false;




    ImageView imgnight;
    ImageView imgeye;
    ImageView imgpartlike;
    ImageView imgadd;


    LinearLayout layoutcard;

    Button btnpre;
    Button btnnext;
    Button btnrestart;




    EditText edtword;
    EditText edttrans;

    MaterialTextField matworld;
    MaterialTextField mattrans;

    TextView txtallpage;
    TextView txtof;

    GetDataBase dataBase ;



    ArrayList<informationinfitecard> infdata = new ArrayList<>();


    private AdaptorInfinitecard  mAdapter;

    private int[] resId = {
             R.color.cardcolor1 , R.color.cardcolor2  , R.color.cardcolor3  , R.color.cardcolor4 , R.color.cardcolor5};
    InfiniteCardView mCardView;






    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cards);


        try {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } catch (Exception e) {

        }


        Bundle bundle = getIntent().getExtras();
        customcard = bundle.getBoolean("customcard");

        infdata = new ArrayList<>();
        dataBase = new GetDataBase(ActivityCards.this);

        setfindviews();

        if(customcard)
            setearlyinformation(false , "customcard");
        else
            setearlyinformation(false , "matchcard");



        setclickviews();



        if(customcard)
            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , true , txtallpage);
        else
            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , false , txtallpage);




        mCardView.setAdapter(mAdapter);
















    }





        private void setfindviews()
        {

            mCardView = (InfiniteCardView) findViewById(R.id.infinitecard);


            imgnight = (ImageView) findViewById(R.id.imgnightpage);
            imgeye = (ImageView) findViewById(R.id.imgeye);
            imgpartlike = (ImageView) findViewById(R.id.imgpartlike);

            btnnext = (Button) findViewById(R.id.btnnext);
            btnpre = (Button) findViewById(R.id.btnpre);
            btnrestart = (Button) findViewById(R.id.btnrestart);

            imgadd = (ImageView) findViewById(R.id.imgadd);


            edtword = (EditText) findViewById(R.id.edtword);
            edttrans = (EditText) findViewById(R.id.edttrans);

            mattrans = (MaterialTextField) findViewById(R.id.mattrans);
            matworld = (MaterialTextField) findViewById(R.id.matworld);


            txtallpage= (TextView) findViewById(R.id.txtallpage);
            txtof = (TextView) findViewById(R.id.txtof);

            layoutcard = (LinearLayout) findViewById(R.id.layoutcard);

        }



        private void setearlyinformation(boolean seelike , String table)
        {





                int i = 0;
                List<Integer> myid;

                if(customcard)
                    myid = dataBase.getlistdata(table , "id");
                else
                {
                    myid = dataBase.getlistdata(table , "id" , "stage" , G.mysurface , "levelnow" , G.mylevel);
                }





                for (int id : myid)
                {


                    try
                    {


                        String word = dataBase.selectinfstring(table , id , "word");
                        String trans= dataBase.selectinfstring(table , id , "trans");
                        int  like = dataBase.selectinfint(table , id , "like");





                        if(seelike)
                        {
                            if(like == 1)
                            {


                                infdata.add(i , new informationinfitecard(word , trans , like , id));
                                i++;

                            }
                        }
                        else
                        {
                            infdata.add(i , new informationinfitecard(word , trans , like , id));
                            i++;
                        }





                    }
                    catch (Exception e)
                    {

                    }


                }



                txtallpage.setText(infdata.size() + "");



            if(!customcard)
                imgadd.setVisibility(View.INVISIBLE);





        }


        private void setclickviews()
        {


            btnnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(addcard)
                    {


                        if(!(edtword.getText().toString().equals("")) && !(edttrans.getText().toString().equals("")))
                        {


                            dataBase.insert("customcard" , edtword.getText().toString() , edttrans.getText().toString() , 0);
                            hideaddcard();
                            infdata.clear();
                            if(customcard)
                                setearlyinformation(false , "customcard");
                            else
                                setearlyinformation(false , "matchcard");

                            if(infdata.size() == 1)
                            {
                                TastyToast.makeText(ActivityCards.this , "کارت مورد نظر افزوده شد"  , TastyToast.LENGTH_SHORT , TastyToast.SUCCESS);
                                finish();
                            }
                            else
                            {
                                if(customcard)
                                    mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , true , txtallpage);
                                else
                                    mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , false , txtallpage);
                                mCardView.setAdapter(mAdapter);
                            }



                        }
                    }
                    else
                    {

                        try
                        {

                            setnextcard();
                            mCardView.bringCardToFront(1);


                        }
                        catch (Exception e)
                        {

                        }




                    }


                }
            });

            

            btnpre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(addcard)
                    {

                        hideaddcard();

                    }
                    else
                    {


                        try
                        {
                            setbackcart();
                            mCardView.bringCardToFront(mAdapter.getCount() - 1);



                        }
                        catch (Exception e)
                        {

                        }






                    }


                }
            });

            btnrestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(addcard)
                    {

                        Intent intent = new Intent(ActivityCards.this , GoogleTranslate.class);
                        intent.putExtra("word" , edtword.getText().toString());
                        startActivity(intent);


                    }
                    else
                    {

                        mCardView.setAdapter(mAdapter);


                    }


                }
            });

            imgeye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(seetrans)
                    {
                        imgeye.setImageResource(R.drawable.eyeclose);
                        if(customcard)
                            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , false , true , true , txtallpage);
                        else
                            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , false , true , false , txtallpage);
                        mCardView.setAdapter(mAdapter);
                        seetrans = false;
                    }
                    else
                    {
                        imgeye.setImageResource(R.drawable.eyeopen);
                        if(customcard)
                            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , true , txtallpage);
                        else
                            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , false , txtallpage);
                        mCardView.setAdapter(mAdapter);
                        seetrans = true;
                    }



                }
            });


            imgpartlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(clickpartlike)
                    {

                        imgpartlike.setImageResource(R.drawable.partlike);
                        if(!(seetrans))
                        {
                            seetrans = true;
                            imgeye.setImageResource(R.drawable.eyeopen);
                        }
                        infdata.clear();
                        if(customcard)
                            setearlyinformation(false , "customcard");
                        else
                            setearlyinformation(false , "matchcard");
                        if(customcard)
                            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , true , txtallpage);
                        else
                            mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , true , false, txtallpage);
                        mCardView.setAdapter(mAdapter);
                        clickpartlike = false;


                    }
                    else
                    {

                        if(!(seetrans))
                        {
                            seetrans = true;
                            imgeye.setImageResource(R.drawable.eyeopen);
                        }

                        List<Integer> listlike;
                        cardlike = false;
                        if(customcard)
                        {
                            listlike = dataBase.getlistdata("customcard" , "like");
                        }
                        else
                        {
                            listlike = dataBase.getlistdata("matchcard" , "like");
                        }


                        for(int like : listlike)
                        {
                            if(like == 1)
                                cardlike = true;

                        }

                        if(cardlike)
                        {
                            imgpartlike.setImageResource(R.drawable.back);
                            infdata.clear();

                            if(customcard)
                                setearlyinformation(true , "customcard");
                            else
                                setearlyinformation(true , "matchcard");
                            if(customcard)
                                mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , false , true , txtallpage);
                            else
                                mAdapter = new AdaptorInfinitecard(resId , ActivityCards.this , infdata , true , false , false , txtallpage);
                            mCardView.setAdapter(mAdapter);
                            clickpartlike = true;
                        }
                        else
                        {
                            TastyToast.makeText(ActivityCards.this , "کارتی موجود نیست"  , TastyToast.LENGTH_SHORT , TastyToast.INFO);
                        }


                    }



                }
            });



            imgnight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(night)
                    {
                        layoutcard.setBackgroundColor(Color.WHITE);
                        night = false;
                    }
                    else
                    {
                        layoutcard.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
                        night = true;
                    }


                }
            });



            imgadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showaddcard();

                }
            });





        }


        private void showaddcard()
        {


            txtof.setVisibility(View.INVISIBLE);
            txtallpage.setVisibility(View.INVISIBLE);
            mCardView.setVisibility(View.INVISIBLE);
            imgadd.setVisibility(View.INVISIBLE);

            mattrans.setVisibility(View.VISIBLE);
            matworld.setVisibility(View.VISIBLE);

            addcard = true;

            imgeye.setClickable(false);
            imgpartlike.setClickable(false);

            btnnext.setText("ذخیره");
            btnrestart.setText("ترجمه انلاین");
            btnpre.setText("انصراف");


        }

        private void hideaddcard()
        {

            txtof.setVisibility(View.VISIBLE);
            txtallpage.setVisibility(View.VISIBLE);
            mCardView.setVisibility(View.VISIBLE);
            imgadd.setVisibility(View.VISIBLE);

            mattrans.setVisibility(View.GONE);
            matworld.setVisibility(View.GONE);

            addcard = false;

            imgeye.setClickable(true);
            imgpartlike.setClickable(true);

            btnnext.setText("Next");
            btnrestart.setText("Restart");
            btnpre.setText("Pre");

        }


        private void setbackcart()
        {





            mCardView.setClickable(true);
            mCardView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT);
            mCardView.setAnimInterpolator(new LinearInterpolator());
            mCardView.setTransformerToFront(new DefaultTransformerToFront());
            mCardView.setTransformerToBack(new DefaultTransformerToBack());
            mCardView.setZIndexTransformerToBack(new DefaultZIndexTransformerCommon());



        }



        private void setnextcard()
        {




            mCardView.setClickable(false);
            mCardView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
            mCardView.setAnimInterpolator(new OvershootInterpolator(-8));
            mCardView.setTransformerToFront(new DefaultCommonTransformer());






            mCardView.setTransformerToBack(new AnimationTransformer() {
                @Override
                public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {


                    int positionCount = fromPosition - toPosition;
                    float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                    if (fraction < 0.5) {
                        view.setTranslationX(cardWidth * fraction * 1.5f);
                        view.setRotationY(-45 * fraction);
                    } else {
                        view.setTranslationX(cardWidth * 1.5f * (1f - fraction));
                        view.setRotationY(-45 * (1 - fraction));
                    }
                }






                @Override
                public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                    int positionCount = fromPosition - toPosition;
                    float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                    view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                            fromPosition - 0.02f * fraction * positionCount));
                }
            });






            mCardView.setZIndexTransformerToBack(new ZIndexTransformer() {
                @Override
                public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                    if (fraction < 0.5f) {
                        card.zIndex = 1f + 0.01f * fromPosition;
                    } else {
                        card.zIndex = 1f + 0.01f * toPosition;
                    }
                }





                @Override
                public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

                }
            });


        }










}
