package br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_right;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.mqsystems.jaPedi.R;

/**
 * Created by valter ramos on 3/30/15.
 */
public class SlidingMenuRightFragment extends Fragment {

    public static final String TAG = "SlidingMenuRightFragment";

    private View mViewContainerEmptyNotification;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_right, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewContainerEmptyNotification = view.findViewById(R.id.fragment_sliding_right_container_empty_notification);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_sliding_right_recicleview);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
