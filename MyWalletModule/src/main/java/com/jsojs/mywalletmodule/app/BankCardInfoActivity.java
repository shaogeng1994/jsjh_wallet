package com.jsojs.mywalletmodule.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.BindBank;
import com.jsojs.mywalletmodule.contract.BankCardInfoContract;
import com.jsojs.mywalletmodule.presenter.BankCardInfoPresenter;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by root on 16-10-20.
 */

public class BankCardInfoActivity extends BaseActivity implements BankCardInfoContract.View {
    private BindBank bindBank;
    private TextView bankNameTextView,bankCardTextView;
    private Button submitButton;
    private LinearLayout linearLayout;
    private Map<String,Integer> map = new HashMap<>();
    private BankCardInfoContract.Presenter mPresenter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        mPresenter = new BankCardInfoPresenter(this,this);
        mPresenter.getBankImg();
        if(getIntent().hasExtra("bank")){
            bindBank = (BindBank) getIntent().getSerializableExtra("bank");
        }else{
            finish();
        }
        setTitle(bindBank.getBankname());
        setContentView(R.layout.activity_bankcard_info);
        initView();
    }

    private void initView(){
        bankNameTextView = (TextView) findViewById(R.id.bindbank_item_bankname);
        bankCardTextView = (TextView) findViewById(R.id.bindbank_item_bankcard);
        submitButton = (Button) findViewById(R.id.bankcard_info_submit);
        linearLayout = (LinearLayout) findViewById(R.id.bindbank_item_layout);

        bankNameTextView.setText(bindBank.getBankname());
        bankCardTextView.setText(bindBank.getBankcard());
        if(map.get(bindBank.getBankname())!=null)
            linearLayout.setBackgroundResource(map.get(bindBank.getBankname()));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BankCardInfoActivity.this);
                builder.setTitle("提示").setMessage("是否确认解绑？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.removeBank(bindBank.getId());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
    }


    private void removeSuccess() {
        setContentView(R.layout.activity_add_bindbank_success);
        setTitle("解绑完成");
        TextView textView = (TextView) findViewById(R.id.bank_msg);
        textView.setText("解绑成功");
        Intent intent = new Intent();
        intent.putExtra("position",getIntent().getIntExtra("position",-1));
        setResult(RESULT_OK,intent);
        setOption("完成");
        setOnClickOptionListener(new OnClickOptionListener() {
            @Override
            public void onClickOption() {
                finish();
            }
        });
    }



    @Override
    protected void onBack() {
        finish();
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
    public void removeBankSuccess() {
        removeSuccess();
    }

    @Override
    public void doTokenOut() {
        tokenOut();
    }

    @Override
    public void bankImg(Map<String, Integer> map) {
        this.map = map;
    }
}
