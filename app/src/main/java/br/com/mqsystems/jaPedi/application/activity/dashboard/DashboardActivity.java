package br.com.mqsystems.jaPedi.application.activity.dashboard;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.activity.AbstractActionBarActivity;
import br.com.mqsystems.jaPedi.application.activity.menu.CategoriasActivity;
import br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_left.SlidingMenuLeftFragment;
import br.com.mqsystems.jaPedi.application.activity.dashboard.sliding_menu_right.SlidingMenuRightFragment;

import br.com.mqsystems.jaPedi.application.activity.menu.cart.CartActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by valter ramos on 3/30/15.
 */
@ContentView(R.layout.activity_dashboard)
public class DashboardActivity extends AbstractActionBarActivity implements SlidingMenuLeftFragment.MenuListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mSlidingLeft, mSlidingRight;

    @InjectView(R.id.activity_dashboard_button_menu)
    private Button mButtonMenu;

    @InjectView(R.id.activity_dashboard_button_garcom)
    private Button mButtonGarcom;

    @InjectView(R.id.activity_dashboard_button_delivery)
    private Button mButtonDelivery;

    @InjectView(R.id.activity_dashboard_button_paymment)
    private Button mButtonPaymment;

    @InjectView(R.id.activity_dashboard_button_reservas)
    private Button mButtonReservas;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.title_activity_dashboard));


        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushActivity(CategoriasActivity.class);
            }
        });

        mButtonDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushActivity(CategoriasActivity.class);
            }
        });

        mButtonPaymment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushActivity(CartActivity.class);
            }
        });

        mSlidingLeft = findViewById(R.id.content_sliding_menu_left);
        mSlidingRight = findViewById(R.id.content_sliding_menu_right);

        setupDrawer();
    }

    private void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerView.getId() == R.id.content_sliding_menu_left) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (drawerView.getId() == R.id.content_sliding_menu_left) {
                    super.onDrawerClosed(drawerView);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (drawerView.getId() == R.id.content_sliding_menu_left) {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            }

        };
        mDrawerToggle.syncState();
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        replaceFragment(R.id.content_sliding_menu_left, new SlidingMenuLeftFragment(), SlidingMenuLeftFragment.TAG);

        replaceFragment(R.id.content_sliding_menu_right, new SlidingMenuRightFragment(), SlidingMenuRightFragment.TAG);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if (mDrawerLayout.isDrawerOpen(mSlidingRight)) {
                mDrawerLayout.closeDrawer(mSlidingRight);
            }
            return true;
        }
        // Handle your other action bar items...

        switch (item.getItemId()) {
            /*case R.id.menu_dashboard_notification:
                if (mDrawerLayout.isDrawerOpen(mSlidingRight)) {
                    mDrawerLayout.closeDrawer(mSlidingRight);
                } else {
                    mDrawerLayout.closeDrawer(mSlidingLeft);
                    mDrawerLayout.openDrawer(mSlidingRight);
                }

                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onMenuItemClick(br.com.mqsystems.jaPedi.domain.model.Menu menu) {
        if (menu != null && menu.clazz != null) {
            pushActivity(menu.clazz);
        } else {
            Toast.makeText(this, "Not implemented.", Toast.LENGTH_SHORT).show();
        }
    }
}
