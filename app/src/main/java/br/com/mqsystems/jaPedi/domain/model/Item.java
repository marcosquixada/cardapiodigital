package br.com.mqsystems.jaPedi.domain.model;

import java.io.Serializable;

/**
 * Created by valter ramos on 7/21/15.
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 3272069325444070822L;

    public int id;
    public String nome;
    public Float valor;
    public Categoria categoria;
    public Ingrediente[] ingredientes;
    public int tempo;
    public String imagem;
}
