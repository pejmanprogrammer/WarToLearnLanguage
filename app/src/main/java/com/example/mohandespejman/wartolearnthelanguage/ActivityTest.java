package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by MohandesPejman on 1/29/2018.
 */

public class ActivityTest extends AppCompatActivity {


    Button btnCh1;
    Button btnCh2;
    Button btnCh3;
    Button btnCh4;


    ImageView imgtwox;
    ImageView imgtherex;
    ImageView imgsoundtest;
    ImageView imgherotest;

    TextView texttest;
    TextView txtmountmagic;
    TextView txtnumberenergy;

    ProgressBar progressBar;

    LinearLayout laytest;

    int MaxRandom = 7;
    int NumQus = 10;

    GetDataBase database;

    int Magic = 0;
    int QusNow = 0;
    int QusTrue = 0;
    int FalseChoose = 9;


    int energy = 0;

    TextToSpeech speech;

    Button[] buttons;

    boolean replyqus = false;
    boolean clickpricereply = false;
    boolean plysound = false;
    boolean matchend = false;


    int choocequs1;
    int choocequs2;
    int choocequs3;

    int wordfalse = 0;

    List<Holderinfdataqus> dataqus;
    List<Holderinfchoose> listidqus;

    Holderinfdataqus holderinfdataqus;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);


        try {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } catch (Exception e) {

        }


        if (!(G.matchend))
            setanimbackground();
        setfindviews();
        setanimbutton();
        setearlyinformation();
        setclickviews();

        if (!(sharedPreferences.contains("helptest")))
            sethelp();



    }

    public void setfindviews() {


        btnCh1 = (Button) findViewById(R.id.btnch1);
        btnCh2 = (Button) findViewById(R.id.btnch2);
        btnCh3 = (Button) findViewById(R.id.btnch3);
        btnCh4 = (Button) findViewById(R.id.btnch4);

        laytest = (LinearLayout) findViewById(R.id.laytest);

        texttest = (TextView) findViewById(R.id.texttest);
        txtmountmagic = (TextView) findViewById(R.id.txtmountmagic);
        txtnumberenergy = (TextView) findViewById(R.id.txtnumberenergy);

        imgtwox = (ImageView) findViewById(R.id.imgtwox);
        imgtherex = (ImageView) findViewById(R.id.imgtherex);
        imgsoundtest = (ImageView) findViewById(R.id.imgsoudtst);
        imgherotest = (ImageView) findViewById(R.id.imgherotest);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        progressBar.setProgress(0);

    }

    private void setearlyinformation() {


        sharedPreferences = getSharedPreferences("insidedata" , Context.MODE_PRIVATE);
        mediaPlayer = MediaPlayer.create(this, R.raw.soundtest);
        matchend = G.matchend;

        if (matchend)
            NumQus = 20;


        if (matchend) {
            Integer[] imageUrls = new Integer[]{
                    R.drawable.hero1, R.drawable.hero2, R.drawable.hero3, R.drawable.hero4, R.drawable.hero5,
                    R.drawable.hero6, R.drawable.hero7, R.drawable.hero8, R.drawable.hero9, R.drawable.hero10
            };
            imgherotest.setImageResource(imageUrls[G.mysurface - 1]);
            laytest.setBackground(getResources().getDrawable(imageUrls[G.mysurface - 1]));
        }

        database = new GetDataBase(ActivityTest.this);
        buttons = new Button[]{btnCh1, btnCh2, btnCh3, btnCh4};

        energy = database.selectinfint("information", 1, "energy");
        txtnumberenergy.setText(energy + "");


        if (!matchend)
            settextmagic();

        setdataqus();


    }


    private void settextmagic() {

        for (int num : G.playersrobat) {
            int mountmagic = database.selectinfint("infrobat", num + 1, "magic");
            Magic += mountmagic;
        }

        txtmountmagic.setText(Magic + "");

    }

    private void setdataqus() {


        switch (G.mysurface) {
            case 1:
                MaxRandom += (G.mylevel * 3);
                break;
            case 2:
            case 3:
            case 4:
                MaxRandom = ((G.mysurface - 2) * 44) + (G.mylevel * 4) + 40;
                break;
            default:
                MaxRandom = ((G.mysurface - 5) * 55) + (G.mylevel * 5) + 172;
                break;
        }


        listidqus = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < NumQus; i++) {

            Holderinfchoose holderinfchoose = new Holderinfchoose();
            int shans = (random.nextInt(MaxRandom) + 1);
            int qus1;
            int qus2;
            int qus3;
            holderinfchoose.Ans = shans;

            for (int m = 0; m <= 3; m++) {
                int mount = (random.nextInt(MaxRandom) + 1);
                if (mount == shans)
                    m--;
                else {
                    switch (m) {

                        case 0:
                            holderinfchoose.Qus1 = mount;
                            break;
                        case 1: {
                            if (mount == holderinfchoose.Qus1)
                                m--;
                            else
                                holderinfchoose.Qus2 = mount;
                            break;
                        }
                        case 2: {
                            if (mount == holderinfchoose.Qus1 || mount == holderinfchoose.Qus2)
                                m--;
                            else
                                holderinfchoose.Qus3 = mount;
                            break;
                        }

                    }


                }

            }

            listidqus.add(holderinfchoose);


        }


        dataqus = new ArrayList<>();
        int i = 0;
        for (Holderinfchoose holderinfchoose : listidqus) {

            Holderinfdataqus holder = new Holderinfdataqus();

            holder.Word = database.selectinfstring("matchcard", holderinfchoose.Ans, "word");
            holder.Ans = database.selectinfstring("matchcard", holderinfchoose.Ans, "trans");
            holder.Qus1 = database.selectinfstring("matchcard", holderinfchoose.Qus1, "trans");
            holder.Qus2 = database.selectinfstring("matchcard", holderinfchoose.Qus2, "trans");
            holder.Qus3 = database.selectinfstring("matchcard", holderinfchoose.Qus3, "trans");

            dataqus.add(i, holder);
            i++;

        }


        setqusviews();
        QusNow++;


    }

    public void setclickviews() {


        btnCh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectview(btnCh1.getText().toString(), btnCh1);
            }
        });

        btnCh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectview(btnCh2.getText().toString(), btnCh2);
            }
        });

        btnCh3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectview(btnCh3.getText().toString(), btnCh3);
            }
        });

        btnCh4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectview(btnCh4.getText().toString(), btnCh4);
            }
        });


        imgtwox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickpricereply) {
                    TastyToast.makeText(getApplicationContext(), "بیش از یک مورد راهنما نمیتوان استفاده کرد", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                } else {
                    if (Magic >= 40) {
                        clickpricereply = true;
                        Magic -= 40;
                        buttons[choocequs1].setBackgroundColor(Color.RED);
                        txtmountmagic.setText(Magic + "");
                    } else if (energy >= 40) {
                        clickpricereply = true;
                        energy -= 40;
                        database.updateint("information", "energy", energy, 1);
                        buttons[choocequs1].setBackgroundColor(Color.RED);
                        energy = database.selectinfint("information", 1, "energy");
                        txtnumberenergy.setText(energy + "");
                    } else {
                        TastyToast.makeText(getApplicationContext(), "مقدار سکه شما کافی نیست", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    }
                }


            }
        });

        imgtherex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickpricereply) {
                    TastyToast.makeText(getApplicationContext(), "بیش از یک مورد راهنما نمیتوان استفاده کرد", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                } else {
                    if (Magic >= 60) {
                        clickpricereply = true;
                        Magic -= 60;
                        buttons[choocequs1].setBackgroundColor(Color.RED);
                        buttons[choocequs2].setBackgroundColor(Color.RED);
                        txtmountmagic.setText(Magic + "");
                    } else if (energy >= 60) {
                        clickpricereply = true;
                        energy -= 60;
                        database.updateint("information", "energy", energy, 1);
                        buttons[choocequs1].setBackgroundColor(Color.RED);
                        buttons[choocequs2].setBackgroundColor(Color.RED);
                        energy = database.selectinfint("information", 1, "energy");
                        txtnumberenergy.setText(energy + "");
                    } else {
                        TastyToast.makeText(getApplicationContext(), "مقدار سکه شما کافی نیست", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    }
                }


            }
        });

        imgsoundtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (plysound) {
                    mediaPlayer.pause();
                    plysound = false;
                } else {

                    mediaPlayer.start();
                    plysound = true;
                }

            }
        });

        texttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offline_tts(texttest.getText().toString());
            }
        });


    }

    private void selectview(String textview, Button btn) {


        if (textview == holderinfdataqus.Ans) {


            if (!replyqus) {
                QusTrue++;
                int progress = progressBar.getProgress();
                progressBar.setProgress(progress + 100 / NumQus);
            }

            if (matchend) {
                if (QusNow == 20) {
                    mediaPlayer.stop();


                    mediaPlayer = MediaPlayer.create(this, R.raw.win);
                    mediaPlayer.start();

                    if (G.surface == G.mysurface) {
                        G.surface++;
                        G.level = 1;
                        database.updateint("information", "surface", G.surface, 1);
                        database.updateint("information", "level", G.level, 1);
                        startActivity(new Intent(ActivityTest.this, ActivityPrice.class));
                        finish();
                    } else
                        finish();


                } else {
                    setbackbutton();
                    setanimbutton();
                    setqusviews();
                }
            } else {

                if (QusNow == 10) {
                    mediaPlayer.stop();
                    Intent intent = new Intent(ActivityTest.this, ActivityAnswerTest.class);
                    intent.putExtra("numans", QusTrue);
                    intent.putExtra("falsechoose", FalseChoose);
                    startActivity(intent);
                    finish();
                } else {

                    setbackbutton();
                    setanimbutton();
                    setqusviews();

                }


            }


            replyqus = false;
            QusNow++;


        } else {

            if (matchend) {
                wordfalse++;
                if (wordfalse == 3) {

                    final AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTest.this);
                    builder.setView(R.layout.dialog_layut);
                    builder.setCancelable(false);
                    dialog = builder.create();
                    dialog.show();

                    TextView txtmessage = (TextView) dialog.findViewById(R.id.txtdialog);
                    ImageView imgdialog = (ImageView) dialog.findViewById(R.id.imgdialog);
                    Button btnyes = (Button) dialog.findViewById(R.id.btnyes);
                    Button btnno = (Button) dialog.findViewById(R.id.btnno);
                    Button btnyestwo = (Button) dialog.findViewById(R.id.btnyestwo);
                    btnno.setVisibility(View.VISIBLE);
                    btnyestwo.setVisibility(View.VISIBLE);

                    txtmessage.setText("متاسفانه اشتباهاتت بیش تر از حد است میتوانی با شرایط زیر ادامه دهی");
                    imgdialog.setImageResource(R.drawable.sad);
                    btnyes.setText("مشاهده تبلیغ");
                    btnyestwo.setText("پرداخت 60 سکه");
                    btnyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            final Handler handler = new Handler();
                            final ShowVidioTapsell vidioTapsell = new ShowVidioTapsell(ActivityTest.this);
                            vidioTapsell.providevidio();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (!(vidioTapsell.showendvidio) && !(vidioTapsell.showerrorvidio)) {
                                        try {

                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (vidioTapsell.showendvidio) {

                                                wordfalse = 0;
                                                dialog.dismiss();

                                            }

                                        }
                                    });
                                }
                            }).start();


                        }
                    });
                    btnyestwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(energy >= 60)
                            {
                                wordfalse = 0;
                                energy -= 60;
                                database.updateint("information", "energy", energy, 1);
                                txtnumberenergy.setText(energy + "");
                                dialog.dismiss();
                            }
                            else
                                TastyToast.makeText(ActivityTest.this ,  "سکه شما کافی نیست" , TastyToast.LENGTH_SHORT , TastyToast.ERROR);

                        }
                    });

                    btnno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });


                }
            }
            replyqus = true;
            btn.setBackgroundColor(Color.RED);

            if (FalseChoose != 0)
                FalseChoose--;


        }


    }


    private void setqusviews() {

        holderinfdataqus = dataqus.get(QusNow);
        texttest.setText(holderinfdataqus.Word);

        int chooceans;


        Random random = new Random();
        chooceans = random.nextInt(4);
        do {
            choocequs1 = random.nextInt(4);
        }
        while (chooceans == choocequs1);

        do {
            choocequs2 = random.nextInt(4);
        }
        while (chooceans == choocequs2 || choocequs1 == choocequs2);

        do {
            choocequs3 = random.nextInt(4);
        }
        while (chooceans == choocequs3 || choocequs1 == choocequs3 || choocequs2 == choocequs3);


        buttons[chooceans].setText(holderinfdataqus.Ans);
        buttons[choocequs1].setText(holderinfdataqus.Qus1);
        buttons[choocequs2].setText(holderinfdataqus.Qus2);
        buttons[choocequs3].setText(holderinfdataqus.Qus3);


        clickpricereply = false;


    }

    public void offline_tts(final String text) {


        speech = new TextToSpeech(ActivityTest.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {


                int result = speech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA && result == TextToSpeech.LANG_NOT_SUPPORTED) {


                } else {

                    speech.setPitch(200);
                    speech.setLanguage(Locale.ENGLISH);


                    speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                }


            }
        });

    }

    private class Holderinfdataqus {

        String Word;
        String Ans;
        String Qus1;
        String Qus2;
        String Qus3;

    }

    private class Holderinfchoose {

        int Qus1;
        int Qus2;
        int Qus3;
        int Ans;

    }


    public void setanimbackground() {

        LinearLayout constraintLayout = (LinearLayout) findViewById(R.id.laytest);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

    }

    private void setbackbutton() {

        btnCh1.setBackground(getResources().getDrawable(R.drawable.buttommain));
        btnCh2.setBackground(getResources().getDrawable(R.drawable.buttommain));
        btnCh3.setBackground(getResources().getDrawable(R.drawable.buttommain));
        btnCh4.setBackground(getResources().getDrawable(R.drawable.buttommain));

    }

    public void setanimbutton() {


        btnCh1.setEnabled(false);
        btnCh2.setEnabled(false);
        btnCh3.setEnabled(false);
        btnCh4.setEnabled(false);


        Animation animright = AnimationUtils.loadAnimation(this, R.anim.animbuttontestright);
        Animation animleft = AnimationUtils.loadAnimation(this, R.anim.animbuttontestleft);
        Animation animtexttest = AnimationUtils.loadAnimation(this, R.anim.animtexttest);

        btnCh1.startAnimation(animright);
        btnCh2.startAnimation(animleft);
        btnCh3.startAnimation(animright);
        btnCh4.startAnimation(animleft);
        texttest.startAnimation(animtexttest);

        btnCh1.setEnabled(true);
        btnCh2.setEnabled(true);
        btnCh3.setEnabled(true);
        btnCh4.setEnabled(true);


    }


    private void sethelp() {

        View[] views = {txtmountmagic, imgtwox, imgtherex, imgsoundtest, texttest};
        String[] titles = {"مقدار جادو برای حذف گزینه ها", "حذف 1 گزینه با پرداخت 40 انرژی یا سکه", "حذف 2 گزینه با پرداخت 60 انرژی یا سکه",
                "پخش موسیقی باحال", "تلفظ لغت"};
        String[] descriptions = {"مقدار جادوی شما بستگی به جادوی جنگجویان شما دارد و همانند سکه عمل کرده و موقع نیاز می توانید گزینه ها را حذف کنید"
                , "", "", " با موسیقی میتونی هم شاد باشی هم یاد بگیری", "برای تغییر سرعت یا دیگر تنظیمات صدای تلفظ به  تنظیمات گوشی بخش گفتار مراجعه کنید"};

        HelpView helpView = new HelpView(ActivityTest.this);
        helpView.showhelp(views , titles ,descriptions);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("helptest" , true);
        editor.apply();

    }


    @Override
    protected void onPause() {
        if(G.matchend)
        {
            if(QusNow != 20)
            {
                QusTrue = 0;
                progressBar.setProgress(0);
            }

        }
        else
        {
            if(QusNow != 10)
            {
                QusTrue = 0;
                progressBar.setProgress(0);
            }
        }

        super.onPause();
    }

    @Override
    public void onBackPressed()
    {

        TastyToast.makeText(ActivityTest.this , "لطفا به تمام سوالات پاسخ دهید" , TastyToast.LENGTH_LONG , TastyToast.INFO);

    }
}
