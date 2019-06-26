package com.example.mohandespejman.wartolearnthelanguage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by MohandesPejman on 1/31/2018.
 */

public class AdaptorInfinitecard extends BaseAdapter {

    private int[] resIds = {};

    int color = 1;

    boolean seetrans ;
    boolean likeable ;
    boolean customcard;

    ArrayList<informationinfitecard> infdata ;

    Activity activity;

    ViewHolder viewHolder;

    ImageView imgeye;

    GetDataBase dataBase;

    TextView txtallpage;




    TextToSpeech speech;



    AdaptorInfinitecard(int[] resIds , Activity activity , ArrayList<informationinfitecard> infdata ,
                        boolean seetrans  , boolean likeable , boolean customcard , TextView txtallpage) {

        this.resIds = resIds;
        this.activity = activity;
        this.infdata = infdata;
        this.seetrans = seetrans;
        this.likeable = likeable;
        this.txtallpage = txtallpage;
        this.customcard = customcard;
        dataBase = new GetDataBase(activity);


    }

    @Override
    public int getCount() {
        return infdata.size();
    }

    @Override
    public informationinfitecard getItem(int position) {
        return infdata.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {




        if(convertView == null)
        {


            convertView = LayoutInflater.from(activity).inflate(R.layout.layout_infinitecard, parent, false);
            viewHolder = new ViewHolder();

            findviews(convertView, viewHolder);
            convertView.setTag(viewHolder);
        }
        else
        {

            viewHolder = (ViewHolder) convertView.getTag();
        }



            switch (color)
            {
                case 1 : convertView.setBackgroundResource(resIds[0]); color++; break;
                case 2 : convertView.setBackgroundResource(resIds[1]); color++; break;
                case 3 : convertView.setBackgroundResource(resIds[2]); color++; break;
                case 4 : convertView.setBackgroundResource(resIds[3]); color++; break;
                case 5 : convertView.setBackgroundResource(resIds[4]); color=1; break;
            }




           final informationinfitecard inf = infdata.get(position);
            viewHolder.txtworldcard.setText(inf.word);
            viewHolder.txttranscard.setText(inf.trans);
            viewHolder.imgcardlike.setImageResource(R.drawable.partlike);



        if(likeable)
        {

            if(inf.like == 1)
            {
                viewHolder.imgcardlike.setImageResource(R.drawable.partlike);
            }
            else
                viewHolder.imgcardlike.setImageResource(R.drawable.partdislike);

        }
        else
        {
            viewHolder.imgcardlike.setVisibility(View.INVISIBLE);
        }






        if(seetrans == false)
        {
            viewHolder.txttranscard.setVisibility(View.INVISIBLE);
        }
        if(!customcard)
            viewHolder.imgtrashcard.setVisibility(View.GONE);


        viewHolder.imgtrashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataBase.delete("customcard" , inf.id);
                infdata.remove(position);
                notifyDataSetChanged();

                    if(infdata.size() == 0)
                        txtallpage.setText(0+"");




            }
        });






        setclickviews(inf);







        return convertView;
    }



    class ViewHolder
    {


        ImageView imgcardlike;
        ImageView imgtrashcard;
        ImageView imginfwrod;
        ImageView imgsoundonline;
        ImageView imgsoundoffline;

        TextView  txtworldcard;
        TextView  txttranscard;

    }



    private void setclickviews(final informationinfitecard inf )
    {





        viewHolder.imgcardlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(inf.like == 1)
                {

                    ImageView img = (ImageView) v;
                    img.setImageResource(R.drawable.partdislike);
                    inf.like = 0;
                    if(customcard)
                        dataBase.updateint("customcard" , "like"  , 0 , inf.id);
                    else
                        dataBase.updateint("matchcard" , "like"  , 0 , inf.id);



                }
                else
                {

                    ImageView img = (ImageView) v;
                    img.setImageResource(R.drawable.partlike);
                    inf.like = 1;
                    if(customcard)
                        dataBase.updateint("customcard" , "like"  , 1 , inf.id);
                    else
                        dataBase.updateint("matchcard" , "like"  , 1 , inf.id);




                }


            }
        });



        viewHolder.imgsoundoffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                offline_tts(inf.word);

            }
        });

        viewHolder.imgsoundonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                online_tts(inf.word , "es");

            }
        });


        viewHolder.imginfwrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity , GoogleTranslate.class);
                intent.putExtra("word" , inf.word);
                activity.startActivity(intent);

            }
        });



    }


    private void findviews(View view, ViewHolder viewHolder)
    {



        viewHolder.imgcardlike = (ImageView) view.findViewById(R.id.imglikecard);
        viewHolder.imgtrashcard = (ImageView) view.findViewById(R.id.imgtrashcard);
        viewHolder.imginfwrod = (ImageView) view.findViewById(R.id.imginfword);
        viewHolder.imgsoundoffline=(ImageView) view.findViewById(R.id.imgsoundoffline);
        viewHolder.imgsoundonline = (ImageView) view.findViewById(R.id.imgsoundonline);


        viewHolder.txtworldcard = (TextView) view.findViewById(R.id.txtwordcard);
        viewHolder.txttranscard = (TextView) view.findViewById(R.id.txttranscard);


    }


    public void online_tts(final String text,final String lan) {



        String Url = "https://translate.google.com/translate_tts?ie=UTF-8";
        String pronouce = "&q=" + text.replaceAll(" ", "%20");
        String language = "&tl=" + lan;
        String web = "&client=tw-ob";

        String fullUrl = Url + pronouce + language + web;



        Uri uri = Uri.parse(fullUrl);

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(activity,uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();

        }






    }




    public void offline_tts(final String text)
    {


        speech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {


                int result = speech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA && result == TextToSpeech.LANG_NOT_SUPPORTED) {


                }
                else
                {

                    speech.setPitch(200);
                    speech.setLanguage(Locale.ENGLISH);


                    speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                }


            }
        });

    }

}
