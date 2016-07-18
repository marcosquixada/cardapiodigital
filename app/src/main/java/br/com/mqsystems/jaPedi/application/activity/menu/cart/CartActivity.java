package br.com.mqsystems.jaPedi.application.activity.menu.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.SessionUserApplication;
import br.com.mqsystems.jaPedi.application.activity.AbstractActionBarActivity;
import br.com.mqsystems.jaPedi.application.activity.menu.itens.ItemActivity;
import br.com.mqsystems.jaPedi.application.util.ProgressDialogUtil;
import br.com.mqsystems.jaPedi.domain.model.Categoria;
import br.com.mqsystems.jaPedi.domain.model.Item;
import br.com.mqsystems.jaPedi.domain.model.ItemPedido;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by valter ramos on 7/4/15.
 */
@ContentView(R.layout.activity_cart)
public class CartActivity extends AbstractActionBarActivity {
    @InjectView(R.id.container_listview)
    private ListView mListView;

    @InjectView(R.id.header_pages_textview_title)
    private TextView mTextViewTitle;

    @InjectView(R.id.activity_cart_textview_total)
    private TextView mTextViewTotal;

    @Inject
    private APIService mAPIService;

    @Inject
    private SessionUserApplication mSessionUserApplication;


    private ItensPedidoAdapter mItensAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextViewTitle.setText(getString(R.string.title_activity_itens));
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        double total = 0.0;
        setAdapter(Arrays.asList(mSessionUserApplication.pedido.itens));
        for(ItemPedido itemPedido: mSessionUserApplication.pedido.itens){
            total = total + (itemPedido.item.valor * itemPedido.quantidade);
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        mTextViewTotal.setText(format.format(total));
    }

    private void setAdapter(List<ItemPedido> itens) {
        mListView.setAdapter(mItensAdapter = new ItensPedidoAdapter(this, itens));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                pushActivity(ItemActivity.class, bundle);
            }
        });
    }
}
