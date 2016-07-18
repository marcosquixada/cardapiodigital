package br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_left;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.mqsystems.jaPedi.application.activity.AbstractFragment;
import br.com.mqsystems.jaPedi.domain.model.Menu;
import br.com.mqsystems.jaPedi.domain.model.stubs.MenuStub;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import br.com.mqsystems.jaPedi.R;

import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by valter ramos on 3/30/15.
 */
public class SlidingMenuLeftFragment extends AbstractFragment {

    public static final String TAG = "SlidingMenuLeftFragment";

    @InjectView(R.id.fragment_menu_listview)
    private ListView mListView;

    private List<Object> mListMenu;

    private LayoutInflater mInflater;
    private DisplayImageOptions options;
    private MenuListener mMenuListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mMenuListener = (MenuListener) activity;
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        return mInflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListMenu = MenuStub.createMenu();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        configureHeader();
        //configureFooter();

        mListView.setAdapter(new SlidingMenuLeftAdapter(getActivity(), mListMenu));
        mListView.setOnItemClickListener(onItemClickListener);
    }

    private void configureHeader() {
        if (mListView != null) {
            View header = mInflater.inflate(R.layout.container_menu_header, null);
            mListView.addHeaderView(header);
        }
    }

    private void configureFooter() {
        if (mListView != null) {
            View footer = mInflater.inflate(R.layout.container_menu_footer, null);
            View container = footer.findViewById(R.id.container_menu_footer);
            container.setOnClickListener(onClickProfileListener);
            mListView.addFooterView(footer);
        }
    }

    private final View.OnClickListener onClickProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //pushActivity(ProfileActivity.class);
        }
    };

    private final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Menu menu = (Menu) parent.getItemAtPosition(position);

            if (menu != null && menu.hasSubMenu()) {
                SlidingSubMenuLeftFragment menuChildFragment = SlidingSubMenuLeftFragment.newInstance(menu);

                pushSubMenu(menu, menuChildFragment);
            } else {
                if (mMenuListener != null) {
                    mMenuListener.onMenuItemClick(menu);
                }
            }
        }
    };

    private void pushSubMenu(Menu menuItem, SlidingSubMenuLeftFragment menuChildFragment) {
        FragmentManager supportFragmentManager = getFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentByTag(SlidingSubMenuLeftFragment.PREFIX + menuItem.id);

        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit);
        if (fragment == null) {
            ft.add(R.id.content_sliding_menu_left, menuChildFragment, SlidingSubMenuLeftFragment.PREFIX + menuItem.id);
        } else {
            ft.show(fragment);
        }
        ft.hide(SlidingMenuLeftFragment.this);
        ft.commit();
    }

    public interface MenuListener {
        void onMenuItemClick(Menu menu);
    }
}
