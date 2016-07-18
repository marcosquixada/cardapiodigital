package br.com.mqsystems.jaPedi.application.activity.menu.itens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;

import java.util.Arrays;
import java.util.List;

import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.SessionUserApplication;
import br.com.mqsystems.jaPedi.application.activity.AbstractActionBarActivity;
import br.com.mqsystems.jaPedi.application.util.ProgressDialogUtil;
import br.com.mqsystems.jaPedi.domain.model.Categoria;
import br.com.mqsystems.jaPedi.domain.model.Item;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by valter ramos on 7/4/15.
 */
@ContentView(R.layout.activity_itens)
public class ItensActivity extends AbstractActionBarActivity {
    @InjectView(R.id.container_listview)
    private ListView mListView;

    @InjectView(R.id.header_pages_textview_title)
    private TextView mTextViewTitle;

    @Inject
    private APIService mAPIService;

    @Inject
    private SessionUserApplication mSessionUserApplication;


    private ItensAdapter mItensAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextViewTitle.setText(getString(R.string.title_activity_itens));
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Categoria categoria = (Categoria) bundle.getSerializable("categoria");

        ProgressDialogUtil.showProgress(this);

        mAPIService.getItems(new Handler<Item[]>() {
            @Override
            public void onSuccess(Item[] response) {
                setAdapter(Arrays.asList(response));
                ProgressDialogUtil.dismissProgress();
            }

            @Override
            public void onFail(Exception error) {
                ProgressDialogUtil.dismissProgress();
                Toast.makeText(ItensActivity.this,"Ocorreu um erro, tente novamente!",Toast.LENGTH_LONG).show();
            }
        },categoria);
    }

    private void setAdapter(List<Item> itens) {
        mListView.setAdapter(mItensAdapter = new ItensAdapter(this, itens));
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
