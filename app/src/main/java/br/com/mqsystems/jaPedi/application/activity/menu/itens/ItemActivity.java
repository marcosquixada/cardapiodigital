package br.com.mqsystems.jaPedi.application.activity.menu.itens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.SessionUserApplication;
import br.com.mqsystems.jaPedi.application.activity.AbstractActionBarActivity;
import br.com.mqsystems.jaPedi.application.util.ProgressDialogUtil;
import br.com.mqsystems.jaPedi.domain.model.Ingrediente;
import br.com.mqsystems.jaPedi.domain.model.Item;
import br.com.mqsystems.jaPedi.domain.model.ItemPedido;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by valter ramos on 7/4/15.
 */
@ContentView(R.layout.activity_item)
public class ItemActivity extends AbstractActionBarActivity {
    @InjectView(R.id.container_listview)
    private ListView mListView;

    @InjectView(R.id.activity_item_textview_nome)
    private TextView mTextViewNome;

    @InjectView(R.id.activity_item_textview_ingredientes)
    private TextView mTextViewIngredientes;

    @InjectView(R.id.activity_item_textview_valor)
    private TextView mTextViewValor;

    @InjectView(R.id.activity_item_quantidade)
    private EditText mEditTextQuantidade;

    @InjectView(R.id.activity_item_add)
    private ImageButton mImageButtonAdd;

    @InjectView(R.id.activity_item_sub)
    private ImageButton mImageButtonSub;

    @InjectView(R.id.activity_item_imageview_imagem)
    private ImageView mImageViewImagem;

    @InjectView(R.id.activity_item_button_send)
    private Button mButton;

    @Inject
    private APIService mAPIService;

    @Inject
    private SessionUserApplication mSessionUserApplication;

    private DisplayImageOptions mOptions;

    private int mQuantidade = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mButton = (Button) findViewById(R.id.activity_item_button_send);

        mEditTextQuantidade.setText(String.valueOf(mQuantidade));

        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final Item item = (Item) bundle.getSerializable("item");
        String ingredientes = "";
        for(Ingrediente ingrediente: item.ingredientes){
            ingredientes = ingredientes + ingrediente.nome + ",";
        }
        if(!ingredientes.equals("")){
            ingredientes = ingredientes.substring(0,ingredientes.length()-1);
        }
        mTextViewNome.setText(item.nome);
        mTextViewIngredientes.setText(ingredientes);
        ImageLoader.getInstance().displayImage(item.imagem.replace("media","japedi/media"), mImageViewImagem, mOptions);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        mTextViewValor.setText(format.format(item.valor));
        mImageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuantidade = mQuantidade + 1;
                mEditTextQuantidade.setText(String.valueOf(mQuantidade));
                NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
                mTextViewValor.setText(format.format(item.valor * mQuantidade));
            }
        });
        mImageButtonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuantidade > 1){
                    mQuantidade = mQuantidade - 1;
                }
                mEditTextQuantidade.setText(String.valueOf(mQuantidade));
                NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
                mTextViewValor.setText(format.format(item.valor * mQuantidade));
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogUtil.showProgress(ItemActivity.this);
                mAPIService.insertItem(new Handler<ItemPedido>() {
                    @Override
                    public void onSuccess(ItemPedido response) {
                        ProgressDialogUtil.dismissProgress();
                        response.item.imagem = item.imagem;
                        mSessionUserApplication.pedido.itens = Arrays.copyOf(mSessionUserApplication.pedido.itens, mSessionUserApplication.pedido.itens.length +1);
                        mSessionUserApplication.pedido.itens[mSessionUserApplication.pedido.itens.length - 1] = response;
                        Toast.makeText(ItemActivity.this,"Seu pedido foi enviado com sucesso!",Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFail(Exception error) {
                        ProgressDialogUtil.dismissProgress();
                        Toast.makeText(ItemActivity.this,"Ocorreu um erro, tente novamente por favor!",Toast.LENGTH_LONG).show();
                    }
                }, mSessionUserApplication.pedido, item, mQuantidade);
            }
        });
    }
}
