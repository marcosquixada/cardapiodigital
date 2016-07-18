package br.com.mqsystems.jaPedi.application.activity.resevations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.activity.AbstractActionBarActivity;
import br.com.mqsystems.jaPedi.application.activity.dashboard.DashboardActivity;
import br.com.mqsystems.jaPedi.application.util.ProgressDialogUtil;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by valter ramos on 7/25/15.
 */
@ContentView(R.layout.activity_reservation)
public class ReservationsActivity extends AbstractActionBarActivity {

    @InjectView(R.id.header_pages_textview_title)
    private TextView mTextViewTitle;

    @InjectView(R.id.activity_reservation_textview_nome)
    private EditText mEditTextNome;

    @InjectView(R.id.activity_reservation_textview_contato)
    private EditText mEdiTextContato;

    @InjectView(R.id.activity_reservation_numberpicker_pessoas)
    private NumberPicker mNumberPicker;

    @InjectView(R.id.activity_reservation_datepicker_date)
    private DatePicker mDatePicker;

    @InjectView(R.id.activity_reservation_button_enviar)
    private Button mButtonEnviar;

    @Inject
    private APIService mAPIService;

    int mNumberPerson = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mTextViewTitle.setText(getString(R.string.title_activity_reservation));
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(30);
        mNumberPicker.setWrapSelectorWheel(true);

        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mNumberPerson = newVal;
            }
        });

        mDatePicker.setMinDate(System.currentTimeMillis() - 1000);
        mButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReservas();
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public String getDateFromDatePicker(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year =  mDatePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String data = sdf.format(calendar.getTime());
        return data;
    }

    private Boolean checkFields(){
        if(TextUtils.isEmpty(mEditTextNome.getText())){
            Toast.makeText(ReservationsActivity.this, "O campo nome é requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(mEdiTextContato.getText())){
            Toast.makeText(ReservationsActivity.this, "O campo contato é requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void sendReservas() {
        if (checkFields()) {
            ProgressDialogUtil.showProgress(this);
            mAPIService.sendReservas(new Handler<JsonObject>() {
                @Override
                public void onSuccess(JsonObject response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReservationsActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Notificação");
                        String status = response.get("status").getAsString();
                        if(status.equals("ok")){
                            showDialogSuccess();
                        }
                        else if(status.equals("time")){
                            builder.setMessage("Não foi possivel efetuar sua a reserva. O horario para reservas no mesmo dia só pode ser feito até as 17h!").
                                    setPositiveButton(getString(R.string.ok), dialogClickListener).show();
                        }else if(status.equals("day")){
                            builder.setMessage("Não foi possivel efetuar sua a reserva. Não temos reservas para os dias de Segunda-feira, Quarta-feira e Domingo.").
                                    setPositiveButton(getString(R.string.ok), dialogClickListener).show();
                        }
                    ProgressDialogUtil.dismissProgress();
                }

                @Override
                public void onFail(Exception error) {
                    Toast.makeText(ReservationsActivity.this, "Falha ao enviar a reserva!", Toast.LENGTH_SHORT).show();
                    ProgressDialogUtil.dismissProgress();
                }
            }, getDateFromDatePicker(), mEditTextNome.getText().toString(), String.valueOf(mNumberPerson), mEdiTextContato.getText().toString());
        }
    }

    private final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    dialog.dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };


    private void showDialogSuccess() {
        final Dialog dialog = new Dialog(this, R.style.DialogFullscreen);
        dialog.setCancelable(true);
        dialog.setTitle(getTitle());
        dialog.setContentView(R.layout.dialog_thanks);

        dialog.findViewById(R.id.dialog_thanks_button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                gotoDashboard();
            }
        });

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void gotoDashboard() {
        Intent i = new Intent(this, DashboardActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        ReservationsActivity.this.finish();
    }
}
