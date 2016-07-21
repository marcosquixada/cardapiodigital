package br.com.mqsystems.jaPedi.infrastructure.services;


import br.com.mqsystems.jaPedi.domain.model.Categoria;
import br.com.mqsystems.jaPedi.domain.model.Ingrediente;
import br.com.mqsystems.jaPedi.domain.model.Item;
import br.com.mqsystems.jaPedi.domain.model.ItemPedido;
import br.com.mqsystems.jaPedi.domain.model.Pedido;
import br.com.mqsystems.jaPedi.domain.model.Valor;

import com.google.gson.JsonObject;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;

public interface APIService {
    void getCategorias(Handler<Categoria[]> handler);
    void getItems(Handler<Item[]> handler, Categoria categoria);
    void createOrder(final Handler<Pedido> handler, String endPoint);
    void insertItem(final Handler<ItemPedido> handler, Pedido pedido, Item item, int quantidade);
    void sendReservas(Handler<JsonObject> handler,String data, String nome, String quantidade, String contato);
    void sendRegister(Handler<Boolean> handler, String token, String name);
}
