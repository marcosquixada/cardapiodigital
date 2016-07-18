package br.com.mqsystems.jaPedi.application.activity.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

import br.com.mqsystems.jaPedi.application.SessionUserApplication;
import br.com.mqsystems.jaPedi.application.activity.AbstractActionBarActivity;
import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.activity.menu.itens.ItensActivity;
import br.com.mqsystems.jaPedi.domain.model.Categoria;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;

import java.util.Arrays;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by valter ramos on 7/4/15.
 */
@ContentView(R.layout.activity_categorias)
public class CategoriasActivity extends AbstractActionBarActivity {
    @InjectView(R.id.container_listview)
    private ListView mListView;

    @InjectView(R.id.header_pages_textview_title)
    private TextView mTextViewTitle;

    @Inject
    private APIService mAPIService;

    @Inject
    private SessionUserApplication mSessionUserApplication;


    private CategoriasAdapter mCategoriasAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextViewTitle.setText(getString(R.string.title_activity_categorias));
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setAdapter(Arrays.asList(mSessionUserApplication.pedido.categorias));
    }

    private void setAdapter(List<Categoria> categorias) {
        mListView.setAdapter(mCategoriasAdapter = new CategoriasAdapter(this, categorias, mSessionUserApplication.endPoint));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("categoria", categoria);
                pushActivity(ItensActivity.class, bundle);
            }
        });
    }
}
