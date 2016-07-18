package br.com.mqsystems.jaPedi.application;

import android.app.Application;
import android.content.Context;

import com.google.inject.AbstractModule;
import br.com.mqsystems.jaPedi.infrastructure.clients.APIRestClient;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;

/**
 * Created by valter ramos on 5/17/15.
 */
public class VolleyFactoryModule extends AbstractModule {

    private Context mContext;

    public VolleyFactoryModule(Application application) {
        this.mContext = application.getApplicationContext();
    }

    @Override
    public void configure() {
        bind(APIService.class).toInstance(new APIRestClient(mContext));
    }
}
