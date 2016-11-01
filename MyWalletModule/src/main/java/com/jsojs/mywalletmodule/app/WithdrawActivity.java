package com.jsojs.mywalletmodule.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.BindBank;
import com.jsojs.mywalletmodule.contract.WithdrawContract;
import com.jsojs.mywalletmodule.presenter.WithdrawPresenter;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/7/29.
 */
public class WithdrawActivity extends BaseActivity implements WithdrawContract.View {
    public final static int REQUEST_ADDBANK = 2000;
    public final static int REQUEST_SELECTBANK = 1000;
    private List<BindBank> mBindBanks;
    private TextView bankNameTV,bankCardTV,getcodeTV,submitTV,mobileTV;
    private EditText amountET,codeET;
    private RelativeLayout bankLayout;
    private static int WAIT_TIME = 60;
    private int second = WAIT_TIME;
    private Timer timer;
    private final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(second == WAIT_TIME){
                        getcodeTV.setBackgroundResource(R.drawable.shape_corner_btn_disable);
                        getcodeTV.setClickable(false);
                    }
                    getcodeTV.setText(second+"");
                    second--;
                    if(second == -1){
                        getcodeTV.setBackgroundResource(R.drawable.shape_corner_btn_red);
                        getcodeTV.setClickable(true);
                        getcodeTV.setText("获取验证码");
                        second = WAIT_TIME;
                        timer.cancel();
                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };
    private TimerTask task;
    private String serialNo;
    private int position = 0;
    private TextView balanceTextView;
    private String amount = "";
    private WithdrawContract.Presenter mPresenter;
    private ImageView bankImageView;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        mPresenter = new WithdrawPresenter(this,this);
        setTitle("提现");
        setContentView(R.layout.activity_withdraw);
        initView();
        mPresenter.getBindBank();

        if(getIntent().hasExtra("balance")){
            balanceTextView.setText(getIntent().getStringExtra("balance")+"元");
        }
    }

    @Override
    protected void onBack() {
        finish();
    }

    private void initView(){
        bankNameTV = (TextView) findViewById(R.id.withdraw_name);
        bankCardTV = (TextView) findViewById(R.id.withdraw_card);
        getcodeTV = (TextView) findViewById(R.id.withdraw_getcode);
        submitTV = (TextView) findViewById(R.id.withdraw_submit);
        amountET = (EditText) findViewById(R.id.withdraw_amount);
        codeET = (EditText) findViewById(R.id.withdraw_code);
        bankLayout = (RelativeLayout) findViewById(R.id.withdraw_layout);
        mobileTV = (TextView) findViewById(R.id.withdraw_mobile);
        balanceTextView = (TextView) findViewById(R.id.withdraw_balance);
        bankImageView = (ImageView) findViewById(R.id.withdraw_bankimg);

        getcodeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCode(mBindBanks.get(position).getId(),amountET.getText().toString());
            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amountET.getText().toString().equals(amount)){
                    mPresenter.withdraw(mBindBanks.get(position).getId(),serialNo,codeET.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),"修改金额后，请重新获取验证码",Toast.LENGTH_SHORT).show();
                }

            }
        });
        bankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WithdrawActivity.this,SelectBindBankActivity.class);
                intent.putExtra("banks",(Serializable)mBindBanks);
                startActivityForResult(intent,REQUEST_SELECTBANK);
            }
        });
    }

    private void setTimer(){
        task = new TimerTask(){
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer = new Timer();
        timer.schedule(task,0, 1000);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SELECTBANK &&resultCode == RESULT_OK){
            position = data.getIntExtra("position",0);
            mPresenter.getBindBank();
            serialNo = null;
        }else if(requestCode == REQUEST_ADDBANK && resultCode == RESULT_OK){
            mPresenter.getBindBank();
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
    public void getBankSuccess(List<BindBank> bindBanks) {
        this.mBindBanks = bindBanks;
        bankNameTV.setText(bindBanks.get(position).getBankname());
        String card = bindBanks.get(position).getBankcard();
        String cardEnd = "尾号"+card.substring(card.length()-4,card.length());
        bankCardTV.setText(cardEnd);
        mobileTV.setText(bindBanks.get(position).getMobile());
        mPresenter.getBankImg(bindBanks.get(position).getBankname());
    }

    @Override
    public void toAddBindBank() {
        showToast("没有绑定银行卡，请先绑定");
        Intent intent = new Intent(WithdrawActivity.this,AddBindBankActivity.class);
        startActivityForResult(intent,REQUEST_ADDBANK);
    }

    @Override
    public void getCodeSuccess(String serialNo) {
        this.amount = amountET.getText().toString();
        this.serialNo = serialNo;
        setTimer();
    }

    @Override
    public void withdrawSuccess() {
        setResult(2001);
        Intent intent = new Intent(this,WithdrawSuccessActivity.class);
        intent.putExtra("name",bankNameTV.getText().toString());
        intent.putExtra("end",bankCardTV.getText().toString());
        intent.putExtra("amount",amountET.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void getBankFailure() {
        finish();
    }

    @Override
    public void getBankImgSuccess(int bankImg) {
        bankImageView.setBackgroundResource(bankImg);
    }
}
