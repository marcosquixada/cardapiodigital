package br.com.mqsystems.jaPedi.application.activity.menu.itens;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.domain.model.Item;

/**
 * Created by valter ramos on 6/22/15.
 */
public class ItensAdapter extends ArrayAdapter<Item> {

    private DisplayImageOptions mOptions;
    private String endPoint;

    public ItensAdapter(Context context, List<Item> items) {
        super(context, 0, items);

        this.mOptions = new DisplayImageOptions.Builder()
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_listview_item_textview_nome);
            holder.tempo = (TextView) convertView.findViewById(R.id.item_listview_item_textview_tempo);
            holder.valor = (TextView) convertView.findViewById(R.id.item_listview_item_textview_valor);
            holder.avatar = (CircularImageView) convertView.findViewById(R.id.item_listview_item_imageview_avatar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(position);


        ImageLoader.getInstance().displayImage(item.imagem.replace("media","japedi/media"), holder.avatar, mOptions);

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.name.setText(item.nome);
        holder.tempo.setText(String.valueOf(item.tempo) + " minutos");

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        holder.valor.setText(format.format(item.valor));

        return convertView;
    }

    public class ViewHolder {
        public TextView name, tempo, valor;
        public CircularImageView avatar;
    }
}
