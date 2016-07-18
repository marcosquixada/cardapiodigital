package br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_left;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import br.com.mqsystems.jaPedi.application.activity.AbstractFragment;
import br.com.mqsystems.jaPedi.domain.model.Menu;
import br.com.mqsystems.jaPedi.R;

import roboguice.inject.InjectView;

public class SlidingSubMenuLeftFragment extends AbstractFragment {

    public static final String PREFIX = "sub_", KEY_EXTRA_MENU = "KEY_EXTRA_MENU";

    private static final String MENU_EXTRA_KEY = "MENU_EXTRA_KEY";

    @InjectView(R.id.fragment_sub_menu_oclistview)
    private ListView mMenuSubListView;

    @InjectView(R.id.fragment_sub_menu_button_back)
    private FrameLayout mFrameButtonBack;

    @InjectView(R.id.fragment_sub_menu_textview_title)
    private TextView mTextViewTitle;

    public static SlidingSubMenuLeftFragment newInstance(Menu menu) {
        SlidingSubMenuLeftFragment menuChildFragment = new SlidingSubMenuLeftFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MENU_EXTRA_KEY, menu);
        menuChildFragment.setArguments(bundle);
        return menuChildFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mFrameButtonBack.setOnClickListener(onBackButtonClickListener);

            Menu menu = (Menu) getArguments().getSerializable(MENU_EXTRA_KEY);

            mTextViewTitle.setText(menu.title);
            configureSubMenuAdapter(menu);
        }
    }

    private void configureSubMenuAdapter(Menu menu) {
        SlidingSubMenuLeftAdapter adapter = new SlidingSubMenuLeftAdapter(getActivity(), menu.subMenu);
        mMenuSubListView.setAdapter(adapter);
        mMenuSubListView.setOnItemClickListener(onItemClickListener);
    }

    private final OnItemClickListener onItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Menu menuItem = (Menu) adapter.getItemAtPosition(position);
            Bundle extras = new Bundle();
            extras.putSerializable(KEY_EXTRA_MENU, menuItem);
            pushActivity(menuItem.clazz, extras);
        }
    };

    private final OnClickListener onBackButtonClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Fragment menuFragment = getFragmentManager().findFragmentByTag(SlidingMenuLeftFragment.TAG);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(com.ocypode.application.R.anim.slide_left_enter, com.ocypode.application.R.anim.slide_left_exit);
            ft.hide(SlidingSubMenuLeftFragment.this);
            ft.show(menuFragment);
            ft.commit();
        }
    };
}