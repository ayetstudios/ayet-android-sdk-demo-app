package com.ayetstudios.ayetandroidsdkdemoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.ayetstudios.publishersdk.VideoAdInterstitial;
import com.ayetstudios.publishersdk.interfaces.ActivateOfferCallback;
import com.ayetstudios.publishersdk.interfaces.DeductUserBalanceCallback;
import com.ayetstudios.publishersdk.interfaces.NativeOffersCallback;
import com.ayetstudios.publishersdk.interfaces.RewardedVideoAsyncCallbackHandler;
import com.ayetstudios.publishersdk.interfaces.RewardedVideoCallbackHandler;
import com.ayetstudios.publishersdk.interfaces.UserBalanceCallback;
import com.ayetstudios.publishersdk.interfaces.VideoAsyncCallbackHandler;
import com.ayetstudios.publishersdk.interfaces.VideoCallbackHandler;
import com.ayetstudios.publishersdk.messages.SdkUserBalance;
import com.ayetstudios.publishersdk.models.NativeOfferList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.gson.Gson;


import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayetstudios.publishersdk.AyetSdk;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WebView webView;
    protected ArrayList<NativeOfferList.AyetOffer> nativeOfferCache;
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // AyetSdk.init(OverviewTask.this.activity.getApplication(), "user" + MyApplication.overviewResponseMessage.getUserId());
        // AyetSdk.init();

        AyetSdk.init(getApplication(), "username (external identifier)", new UserBalanceCallback() {

            @Override
            public void userBalanceChanged(SdkUserBalance sdkUserBalance) {
                Log.d(TAG, "userBalanceChanged");
                ((TextView) findViewById(R.id.textview_account_balance)).setText(Integer.toString(sdkUserBalance.getAvailableBalance()));
            }

            @Override
            public void userBalanceInitialized(SdkUserBalance sdkUserBalance) {
                Log.d(TAG, "userBalanceInitialized");
                ((TextView) findViewById(R.id.textview_account_balance)).setText(Integer.toString(sdkUserBalance.getAvailableBalance()));
            }

            @Override
            public void initializationFailed() {
                Log.d(TAG, "initializationFailed");
            }
        });

        setContentView(R.layout.activity_main);

        if (AyetSdk.isInitialized()) {
            Log.d(TAG , "SDK is ready");
        } else {
            Log.d(TAG , "SDK is NOT ready");
        }

        findViewById(R.id.button_show_offerwall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Show offerwall clicked");
                AyetSdk.showOfferwall(getApplication(), "android_demo_app");
            }
        });

        findViewById(R.id.button_deduct_balance_50).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "Deduct balance 5 clicked");

                int amount = 5;
                AyetSdk.deductUserBalance(getApplicationContext(), amount, new DeductUserBalanceCallback() {
                    @Override
                    public void success() {
                        Log.d(TAG, "deductUserBalance - successful, new available balance: " + AyetSdk.getAvailableBalance(getApplicationContext()));
                        // TODO: activate the purchased content
                    }

                    @Override
                    public void failed() {
                        Log.d(TAG, "deductUserBalance - failed");
                        // this usually means that the user does not have sufficient balance in his account
                    }
                });
            }
        });

        findViewById(R.id.button_process_retention).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Process retention clicked");
                //new RetentionHelper().doCheckRetention(MainActivity.this);

                Toast.makeText(MainActivity.this , "This function is disabled, lib source code is proguarded " , Toast.LENGTH_SHORT).show();

            }
        });


        findViewById(R.id.button_track_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AyetSdk.trackEvent(MainActivity.this, "click_event", 2);
            }
        });


        findViewById(R.id.button_webview_offerwall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);

                MainActivity.this.startActivity(intent);
            }
        });







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}