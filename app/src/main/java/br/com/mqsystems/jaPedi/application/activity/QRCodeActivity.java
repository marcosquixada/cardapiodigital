package br.com.mqsystems.jaPedi.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ocypode.volleyrestclient.infrastructure.handler.Handler;

import br.com.mqsystems.jaPedi.R;
import br.com.mqsystems.jaPedi.application.SessionUserApplication;
import br.com.mqsystems.jaPedi.application.activity.dashboard.DashboardActivity;
import br.com.mqsystems.jaPedi.application.util.ProgressDialogUtil;
import br.com.mqsystems.jaPedi.domain.model.Categoria;
import br.com.mqsystems.jaPedi.domain.model.Pedido;
import br.com.mqsystems.jaPedi.infrastructure.services.APIService;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_qrcode)
public class QRCodeActivity extends AbstractActionBarActivity {

    @InjectView(R.id.QrCode)
    private ImageView mImageView;

    @Inject
    private APIService mAPIService;

    @Inject
    private SessionUserApplication mSessionUserApplication;

    private String toast, qrcode;
    private final int TIME_DELAY = 3000;

    private android.os.Handler mHandler = new android.os.Handler();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFromFragment();
            }
        });
        /*ProgressDialogUtil.showProgress(this);
        mAPIService.createOrder(new Handler<Pedido>() {
            @Override
            public void onSuccess(Pedido response) {
                mSessionUserApplication.pedido = response;
                ProgressDialogUtil.dismissProgress();
                pushActivity(DashboardActivity.class);
            }

            @Override
            public void onFail(Exception error) {
                ProgressDialogUtil.dismissProgress();
            }
        },"http://192.241.241.25/japedi/create_order/1");*/

    }

    public void scanFromFragment() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                toast = "Cancelado pelo usuário";
                displayToast();
            } else {
                String endPoint = result.getContents();
                ProgressDialogUtil.showProgress(this);
                mAPIService.createOrder(new Handler<Pedido>() {
                    @Override
                    public void onSuccess(Pedido response) {
                        mSessionUserApplication.pedido = response;
                        ProgressDialogUtil.dismissProgress();
                        pushActivity(DashboardActivity.class);
                    }

                    @Override
                    public void onFail(Exception error) {
                        ProgressDialogUtil.dismissProgress();
                        Toast.makeText(QRCodeActivity.this, "A mesa já esta aberta. Por favor verifique com um garçom! ", Toast.LENGTH_LONG).show();
                    }
                },endPoint);
            }
        }
    }

    private void displayToast() {
        if(toast != null) {
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
