package com.adida.aka.boundservicelocal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtNumA, mEdtNumB;
    private Button mBtnSum;
    private ServiceConnection mConnection;
    private boolean isConnect;
    private SumNumberService mSumNumberService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        connectionServicve();
    }

    private void connectionServicve() {
        Intent intent = new Intent(this, SumNumberService.class);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                SumNumberService.SumBinder sumBinder = (SumNumberService.SumBinder) service;
                mSumNumberService = sumBinder.getService();
                isConnect = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnect = false;
                mSumNumberService = null;
            }
        };
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private void initView() {
        mEdtNumA = (EditText) findViewById(R.id.edt_num_a);
        mEdtNumB = (EditText) findViewById(R.id.edt_num_b);
        mBtnSum  = (Button) findViewById(R.id.btn_sum);
    }

    public void sum(View view) {
        if (isConnect == false){
            return;
        }
        int a = Integer.parseInt(mEdtNumA.getText().toString());
        int b = Integer.parseInt(mEdtNumB.getText().toString());
        int result = mSumNumberService.add(a, b);
        Toast.makeText(mSumNumberService, "Sum : "+ result, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
