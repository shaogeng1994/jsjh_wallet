package com.jsojs.mywalletmodule.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsojs.mywalletmodule.R;

/**
 * Created by root on 16-10-24.
 */

public class WithdrawSuccessActivity extends BaseActivity {
    private TextView typeTextView,bankNameTextView,cardEndTextView,amountNameTextView,amountTextView;
    private Button finishButton;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("提现完成");
        setContentView(R.layout.activity_recharge_success);
        initView();

        bankNameTextView.setText(getIntent().getStringExtra("name"));
        amountTextView.setText("￥"+getIntent().getStringExtra("amount"));
        cardEndTextView.setText(getIntent().getStringExtra("end"));
        typeTextView.setText("提现成功");
        amountNameTextView.setText("提现金额");
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView(){
        typeTextView = (TextView) findViewById(R.id.recharge_success_type);
        bankNameTextView = (TextView) findViewById(R.id.recharge_success_cardname);
        cardEndTextView = (TextView) findViewById(R.id.recharge_success_cardend);
        amountNameTextView = (TextView) findViewById(R.id.recharge_success_amount_name);
        amountTextView = (TextView) findViewById(R.id.recharge_success_amount);
        finishButton = (Button) findViewById(R.id.recharge_success_btn);
    }

    @Override
    protected void onBack() {
        finish();
    }
}
