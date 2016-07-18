package br.com.mqsystems.jaPedi.domain.model;

import java.io.Serializable;

/**
 * Created by valter on 20/01/16.
 */
public class Pedido implements Serializable {

    private static final long serialVersionUID = 3271111325444075822L;

    public int id;
    public Mesa mesa;
    public int status;
    public FormaPagamento forma_pagamento;
    public FormaPagamento[] formas_pagamento;
    public ItemPedido[] itens;
    public Categoria[] categorias;
    public Float total;
}
