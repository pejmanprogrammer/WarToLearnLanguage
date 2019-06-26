package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by MohandesPejman on 2/5/2018.
 */

public class AdaptorRecycleAnswer extends RecyclerView.Adapter<AdaptorRecycleAnswer.HolderAnswer>
{

    GetDataBase dataBase;
    Context context;

    Integer[] numsans  = new Integer[G.playersrobat.size()];
    Integer[] numsansenemy  = new Integer[G.playersrobat.size()];

    Integer[] picfriend ;
    Integer[] picenemy ;

    int qustrue;
    int falsechoose;
    int percendheroenemy;
    int level;
    int surface;

    boolean friend = false;

    Random random ;

    public  AdaptorRecycleAnswer(Context context, int qusTrue, int falsechoose, boolean friend, Integer[] numsans , Integer[] numsansenemy
    , int percendheroenemy)
    {


        dataBase =  new GetDataBase(context);
        this.numsans = numsans;
        this.context = context;
        this.qustrue = qusTrue;
        this.friend = friend;
        this.numsansenemy = numsansenemy;
        this.falsechoose = falsechoose;
        this.percendheroenemy = percendheroenemy;

        picfriend= new Integer[]{R.drawable.friend1, R.drawable.friend2, R.drawable.friend3, R.drawable.friend4, R.drawable.friend5,
                R.drawable.friend6, R.drawable.friend7, R.drawable.friend8, R.drawable.friend9, R.drawable.firend10, R.drawable.friend11,
                R.drawable.friend12, R.drawable.friend13};
        picenemy = new Integer[]{R.drawable.heroenemy1 , R.drawable.heroenemy2 , R.drawable.heroenemy3 , R.drawable.heroenemy4 ,
                R.drawable.heroenemy5 , R.drawable.heroenemy6 , R.drawable.heroenemy7 , R.drawable.heroenemy8 , R.drawable.heroenemy9
                , R.drawable.heroenemy10 , R.drawable.heroenemy11 , R.drawable.heroenemy12};
        random = new Random();

        level = dataBase.selectinfint("information" , 1  , "level");
        surface =dataBase.selectinfint("information" , 1  , "surface");

        setlevelsenemy();

    }




    @Override
    public HolderAnswer onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_answercard  , parent , false);
        return new HolderAnswer(view);

    }

    @Override
    public void onBindViewHolder(HolderAnswer holder, int position) {

            if(friend)
            {




                if(position == 0)
                {

                    try
                    {

                        String photoPath = Environment.getExternalStorageDirectory()+"/ImageProfile/myprofile.jpg";

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);


                        holder.imgpichero.setImageBitmap(bitmap);

                    }
                    catch (Exception e)
                    {

                    }




                    holder.imgmytime.setVisibility(View.INVISIBLE);
                    holder.txtqustrueans.setText(((qustrue * 10 ) + falsechoose) +"" );


                }
                else
                {



                    int idrobat =G.playersrobat.get(position - 1) + 1;
                    int skill =dataBase.selectinfint("infrobat" , idrobat  , "skill");
                    int speed = dataBase.selectinfint("infrobat" , idrobat , "speed");
                    int lvl  = dataBase.selectinfint("infrobat" , idrobat , "herelevel");




                    holder.imgpichero.setImageResource(picfriend[idrobat - 1]);
                    holder.txtqustrueans.setText(skill + numsans[position - 1] + "");
                    holder.txttimeans.setText((speed + numsans[position - 1])+ "");
                    holder.txtlvlhero.setText(lvl+"");

                }

            }







            else
            {

                holder.cardView.setCardBackgroundColor(Color.RED);
                if(surface == 2  && position == 1)
                    holder.imgpichero.setImageResource(picenemy[5]);
                else if(surface == 3 && position == 2)
                    holder.imgpichero.setImageResource(picenemy[6]);
                else if(surface == 4 && position == 3)
                    holder.imgpichero.setImageResource(picenemy[7]);
                else if(surface == 5 && position == 4)
                    holder.imgpichero.setImageResource(picenemy[8]);
                else if(surface == 6 && position == 1)
                    holder.imgpichero.setImageResource(picenemy[9]);
                else if(surface == 7 && position == 2)
                    holder.imgpichero.setImageResource(picenemy[10]);
                else if(surface == 8 && position == 3)
                    holder.imgpichero.setImageResource(picenemy[11]);
                else
                    holder.imgpichero.setImageResource(picenemy[position]);



                if(position == 0)
                {





                    holder.imgflag.setImageResource(R.drawable.flagred);
                    holder.imgmytime.setVisibility(View.INVISIBLE);
                    holder.txtqustrueans.setText((50 + percendheroenemy) + "");



                }
                else
                {



                    int idrobat = position;
                    int skill =dataBase.selectinfint("infrobatenemy" , idrobat  , "skill");
                    int speed = dataBase.selectinfint("infrobatenemy" , idrobat , "speed");
                    int lvl  = dataBase.selectinfint("infrobatenemy" , idrobat , "herelevel");

                    holder.txtqustrueans.setText((skill + numsansenemy[position - 1]) + "");
                    holder.txttimeans.setText((speed + numsansenemy[position - 1])+ "");
                    holder.txtlvlhero.setText(lvl+"");

                }






            }


    }

    @Override
    public int getItemCount() {
        return G.playersrobat.size() + 1;
    }

    public class HolderAnswer extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imgpichero;
        ImageView imgmytime;
        ImageView imgflag;
        TextView txtlvlhero;
        TextView txtqustrueans;
        TextView txttimeans;

        public HolderAnswer(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardenemy);
            imgflag = (ImageView) itemView.findViewById(R.id.imgflag);
            imgpichero = (ImageView) itemView.findViewById(R.id.imgpicheroans);
            imgmytime = (ImageView) itemView.findViewById(R.id.imgmytime);

            txtlvlhero = (TextView) itemView.findViewById(R.id.txtlvlheroans);
            txttimeans = (TextView) itemView.findViewById(R.id.txttimeans);
            txtqustrueans = (TextView) itemView.findViewById(R.id.txtqustrueans);

        }
    }


    private void setlevelsenemy()
    {





        if(G.mysurface == surface && G.mylevel == level - 1 && friend == false)
        {

            if(level == 2   || level == 6  || level == 11)
            {


                int numselectenemy = random.nextInt(4) + 1;
                int skill =dataBase.selectinfint("infrobatenemy" , numselectenemy  , "skill");
                int speed = dataBase.selectinfint("infrobatenemy" , numselectenemy , "speed");
                int lvl  = dataBase.selectinfint("infrobatenemy" , numselectenemy , "herelevel");

                dataBase.updateint("infrobatenemy" , "skill" , skill + 7 , numselectenemy);
                dataBase.updateint("infrobatenemy" , "speed" , speed + 6 , numselectenemy);
                dataBase.updateint("infrobatenemy" , "herelevel" , lvl   + 1 , numselectenemy);


            }

        }

    }




}

