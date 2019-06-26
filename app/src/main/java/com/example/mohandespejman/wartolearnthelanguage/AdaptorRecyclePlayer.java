package com.example.mohandespejman.wartolearnthelanguage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.sdsmdg.tastytoast.TastyToast;
import com.vincan.rotatecircleimageview.RotateCircleImageView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by MohandesPejman on 1/28/2018.
 */

public class AdaptorRecyclePlayer extends RecyclerView.Adapter<AdaptorRecyclePlayer.HolderPlayers> {



    boolean percent = false;

    int numplayer = 0;
    int energy=0;

    Activity activity;
    int[] friend = new int[]{R.drawable.friend1, R.drawable.friend2, R.drawable.friend3, R.drawable.friend4, R.drawable.friend5,
            R.drawable.friend6, R.drawable.friend7, R.drawable.friend8, R.drawable.friend9, R.drawable.firend10, R.drawable.friend11,
            R.drawable.friend12, R.drawable.friend13};

    ArrayList<InformationData> informationDatas = new ArrayList<>();

    GetDataBase dataBase ;

    ImageView player1;
    ImageView player2;
    ImageView player3;
    ImageView player4;
    ImageView refresh;

    TextView txtumenery;


    public  AdaptorRecyclePlayer(Activity activity , final ImageView player1 , final ImageView player2 , final ImageView player3 , final ImageView player4 , ImageView refresh
     , TextView txtnumenergy , boolean percent)
    {



        this.activity =activity ;

        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.refresh = refresh;

        this.percent = percent;

        this.txtumenery = txtnumenergy;

        dataBase = new GetDataBase(activity );

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                G.playersrobat.clear();
                numplayer = 0;
                player1.setImageResource(0);
                player2.setImageResource(0);
                player3.setImageResource(0);
                player4.setImageResource(0);



            }
        });





        getinfdatabase();




    }
    @Override
    public HolderPlayers onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleplayer ,parent , false);

        return new HolderPlayers(view);
    }

    @Override
    public void onBindViewHolder(final HolderPlayers holder, final int position) {

        final Animation animcard = AnimationUtils.loadAnimation(activity , R.anim.animselectcard);
        holder.cardplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InformationData informationData = informationDatas.get(position);
                int lock = informationData.idselect;

                boolean reply=false;
                for(int counter : G.playersrobat)
                {
                    if(counter == position)
                        reply = true;
                }

                if(lock == 1 && reply == false)
                {



                    if(numplayer <= 3)
                    {

                        v.startAnimation(animcard);
                        G.playersrobat.add(numplayer , position);

                        numplayer++;
                        for (int i = 0; i <numplayer ; i++) {

                            int picrobat = G.playersrobat.get(i);
                            switch (i)
                            {
                                case 0 : player1.setImageResource(friend[picrobat]);break;
                                case 1 : player2.setImageResource(friend[picrobat]);break;
                                case 2 : player3.setImageResource(friend[picrobat]);break;
                                case 3 : player4.setImageResource(friend[picrobat]);break;
                            }


                        }


                    }

                }




            }
        });

        setearlyinformation(holder , position);



    }




    @Override
    public int getItemCount() {
        return friend.length;
    }

    public class HolderPlayers extends RecyclerView.ViewHolder
    {

        CardView cardplayer;
        RotateCircleImageView imgrobatfirend;
        ImageView imgtype;
        ImageView imglockrobat;
        Button btnchange;


        TextView txtlevel;
        TextView txtskill;
        TextView txtspeed;
        TextView txtmagic;

        public HolderPlayers(View itemView) {
            super(itemView);


            cardplayer= (CardView) itemView.findViewById(R.id.cardplayer);
            imgtype = (ImageView) itemView.findViewById(R.id.imgtype);
            imglockrobat = (ImageView) itemView.findViewById(R.id.imglockrobat);
            imgrobatfirend = (RotateCircleImageView) itemView.findViewById(R.id.imgrobatfriend);
            btnchange = (Button) itemView.findViewById(R.id.btnchange);


            txtlevel = (TextView) itemView.findViewById(R.id.txtnumlevel);
            txtskill = (TextView) itemView.findViewById(R.id.txtskill);
            txtspeed = (TextView) itemView.findViewById(R.id.txtspeed);
            txtmagic = (TextView) itemView.findViewById(R.id.txtmagic);






        }


    }



    //تنظیم اطلاعات اولیه
    public void setearlyinformation(final HolderPlayers holder,final int position)
    {

        InformationData informationData = informationDatas.get(position);

        holder.imgrobatfirend.setImageResource(friend[position]);

        holder.txtskill.setText(informationData.skill + "");
        holder.txtspeed.setText(informationData.speed + "");
        holder.txtmagic.setText(informationData.magic + "");

        holder.txtlevel.setText(informationData.herolevel + "");



        int select = informationData.idselect;

        if(select == 0 )
        {

            switch (position)
            {

                case 5 :
                case 6 :
                case 7 :
                    {
                        if(percent)
                            settextandclickbtn("280" ,position , holder);
                        else
                            settextandclickbtn("350" ,position , holder);
                    break;

                }



                case 8 :
                case 9 :
                    {
                        if(percent)
                            settextandclickbtn("480" ,position , holder);
                        else
                            settextandclickbtn("600" ,position , holder);
                        break;

                }
                case 10:
                case 11:
                    {
                    if(percent)
                        settextandclickbtn("800" ,position , holder);
                    else
                        settextandclickbtn("1000" ,position , holder);
                    ;break;

                }
                case 12:
                    {
                    if(percent)
                        settextandclickbtn("1200" ,position , holder);
                    else
                        settextandclickbtn("1500" ,position , holder);
                    ;break;

                }

            }


            holder.imglockrobat.setVisibility(View.VISIBLE);

        }
        else
        {

            if(percent)
            {
                holder.btnchange.setText("ارتقا (80 سکه)");
                holder.btnchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updaterobat(position , 80 , holder);
                    }
                });
            }
            else
            {
                holder.btnchange.setText("ارتقا (100 سکه)");
                holder.btnchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updaterobat(position , 100 , holder);
                    }
                });
            }

            holder.imglockrobat.setVisibility(View.INVISIBLE);
        }



        String type = informationData.type;

        switch (type)
        {
            case "Skill" : holder.imgtype.setImageResource(R.drawable.brainplayer);break;
            case "Speed" : holder.imgtype.setImageResource(R.drawable.end);break;
            case "Magic" : holder.imgtype.setImageResource(R.drawable.imgmagic);break;
        }




    }

    private void settextandclickbtn(String text ,final int position ,final HolderPlayers holder)
    {

        holder.btnchange.setText(text + "سکه");
        final int numgold = Integer.parseInt(text);

        holder.btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(percent)
                    buyrobat(position , numgold , holder);
                else
                    buyrobat(position , numgold , holder);
            }
        });


    }

    private void buyrobat(int position , int needgold , HolderPlayers holder)
    {
        energy = dataBase.selectinfint("information" , 1 , "energy");
        if(energy < needgold)
        {
            TastyToast.makeText(activity , "مقدار سکه شما کافی نیست"  , TastyToast.LENGTH_SHORT , TastyToast.INFO);
        }
        else
        {
            dataBase.updateint("infrobat" , "available" , 1 , position + 1);
            energy -= needgold;
            dataBase.updateint("information" , "energy" , energy , 1);
            getinfdatabase();
            Animation anim = AnimationUtils.loadAnimation(activity , R.anim.animtexttest);
            MediaPlayer media = MediaPlayer.create(activity , R.raw.win);
            media.start();
            if(percent)
                percent = false;
            txtumenery.setText(energy+"");
            this.notifyDataSetChanged();
            holder.itemView.setAnimation(anim);


        }
    }

    private void updaterobat(int position , int needgold , HolderPlayers holder)
    {

        energy = dataBase.selectinfint("information" , 1 , "energy");
        if(energy < needgold)
        {
            TastyToast.makeText(activity , "مقدار سکه شما کافی نیست"  , TastyToast.LENGTH_SHORT , TastyToast.INFO);
        }
        else
        {

            energy -= needgold;

            int skill =dataBase.selectinfint("infrobat" , position + 1  , "skill");
            int speed = dataBase.selectinfint("infrobat" , position + 1  , "speed");
            int magic  = dataBase.selectinfint("infrobat" , position + 1  , "magic");
            int lvl  = dataBase.selectinfint("infrobat" , position + 1  , "herelevel");



            dataBase.updateint("information"   , "energy" , energy , 1);
            dataBase.updateint("infrobat" , "skill" , skill + 5 , position + 1 );
            dataBase.updateint("infrobat" , "speed" , speed + 5 , position + 1 );
            dataBase.updateint("infrobat" , "magic" , magic + 5 , position + 1 );
            dataBase.updateint("infrobat" , "herelevel" , lvl+ 1 , position + 1 );


            MediaPlayer media = MediaPlayer.create(activity , R.raw.win);
            media.start();
            Animation anim = AnimationUtils.loadAnimation(activity , R.anim.animtexttest);
            holder.itemView.setAnimation(anim);
            if(percent)
                percent = false;
            txtumenery.setText(energy+"");
            getinfdatabase();
            this.notifyDataSetChanged();


        }

    }




    //گرفتن اطلاعات از دیتابیس
    public void getinfdatabase()
    {




        for (int i = 0; i <friend.length ; i++)
        {

            InformationData informationData = new InformationData();
            int numid = i+1;

            int skill = dataBase.selectinfint("infrobat" , numid , "skill");
            informationData.skill = skill;

            int speed = dataBase.selectinfint("infrobat" , numid  , "speed");
            informationData.speed = speed;

            int magic = dataBase.selectinfint("infrobat" , numid , "magic");
            informationData.magic = magic;

            int idselect = dataBase.selectinfint("infrobat" , numid , "available");
            informationData.idselect = idselect;

            int herelevel = dataBase.selectinfint("infrobat" , numid , "herelevel");
            informationData.herolevel = herelevel;

            String type = dataBase.selectinfstring("infrobat" , numid , "type");
            informationData.type = type;

            informationDatas.add(i , informationData);


        }



    }


    private class InformationData
    {

        int skill;
        int speed;
        int magic;
        int idselect;
        int herolevel;

        String type;

    }










}





