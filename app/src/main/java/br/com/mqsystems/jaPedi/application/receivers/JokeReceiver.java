package br.com.mqsystems.jaPedi.application.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import br.com.mqsystems.jaPedi.application.util.NotificationUtil;
import br.com.mqsystems.jaPedi.domain.model.Ingrediente;


public class JokeReceiver extends BroadcastReceiver {
    public JokeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

        Ingrediente joke = new Ingrediente();

        String jokeStr = intent.getStringExtra("message");
        if (jokeStr != null)
            joke = gson.fromJson(jokeStr, Ingrediente.class);
        NotificationUtil.createJokeNotification(context, joke);
    }
}
