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

        findViewById(R.id.button_prepare_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "Video Ad clicked");

                AyetSdk.showVideoAd(MainActivity.this, "default_video", AyetSdk.FLAG_DEFAULT, new VideoCallbackHandler() {
                    @Override
                    public void nofill() {
                        Log.d(TAG , "AyetSdk.showVideoAd::nofill()");
                    }
                    @Override
                    public void finished() {
                        Log.d(TAG , "AyetSdk.showVideoAd::finished()");
                    }
                    @Override
                    public void willBeShown() {
                        Log.d(TAG , "AyetSdk.showVideoAd::willBeShown()");
                    }
                });
            }
        });


        findViewById(R.id.button_prepare_video_vertical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "Vertical Video Ad clicked");

                AyetSdk.showVideoAd(MainActivity.this, "default_video",AyetSdk.FLAG_ORIENTATION_PORTRAIT, new VideoCallbackHandler() {
                    @Override
                    public void nofill() {
                        Log.d(TAG , "AyetSdk.showVideoAd::nofill()");
                    }
                    @Override
                    public void finished() {
                        Log.d(TAG , "AyetSdk.showVideoAd::finished()");
                    }
                    @Override
                    public void willBeShown() {
                        Log.d(TAG , "AyetSdk.showVideoAd::willBeShown()");
                    }
                });
            }
        });


        findViewById(R.id.button_start_video_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG , "Start Video Activity");
                //AyetSdk.showVideo(MainActivity.this);

                Log.d(TAG , "Video Ad Async clicked");

                AyetSdk.showVideoAd(MainActivity.this, "default_video",AyetSdk.FLAG_ASYNC, new VideoAsyncCallbackHandler() {
                    @Override
                    public void nofill() {
                        Log.d(TAG , "AyetSdk.showVideoAdAsync::nofill()");
                    }
                    @Override
                    public void finished() {
                        Log.d(TAG , "AyetSdk.showVideoAdAsync::finished()");
                    }
                    @Override
                    public void willBeShown() {
                        Log.d(TAG , "AyetSdk.showVideoAdAsync::willBeShown()");
                    }
                    @Override
                    public void ready(final VideoAdInterstitial videoAd) {
                        Log.d(TAG , "AyetSdk.showVideoAdAsync::ready()");

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                videoAd.showVideo();
                            }
                        } , 4000);
                    }
                });
            }
        });


        findViewById(R.id.button_rewarded_video_wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AyetSdk.showVideoAd(MainActivity.this, "default_video",AyetSdk.FLAG_REWARDED | AyetSdk.FLAG_WIFI_ONLY, new RewardedVideoCallbackHandler() {
                    @Override
                    public void aborted() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::aborted()");
                    }
                    @Override
                    public void completed(int amount) {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::completed()   Amount: "+Integer.toString(amount));
                    }
                    @Override
                    public void nofill() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::nofill()");
                    }
                    @Override
                    public void finished() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::finished()");
                    }
                    @Override
                    public void willBeShown() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::willBeShown()");
                    }
                });
            }
        });

        findViewById(R.id.button_rewarded_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AyetSdk.showVideoAd(MainActivity.this, "default_video",AyetSdk.FLAG_REWARDED, new RewardedVideoCallbackHandler() {
                    @Override
                    public void aborted() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::aborted()");
                    }
                    @Override
                    public void completed(int amount) {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::completed()   Amount: "+Integer.toString(amount));
                    }
                    @Override
                    public void nofill() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::nofill()");
                    }
                    @Override
                    public void finished() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::finished()");
                    }
                    @Override
                    public void willBeShown() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::willBeShown()");
                    }
                });
            }
        });

        findViewById(R.id.button_rewarded_video_vertical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AyetSdk.showVideoAd(MainActivity.this, "default_video",AyetSdk.FLAG_REWARDED | AyetSdk.FLAG_ORIENTATION_PORTRAIT, new RewardedVideoCallbackHandler() {
                    @Override
                    public void aborted() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::aborted()");
                    }
                    @Override
                    public void completed(int amount) {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::completed()   Amount: "+Integer.toString(amount));
                    }
                    @Override
                    public void nofill() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::nofill()");
                    }
                    @Override
                    public void finished() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::finished()");
                    }
                    @Override
                    public void willBeShown() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAd::willBeShown()");
                    }
                });
            }
        });

        findViewById(R.id.button_rewarded_video_async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AyetSdk.showVideoAd(MainActivity.this, "default_video",AyetSdk.FLAG_REWARDED | AyetSdk.FLAG_ASYNC, new RewardedVideoAsyncCallbackHandler() {
                    @Override
                    public void ready(final VideoAdInterstitial videoAd) {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAdAsync::ready()");

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                videoAd.showVideo();
                            }
                        } , 5000);
                    }
                    @Override
                    public void aborted() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAdAsync::aborted()");
                    }
                    @Override
                    public void completed(int amount) {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAdAsync::completed()   Amount: "+Integer.toString(amount));
                    }
                    @Override
                    public void nofill() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAdAsync::nofill()");
                    }
                    @Override
                    public void finished() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAdAsync::finished()");
                    }
                    @Override
                    public void willBeShown() {
                        Log.d(TAG , "AyetSdk.showRewardedVideoAdAsync::willBeShown()");
                    }
                });

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

        findViewById(R.id.button_request_native).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AyetSdk.getNativeOffers(MainActivity.this.getApplication(), "android_native_offers", new NativeOffersCallback() {
                    @Override
                    public void onResult(boolean success, NativeOfferList responseMessage) {

                        Toast.makeText(MainActivity.this , "SUCCESS : "+responseMessage.offers.size()+"!!!" , Toast.LENGTH_LONG).show();
                        Log.e("JSON Native Offers",  new Gson().toJson( responseMessage.offers ) );
                        ((TextView)MainActivity.this.findViewById(R.id.textView)).setText(new Gson().toJson( responseMessage.offers ));
                        nativeOfferCache=responseMessage.offers;

                    }
                });
            }
        });

        findViewById(R.id.button_activate_native_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.this.nativeOfferCache==null || MainActivity.this.nativeOfferCache.size()<1)
                    Toast.makeText(MainActivity.this , "Native Offer #1 not available!" , Toast.LENGTH_LONG).show();
                else {
                    AyetSdk.activateOffer(MainActivity.this, nativeOfferCache.get(0).getId(), new ActivateOfferCallback() {
                        @Override
                        public void onFailed() {
                            Toast.makeText(MainActivity.this , "Native Offer #1 - onFailed" , Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(MainActivity.this , "Native Offer #1 - onSuccess" , Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


        findViewById(R.id.button_activate_native_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.this.nativeOfferCache==null || MainActivity.this.nativeOfferCache.size()<2)
                    Toast.makeText(MainActivity.this , "Native Offer #2 not available!" , Toast.LENGTH_LONG).show();
                else {
                    AyetSdk.activateOffer(MainActivity.this, nativeOfferCache.get(1).getId(), new ActivateOfferCallback() {
                        @Override
                        public void onFailed() {
                            Toast.makeText(MainActivity.this , "Native Offer #2 - onFailed" , Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(MainActivity.this , "Native Offer #2 - onSuccess" , Toast.LENGTH_LONG).show();
                        }
                    });
                }
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