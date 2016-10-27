package com.jsojs.mywalletmodule.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.WalletMsg;
import com.jsojs.mywalletmodule.contract.WalletContract;
import com.jsojs.mywalletmodule.presenter.WalletPresenter;
import com.jsojs.mywalletmodule.util.ActivityStack;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/27.
 */
public class MyWalletActivity extends BaseActivity implements WalletContract.View{
    public final static int REQUEST_WITHDRAW = 2000;
    public final static int REQUEST_RECHARGE = 1000;
    private ActionBar actionBar;
    private TextView money1TV,money2TV,money3TV;
    private RelativeLayout outLayout,bankLayout,rechargeLayout;
    private WalletContract.Presenter mPresenter;

    @Override
    public int setActionBarStyle() {
        return WHITE_ACTION_BAR;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        mPresenter = new WalletPresenter(this,this);
        setTitle("我的钱包");
        setContentView(R.layout.activity_my_wallet2);
        initView();
        mPresenter.getWalletMsg();
    }

    @Override
    protected void onBack() {
        finish();
    }

    private void initView(){
        money1TV = (TextView) findViewById(R.id.my_wallet_money1);
        money2TV = (TextView) findViewById(R.id.my_wallet_money2);
        money3TV = (TextView) findViewById(R.id.my_wallet_money3);
        outLayout = (RelativeLayout) findViewById(R.id.my_wallet_outmoney);
        bankLayout = (RelativeLayout) findViewById(R.id.my_wallet_bindbank);
        rechargeLayout = (RelativeLayout) findViewById(R.id.my_wallet_recharge);


        bankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MyBindBankActivity.class);
                startActivity(intent);
            }
        });
        rechargeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RechargeActivity.class);
                intent.putExtra("balance",money1TV.getText().toString());
                startActivityForResult(intent,REQUEST_RECHARGE);
            }
        });
        outLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WithdrawActivity.class);
                intent.putExtra("balance",money1TV.getText().toString());
                startActivityForResult(intent,REQUEST_WITHDRAW);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_WITHDRAW && resultCode == 2001){
            mPresenter.getWalletMsg();
        }
        if(requestCode == REQUEST_RECHARGE && resultCode == RESULT_OK){
            mPresenter.getWalletMsg();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getScreenManager().clearAllActivity();
        mPresenter = null;
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
    public void showWalletMsg(WalletMsg walletMsg) {
        money1TV.setText(walletMsg.getTotalBalance());
        money2TV.setText("￥"+walletMsg.getTotalFreezeAmount());
        money3TV.setText("￥"+walletMsg.getTotalAmount());
    }

    @Override
    public void doTokenOut() {
        tokenOut();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
