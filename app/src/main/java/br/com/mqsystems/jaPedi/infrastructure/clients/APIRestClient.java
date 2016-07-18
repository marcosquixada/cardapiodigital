package br.com.mqsystems.jaPedi.infrastructure.clients;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.JsonObject;

import br.com.mqsystems.jaPedi.domain.model.Categoria;
import br.com.mqsystems.jaPedi.domain.model.Ingrediente;
import br.com.mqsystems.jaPedi.domain.model.Item;
import br.com.mqsystems.jaPedi.domain.model.ItemPedido;
import br.com.mqsystems.jaPedi.domain.model.Pedido;
import br.com.mqsystems.jaPedi.domain.model.Valor;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;
import com.ocypode.volleyrestclient.infrastructure.request.RequestHelper;
import com.ocypode.volleyrestclient.infrastructure.request.gson.GsonRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by macksuel on 7/14/15.
 */
public class APIRestClient extends AbstractVolleyRestClient implements APIService {

    public APIRestClient(Context context) {
        super(context);
    }

    @Override
    protected String createUrl(String resource){
        return super.createUrl(resource);
    }

    @Override
    public void getCategorias(final Handler<Categoria[]> handler) {
        String url = createUrl("items/categories/");

        GsonRequest<Categoria[]> request = new GsonRequest<>(
                Request.Method.GET, url, Categoria[].class, handler, new RequestHelper.ListenerCatchingException<Categoria[]>() {

            @Override
            public void onResponse(Categoria[] response) throws JSONException {
                handler.onSuccess(response);
            }
        });

        addToRequestQueue(request);
    }

    @Override
    public void getItems(final Handler<Item[]> handler, Categoria categoria) {
        String url = createUrl("items/" + categoria.id + "/");

        GsonRequest<Item[]> request = new GsonRequest<>(
                Request.Method.GET, url, Item[].class, handler, new RequestHelper.ListenerCatchingException<Item[]>() {

            @Override
            public void onResponse(Item[] response) throws JSONException {
                handler.onSuccess(response);
            }
        });

        addToRequestQueue(request);
    }

    @Override
    public void createOrder(final Handler<Pedido> handler, String endPoint) {
        String url = endPoint;

        GsonRequest<Pedido> request = new GsonRequest<>(
                Request.Method.POST, url, Pedido.class, handler, requestTaskHeader(), new RequestHelper.ListenerCatchingException<Pedido>() {
            @Override
            public void onResponse(Pedido response) throws JSONException {
                handler.onSuccess(response);
            }
        });

        addToRequestQueue(request);
    }

    @Override
    public void insertItem(final Handler<ItemPedido> handler, Pedido pedido, Item item) {
        String url = createUrl("item_order/" + pedido.id + "/" + item.id);

        GsonRequest<ItemPedido> request = new GsonRequest<>(
                Request.Method.POST, url, ItemPedido.class, handler, requestTaskHeader(), new RequestHelper.ListenerCatchingException<ItemPedido>() {
            @Override
            public void onResponse(ItemPedido response) throws JSONException {
                handler.onSuccess(response);
            }
        });

        addToRequestQueue(request);
    }


    @Override
    public void sendReservas(final Handler<JsonObject> handler, String data, String nome, String quantidade, String contato) {
        String url = createUrl("reservas");

        GsonRequest<JsonObject> request = new GsonRequest<>(
                Request.Method.POST, url, JsonObject.class, handler, requestTaskHeader(), requestReservationParams(data, nome, quantidade, contato), new RequestHelper.ListenerCatchingException<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) throws JSONException {
                handler.onSuccess(response);
            }
        });

        addToRequestQueue(request);
    }

    @Override
    public void sendRegister(final Handler<Boolean> handler, String token, String name) {
        String url = createUrl("register");

        GsonRequest<JsonObject> request = new GsonRequest<>(
                Request.Method.POST, url, JsonObject.class, handler, requestTaskHeader(), requestRegisterParams(token, name), new RequestHelper.ListenerCatchingException<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) throws JSONException {
                handler.onSuccess(true);
            }
        });

        addToRequestQueue(request);
    }


    private Map<String, String> requestReservationParams(String data,String nome, String quantidade, String contato) {
        Map<String, String> params = new HashMap<>();
        params.put("data", data);
        params.put("nome", nome);
        params.put("numero_pessoas", quantidade);
        params.put("contato", contato);
        return params;
    }

    private Map<String, String> requestRegisterParams(String token, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("name", name);
        params.put("operating_system","Android");
        return params;
    }

    private Map<String, String> requestTaskHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Charset", "UTF-8");
        return headers;
    }
}
