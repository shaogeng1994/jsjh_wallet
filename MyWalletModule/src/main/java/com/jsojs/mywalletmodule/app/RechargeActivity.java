package com.jsojs.mywalletmodule.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.RechargeOrder;
import com.jsojs.mywalletmodule.contract.RechargeContract;
import com.jsojs.mywalletmodule.presenter.RechargePresenter;


/**
 * Created by Administrator on 2016/7/28.
 */
public class RechargeActivity extends BaseActivity implements RechargeContract.View {
    private final static int REQUEST_FOR_RECHARGEPAY = 1000;
    private RadioButton radioButton1,radioButton2,radioButton3;
    private EditText amountET;
    private TextView submitTV;
    private TextView balanceTextView;
    private RechargeContract.Presenter mPresenter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        mPresenter = new RechargePresenter(this,this);
        setTitle("账户充值");
        setContentView(R.layout.activity_recharge);
        initView();

        if(getIntent().hasExtra("balance")){
            balanceTextView.setText(getIntent().getStringExtra("balance")+"元");
        }
    }

    @Override
    protected void onBack() {
        finish();
    }

    private void initView(){
        radioButton1 = (RadioButton) findViewById(R.id.recharge_radio1);
        radioButton2 = (RadioButton) findViewById(R.id.recharge_radio2);
        radioButton3 = (RadioButton) findViewById(R.id.recharge_radio3);
        amountET = (EditText) findViewById(R.id.recharge_amount);
        submitTV = (TextView) findViewById(R.id.recharge_submit);
        balanceTextView = (TextView) findViewById(R.id.recharge_balance);
        radioButton2.setChecked(true);


        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submitOrder(amountET.getText().toString());
            }
        });
    }

    private int getCheck(){
        if(radioButton1.isChecked())return 1;
        else if(radioButton2.isChecked())return 2;
        else if(radioButton3.isChecked())return 3;
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_FOR_RECHARGEPAY && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.hide();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doTokenOut() {
        tokenOut();
    }

    @Override
    public void submitSuccess(String paymentSn) {
        Intent intent = new Intent(getApplicationContext(),RechargePayActivity.class);
        intent.putExtra("paymentSn",paymentSn);
        intent.putExtra("check",getCheck());
        startActivityForResult(intent,REQUEST_FOR_RECHARGEPAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        submitTV.setOnClickListener(null);
    }
}
