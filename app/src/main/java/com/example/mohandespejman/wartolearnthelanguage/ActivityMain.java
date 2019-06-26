package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.sdsmdg.tastytoast.TastyToast;
import com.vincan.rotatecircleimageview.RotateCircleImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class ActivityMain extends RuntimePermissionsActivity {

    int surface;
    int level;
    int CaptureImage = 1;
    int Request_External_Access = 100;

    Button[] btnlevel = null;
    Integer[] colorslevel = {Color.YELLOW , Color.GRAY , Color.BLUE , Color.RED , Color.GREEN , Color.WHITE , Color.GRAY ,Color.MAGENTA , Color.BLACK };


    ImageView imgcustomcard;
    ImageView imgmyprofile;
    ImageView imgmatchend;
    ImageView imgcamera;

    TextView txtnumberenergy;
    TextView txtlevel;

    Button btnmatch1;
    Button btnmatch2;
    Button btnmatch3;
    Button btnmatch4;
    Button btnmatch5;
    Button btnmatch6;
    Button btnmatch7;
    Button btnmatch8;
    Button btnmatch9;
    Button btnmatch10;
    Button btnmatch11;

    LinearLayout laymain;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        if (!(sharedPreferences.contains("helpmain")) && (sharedPreferences.contains("checkhelpmain")))
            sethelp();


    }




    //وصل کردن لایه xml در فایل جاوا
    public void setfindviews()
    {



        imgcustomcard = (ImageView) findViewById(R.id.imgcustomcard);
        imgmyprofile = (ImageView) findViewById(R.id.imgmyprofile);
        imgmatchend = (ImageView) findViewById(R.id.imgmatchend);
        imgcamera = (ImageView) findViewById(R.id.imgcamera);


        txtnumberenergy = (TextView) findViewById(R.id.txtnumberenergy);
        txtlevel = (TextView) findViewById(R.id.textlevel);

        btnmatch1 = (Button) findViewById(R.id.btnmatch1);
        btnmatch2 = (Button) findViewById(R.id.btnmatch2);
        btnmatch3 = (Button) findViewById(R.id.btnmatch3);
        btnmatch4 = (Button) findViewById(R.id.btnmatch4);
        btnmatch5 = (Button) findViewById(R.id.btnmatch5);
        btnmatch6 = (Button) findViewById(R.id.btnmatch6);
        btnmatch7 = (Button) findViewById(R.id.btnmatch7);
        btnmatch8 = (Button) findViewById(R.id.btnmatch8);
        btnmatch9 = (Button) findViewById(R.id.btnmatch9);
        btnmatch10 = (Button) findViewById(R.id.btnmatch10);
        btnmatch11 = (Button) findViewById(R.id.btnmatch11);

        laymain = (LinearLayout) findViewById(R.id.laymain);


    }



    //تنظیم اطلاعات اولیه لایه و گرفتن ان از دیتابیس
    public void setearlyinformation()
    {



        sharedPreferences = getSharedPreferences("insidedata" , Context.MODE_PRIVATE);


        GetDataBase dataBase = new GetDataBase(ActivityMain.this);

        surface=dataBase.selectinfint("information" , 1 , "surface");

        int setbackcolor = G.mysurface;


        if(setbackcolor >= 2)
            laymain.setBackgroundColor(colorslevel[setbackcolor - 2]);





        setbackprofile();



        level =dataBase.selectinfint("information" , 1 , "level");
        G.level = level;
        int energy =dataBase.selectinfint("information" , 1 , "energy");
        if(G.mysurface < surface)
            level = 12;


        setcolorbutton(level);
        txtnumberenergy.setText(energy+"");
        txtlevel.setText(level+"");










    }


    private void setbackprofile()
    {

        try
        {



            String photoPath = Environment.getExternalStorageDirectory()+"/ImageProfile/myprofile.jpg";

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);


            imgmyprofile.setImageBitmap(bitmap);



        }
        catch (Exception e)
        {

        }


    }


    //تنظیم رنگ buttons
    public void setcolorbutton(int level)
    {



        btnlevel = new Button[]{btnmatch1, btnmatch2, btnmatch3, btnmatch4, btnmatch5,
                btnmatch6, btnmatch7, btnmatch8, btnmatch9, btnmatch10, btnmatch11};


        for (int i = 0; i < btnlevel.length ; i++) {

            if( i <= level-1)
            {

                try
                {


                    View v = btnlevel[i];
                    v.setBackgroundResource(R.drawable.backselectbtnnumber);


                }
                catch (Exception e)
                {

                }


            }


        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CaptureImage && resultCode == RESULT_OK)
        {

            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            saveimg(bitmap);




        }

    }



    public void saveimg(final Bitmap bitmap)

    {




        new Thread() {
            @Override
            public void run() {


                try

                {



                    File sdCardDirectory = new File(
                            Environment.getExternalStorageDirectory(),
                            "ImageProfile");

                    sdCardDirectory.mkdirs();

                    String imageNameForSDCard = "myprofile.jpg";



                    File image = new File(sdCardDirectory, imageNameForSDCard);

                    FileOutputStream outStream;

                    outStream = new FileOutputStream(image);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

                    outStream.flush();

                    outStream.close();

                    sendBroadcast(new Intent(
                            Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse("file://"
                                    + Environment.getExternalStorageDirectory())));

                    setbackprofile();

                }

                catch (Exception e)

                {


                }

            }
        }.start();

    }







    //عملیات کلیک کردن بر روی views
    public void setclickviews()
    {



        //کلیک برای ImageView
        imgcustomcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ActivityMain.this , ActivityCards.class);
                intent.putExtra("customcard" , true);
                startActivity(intent);



            }
        });

        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActivityMain.super.requestAppPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE} , Request_External_Access);



            }
        });







            imgmatchend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(level == 12)
                    {

                        G.mylevel = 12;
                        G.matchend = true;
                        startActivity(new Intent(ActivityMain.this, ActivityTest.class));

                    }

                }
            });






        //کلیک برای TextView
        txtnumberenergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this , ActivityBuy.class));
            }
        });



        //کلیک برای Buttons


       for (int i = 0; i <btnlevel.length ; i++) {

             final  Button button  = btnlevel[i];

            if(i <= level - 1)
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int selectlevel = Integer.parseInt(button.getText().toString());
                    G.mylevel = selectlevel;
                    G.matchend = false;
                    Intent intent = new Intent(ActivityMain.this , ActivitySelectPlayer.class);
                    startActivity(intent);



                }
            });


        }










    }





    private void sethelp()
    {

        View[] views = {imgcustomcard , imgcamera};
        String[] titles = { "ساخت کارت های دلخواه" , "گرفتن عکس با دوربین" };
        String[] descriptions = {"میتونی وقتی امتحان زبان داری یا وقتی داری زبان میخونی لغات جدیدش را اینجا وارد کنی تا بتونی خوب ان لغت را یاد بگیری",
                "با دوربینت عکس بگیر و عکس گرفته شما در برنامه نمایش داده میشود"
        };
        HelpView helpView = new HelpView(ActivityMain.this);
        helpView.showhelp(views , titles , descriptions);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("helpmain" , true);
        editor.apply();


    }





    @Override
    public void onPermissionsGranted(int requestCode) {

        if(requestCode == Request_External_Access)
        {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent , CaptureImage);

        }
    }

    @Override
    public void onPermissionsDeny(int requestCode) {

    }






    @Override
    protected void onPause() {
        G.playersrobat.clear();
        super.onPause();
    }

    @Override
    protected void onRestart() {

        if(G.surface > G.mysurface)
            finish();
        else
        {
            setearlyinformation();
            setclickviews();
        }

        super.onRestart();
    }
}
