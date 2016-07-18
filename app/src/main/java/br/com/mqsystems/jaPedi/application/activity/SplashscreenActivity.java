package br.com.mqsystems.jaPedi.application.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.inject.Inject;
import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.activity.dashboard.DashboardActivity;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;

import java.io.IOException;

import roboguice.inject.ContentView;

@ContentView(R.layout.activity_splashscreen)
public class SplashscreenActivity extends AbstractActionBarActivity {

    @Inject
    private APIService mAPIService;

    private final int TIME_DELAY = 3000;

    private android.os.Handler mHandler = new android.os.Handler();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        sendRegister();
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private void sendRegister(){
        new Thread(new Runnable() {
            public void run() {
                InstanceID instanceID = InstanceID.getInstance(getBaseContext());
                String token = null;
                try {
                    token = instanceID.getToken(getString(R.string.senderID),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    String name = getDeviceName();
                    mAPIService.sendRegister(new Handler<Boolean>() {
                        @Override
                        public void onSuccess(Boolean response) {
                        }

                        @Override
                        public void onFail(Exception error) {
                        }
                    }, token, name);
                } catch (IOException e) {
                }
                mHandler.postDelayed(mRunnable, TIME_DELAY);
            }
        }).start();
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            pushActivity(QRCodeActivity.class);
            SplashscreenActivity.this.finish();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(this, getResources().getString(R.string.loading_users_data), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);

        super.onDestroy();

    }
}
