package com.example.mohandespejman.wartolearnthelanguage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;


/**
 * Created by Pejman on 4/19/2018.
 */

public class ActivityBuy extends AppCompatActivity {


    RelativeLayout layonecoin;
    RelativeLayout laytwocoin;
    RelativeLayout laytherecoin;
    RelativeLayout layfourcoin;
    RelativeLayout layfivecoin;


    static final String TAG = "Log";


    static  String SKU_PREMIUM = "";


    boolean mIsPremium = false;


    static  int RC_REQUEST=0;


    IabHelper mHelper;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    GetDataBase dataBase;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buyenergy);

        try {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } catch (Exception e) {

        }


        SKU_PREMIUM = "";
        RC_REQUEST= 0;
        dataBase = new GetDataBase(ActivityBuy.this);


        String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDIqgc5vx24ZN7sWGIEnqYmj3FpDZEFzv/AC2fNMKzKp442XMhFtJhskAV3tLFwFjgYHQpnC3MpyURH3vT+xPtFIJurZvbYsAFJHR1WZzabJdz2pdfxmzLkEpkrjujOATdHZsPgga+lRpJibGp7CRCrobCmYwZMK4aDuUn2CCMSoyaK++cxOaElHztClgqZCawWIrpqbgdh84MEkB+4ZpUx1pRNMieO5oshDecQ7+UCAwEAAQ==";

        mHelper = new IabHelper(this, base64EncodedPublicKey);


        mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

                if (result.isFailure()) {
                    return;
                }
                else {
                    // does the user have the premium upgrade?
                    mIsPremium = inventory.hasPurchase(SKU_PREMIUM);
                    if (mIsPremium){
                        MasrafSeke(inventory.getPurchase(SKU_PREMIUM));
                    }



                }


            }
        };

        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (result.isFailure()) {

                    return;
                }
                else if (purchase.getSku().equals(SKU_PREMIUM)) {
                    // give user access to premium content and update the UI
                    MasrafSeke(purchase);

                }
            }
        };



        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {


                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                }
                // Hooray, IAB is fully set up!
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });


        setfindviews();
        setclickviews();


    }

    private void setfindviews()
    {

        layonecoin = (RelativeLayout) findViewById(R.id.layonecoin);
        laytwocoin = (RelativeLayout) findViewById(R.id.laytwocoin);
        laytherecoin = (RelativeLayout) findViewById(R.id.laytherecoin);
        layfourcoin = (RelativeLayout) findViewById(R.id.layfourcoin);
        layfivecoin = (RelativeLayout) findViewById(R.id.layfivecoin);


    }

    private void setclickviews()
    {

        layonecoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SKU_PREMIUM = "OneCoin";
                RC_REQUEST = 1001;
                mHelper.launchPurchaseFlow(ActivityBuy.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");

            }
        });

        laytwocoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SKU_PREMIUM = "TwoCoin";
                RC_REQUEST = 1002;
                mHelper.launchPurchaseFlow(ActivityBuy.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");

            }
        });

        laytherecoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SKU_PREMIUM = "ThereCoin";
                RC_REQUEST = 1003;
                mHelper.launchPurchaseFlow(ActivityBuy.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");

            }
        });

        layfourcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SKU_PREMIUM = "FourCoin";
                RC_REQUEST = 1004;
                mHelper.launchPurchaseFlow(ActivityBuy.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");

            }
        });

        layfivecoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SKU_PREMIUM = "FiveCoin";
                RC_REQUEST = 1005;
                mHelper.launchPurchaseFlow(ActivityBuy.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");

            }
        });

    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {

        }
    }


    @Override
    public void onDestroy()
    {

        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;

    }


    private void MasrafSeke(Purchase kala){

        mHelper.consumeAsync(kala, new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                if (result.isSuccess())
                {

                    int energy= dataBase.selectinfint("information" , 1 , "energy");

                    switch (RC_REQUEST)
                    {

                        case 1001 :  dataBase.updateint("information" , "energy" , energy + 350 , 1);break;
                        case 1002 :  dataBase.updateint("information" , "energy" , energy + 850 , 1);break;
                        case 1003 :  dataBase.updateint("information" , "energy" , energy + 1000 , 1);break;
                        case 1004 :  dataBase.updateint("information" , "energy" , energy + 2500 , 1);break;
                        case 1005 :  dataBase.updateint("information" , "energy" , energy + 8000 , 1);break;

                    }
                    TastyToast.makeText(ActivityBuy.this , "خرید با موفقیت انجام شد و سکه شما افزوده شد" , TastyToast.LENGTH_LONG , TastyToast.SUCCESS);


                }


            }
        });
    }



}
