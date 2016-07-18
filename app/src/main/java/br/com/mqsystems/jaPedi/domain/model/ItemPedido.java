package br.com.mqsystems.jaPedi.domain.model;

import java.io.Serializable;

/**
 * Created by valter ramos on 7/21/15.
 */
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 3272069325444070822L;

    public int id;
    public Item item;
    public int quantidade;
    public int status;
}
