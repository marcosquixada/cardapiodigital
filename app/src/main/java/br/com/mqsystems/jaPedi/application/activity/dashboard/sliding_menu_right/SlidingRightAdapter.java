package br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_right;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mqsystems.jaPedi.R;

/**
 * Created by valter ramos on 5/17/15.
 */
public class SlidingRightAdapter extends RecyclerView.Adapter<SlidingRightAdapter.RecyclerViewHolder> {

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_sliding_menu_right, parent, false);

        RecyclerViewHolder vh = new RecyclerViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewTitle, mTextViewDescription, mTextViewDate;
        public ImageView mImageViewIcon;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            mTextViewTitle = (TextView) itemView.findViewById(R.id.item_sliding_menu_right_textview_title);
            mTextViewDescription = (TextView) itemView.findViewById(R.id.item_sliding_menu_right_textview_description);
            mTextViewDate = (TextView) itemView.findViewById(R.id.item_sliding_menu_right_textview_date);
        }
    }
}
