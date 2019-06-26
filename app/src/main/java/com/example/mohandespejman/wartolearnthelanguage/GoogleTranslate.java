package com.example.mohandespejman.wartolearnthelanguage;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Pejman on 2/21/2018.
 */

public class GoogleTranslate extends AppCompatActivity {


    WebView webView ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_googletranslate);

        webView = (WebView) findViewById(R.id.webgoogletranslate);



        Bundle bundle = getIntent().getExtras();


        String word = bundle.getString("word");


        try
        {
            webView.loadUrl("https://translate.google.com/?hl=fa#en/fa/" + word);
            this.finish();
        }
        catch (Exception e)
        {
            this.finish();
        }





    }














}
