package br.com.mqsystems.jaPedi.application.activity.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.mqsystems.jaPedi.domain.model.Categoria;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import br.com.mqsystems.jaPedi.R;

import java.util.List;

/**
 * Created by valter ramos on 6/22/15.
 */
public class CategoriasAdapter extends ArrayAdapter<Categoria> {

    private String endPoint;

    public CategoriasAdapter(Context context, List<Categoria> items, String endPoint) {
        super(context, 0, items);

        this.endPoint = endPoint;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_categoria, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_listview_categoria_textview_nome);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Categoria categoria = getItem(position);

        holder.name.setText(categoria.nome);

        return convertView;
    }

    public class ViewHolder {
        public TextView name;
    }
}
