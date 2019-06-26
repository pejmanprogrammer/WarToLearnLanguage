package com.example.mohandespejman.wartolearnthelanguage;

import android.app.ProgressDialog;
import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

/**
 * Created by Pejman on 4/3/2018.
 */

public class ShowVidioTapsell {

    Context context;
    public   boolean showendvidio = false;
    public   boolean showerrorvidio = false;

    public ShowVidioTapsell(Context context)
    {

        this.context = context;

    }

    public void providevidio()
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("در حال بارگذاری تبلیغ...");
        progressDialog.setMessage("لطفا چند ثانیه منتظر بمانید");
        progressDialog.show();
        TapsellAdRequestOptions options = new TapsellAdRequestOptions( TapsellAdRequestOptions.CACHE_TYPE_STREAMED);
        Tapsell.requestAd(context, null, options , new TapsellAdRequestListener() {
            @Override
            public void onError(String s) {
                TastyToast.makeText(context , "متاسفانه مشکلی در دریافت تبلیغ پیش امده"  , TastyToast.LENGTH_LONG , TastyToast.ERROR);
                showerrorvidio = true;
                progressDialog.dismiss();
            }

            @Override
            public void onAdAvailable(TapsellAd tapsellAd) {

                progressDialog.dismiss();
                TapsellShowOptions showOptions = new TapsellShowOptions();
                showOptions.setBackDisabled(false);
                showOptions.setImmersiveMode(true);
                showOptions.setShowDialog(true);
                tapsellAd.show(context, showOptions , new TapsellAdShowListener() {
                    @Override
                    public void onOpened(TapsellAd tapsellAd) {

                    }

                    @Override
                    public void onClosed(TapsellAd tapsellAd) {

                    }
                });

            }

            @Override
            public void onNoAdAvailable() {
                TastyToast.makeText(context , "تبلیغی برای نمایش وجود ندارد"  , TastyToast.LENGTH_LONG , TastyToast.INFO);
                showerrorvidio = true;
                progressDialog.dismiss();
            }

            @Override
            public void onNoNetwork() {
                TastyToast.makeText(context , "عدم دسترسی به اینترنت"  , TastyToast.LENGTH_LONG , TastyToast.ERROR);
                showerrorvidio = true;
                progressDialog.dismiss();
            }

            @Override
            public void onExpiring(TapsellAd tapsellAd) {
                TastyToast.makeText(context , "تبلیغ منقضی شده"  , TastyToast.LENGTH_LONG , TastyToast.CONFUSING);
                showerrorvidio = true;
                progressDialog.dismiss();
            }
        });

        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd tapsellAd, boolean b) {
                if (b)
                {


                    showendvidio = true;

                }
                else
                {

                    TastyToast.makeText(context, "تبلیغ تا انتها دیده نشد", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    showerrorvidio = true;
                }



            }
        });


    }




}
