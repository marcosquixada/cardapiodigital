package br.com.mqsystems.jaPedi.application.receivers;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import br.com.mqsystems.jaPedi.application.util.NotificationUtil;
import br.com.mqsystems.jaPedi.domain.model.Ingrediente;


public class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {
    public static final String TAG = "LOG";


    @Override
    public void onMessageReceived(String from, Bundle data) {
        Gson gson = new GsonBuilder().create();

        Ingrediente joke = new Ingrediente();
        Context context = getBaseContext();
        String message = data.getString("message");
        if (message != null)
            joke = gson.fromJson(message, Ingrediente.class);
        NotificationUtil.createJokeNotification(context, joke);
    }
}
