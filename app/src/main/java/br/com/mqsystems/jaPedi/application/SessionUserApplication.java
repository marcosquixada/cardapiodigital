package br.com.mqsystems.jaPedi.application;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import br.com.mqsystems.jaPedi.domain.model.Categoria;
import br.com.mqsystems.jaPedi.domain.model.Pedido;

@Singleton
public class SessionUserApplication {

    public String endPoint;
    public Pedido pedido;


    @Inject
    public SessionUserApplication(Context context) {
        pedido = new Pedido();
    }

}
