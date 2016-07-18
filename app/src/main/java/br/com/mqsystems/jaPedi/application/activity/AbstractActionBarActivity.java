package br.com.mqsystems.jaPedi.application.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ocypode.application.activity.robo.AbstractRoboActionBarActivity;
import br.com.mqsystems.jaPedi.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by valter ramos on 3/26/15.
 */
abstract public class AbstractActionBarActivity extends AbstractRoboActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        toolbar = (Toolbar) findViewById(R.id.container_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (toolbar != null) {
            ((TextView) toolbar.findViewById(R.id.container_toolbar_textview_title)).setText(title);
        }
        super.setTitle("");
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
        super.setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
