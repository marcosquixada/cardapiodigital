package br.com.mqsystems.jaPedi.application.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by valter ramos on 7/21/15.
 */
public class ProgressDialogUtil {

    private static ProgressDialog progress;

    public static void showProgress(Context context) {
        progress = new ProgressDialog(context);
        progress.setMessage("Aguardar...");
        progress.show();
    }

    public static void showProgress(Context context, String message) {
        progress = new ProgressDialog(context);
        progress.setMessage(message);
        progress.show();
    }

    public static void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }
}
