package br.com.mqsystems.jaPedi.application.activity.about;

import android.os.Bundle;

import br.com.mqsystems.jaPedi.application.activity.AbstractActionBarActivity;
import br.com.mqsystems.jaPedi.R;

import roboguice.inject.ContentView;

/**
 * Created by valter ramos on 7/25/15.
 */
@ContentView(R.layout.activity_about)
public class AboutActivity extends AbstractActionBarActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setTitle(getString(R.string.title_activity_about));
    }
}
