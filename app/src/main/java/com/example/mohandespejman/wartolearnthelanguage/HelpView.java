package com.example.mohandespejman.wartolearnthelanguage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

/**
 * Created by Pejman on 4/14/2018.
 */

public class HelpView {

    String[] datatitle;
    String[] datadescription;
    View[] views;

    int counter=0;
    Activity activity;
    public HelpView(Activity activity)
    {

        this.activity = activity;

    }

    public void showhelp(final View[] v , String[] title , String[] description) {


        views = v;
        datatitle = title;
        datadescription = description;
        goshow(views[0] , datatitle[0] , datadescription[0]);


    }

    private void goshow(View view , String title , String description)
    {


        TapTargetView.showFor(activity,                 // `this` is an Activity
                TapTarget.forView(view, title, description)
                        // All options below are optional
                        .outerCircleColor(R.color.colorApp)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(22)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(18)            // Specify the size (in sp) of the description text// Specify the color of the description text
                        .textColor(R.color.white)            // Specify a color for both the title and description text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)
                        // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        // Specify a custom drawable to draw as the target
                        .targetRadius(45),
                // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        counter++;
                        if(counter != views.length)
                            goshow(views[counter] , datatitle[counter] , datadescription[counter]);
                    }
                });


    }






}
