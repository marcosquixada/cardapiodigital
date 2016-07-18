package br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_left;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mqsystems.jaPedi.domain.model.Menu;
import br.com.mqsystems.jaPedi.domain.model.MenuSection;
import br.com.mqsystems.jaPedi.R;

import java.util.List;

/**
 * Created by valter ramos on 3/30/15.
 */
public class SlidingMenuLeftAdapter extends BaseAdapter {

    private final int VIEW_TYPE_MENU = 0, VIEW_TYPE_MENU_SECTION = 1;
    private Context mContext;
    private List<Object> items;

    public SlidingMenuLeftAdapter(Context context, List<Object> objects) {
        this.mContext = context;
        this.items = objects;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    public Menu getMenuItem(int position) {
        return (Menu) getItem(position);
    }

    public MenuSection getMenuSection(int position) {
        return (MenuSection) getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Menu) {
            return VIEW_TYPE_MENU;
        }

        return VIEW_TYPE_MENU_SECTION;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == VIEW_TYPE_MENU) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_menu, parent, false);

                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.item_dashboard_menu_textview_title);
                holder.icon = (ImageView) convertView.findViewById(R.id.item_dashboard_menu_imageview_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.icon.setImageResource(getMenuItem(position).icon);
            holder.title.setText(getMenuItem(position).title);

        } else {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_menu_section, parent, false);

                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.item_dashboard_menu_section_textview_title);
                holder.icon = (ImageView) convertView.findViewById(R.id.item_dashboard_menu_section_imageview_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.icon.setImageResource(getMenuSection(position).icon);
            holder.title.setText(getMenuSection(position).title);
            convertView.setOnClickListener(null);
        }

        return convertView;
    }

    private class ViewHolder {
        public TextView title;
        public ImageView icon;
    }


}
