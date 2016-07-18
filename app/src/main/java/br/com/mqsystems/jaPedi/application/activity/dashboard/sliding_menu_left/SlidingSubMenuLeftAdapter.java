package br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_left;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.mqsystems.jaPedi.domain.model.Menu;
import br.com.mqsystems.jaPedi.R;

import java.util.List;

/**
 * Created by valter ramos on 3/30/15.
 */
public class SlidingSubMenuLeftAdapter extends ArrayAdapter<Menu> {

    public SlidingSubMenuLeftAdapter(Context context, List<Menu> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_submenu, parent, false);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item_dashboard_submenu_textview_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(getItem(position).title);

        return convertView;
    }

    private class ViewHolder {
        public TextView title;
    }

}
