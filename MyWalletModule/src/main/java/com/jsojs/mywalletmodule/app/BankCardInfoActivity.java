package com.jsojs.mywalletmodule.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jsojs.baselibrary.util.MyToast;
import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.BindBank;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by root on 16-10-20.
 */

public class BankCardInfoActivity extends BaseActivity {
    private BindBank bindBank;
    private TextView bankNameTextView,bankCardTextView,mobileTextView,getCodeTextView;
    private Button submitButton;
    private EditText codeEditView;
    private LinearLayout linearLayout;
    private Map<String,Integer> map = new HashMap<>();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        pushBankImg();
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
//        mobileTextView = (TextView) findViewById(R.id.bankcard_info_mobile);
//        getCodeTextView = (TextView) findViewById(R.id.bankcard_info_getcode);
        submitButton = (Button) findViewById(R.id.bankcard_info_submit);
//        codeEditView = (EditText) findViewById(R.id.bankcard_info_code);
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
                        removeBind();
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


    private void removeBind(){
//            String url = APIUrl.URL + "app/delBindBank";
        HashMap<String,String> map = new HashMap<>();
        map.put("token", MyToken.getMyToken(this));
        map.put("action","delBindBank");
        map.put("id",bindBank.getId());
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if (code.equals("1")){
                        MyToast.makeToast(getApplicationContext(),"解绑成功");
                        removeSuccess();
                    }else{
                        MyJson.getMsg(getApplicationContext(),jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void pushBankImg(){
        map.put("工商银行",R.mipmap.bank_icbc);
        map.put("农业银行",R.mipmap.bank_abc);
        map.put("中国银行",R.mipmap.bank_boc);
        map.put("建设银行",R.mipmap.bank_ccb);
        map.put("交通银行",R.mipmap.bank_bcm);
        map.put("招商银行",R.mipmap.bank_cmb);
        map.put("中信银行",R.mipmap.bank_citic);
        map.put("平安银行",R.mipmap.bank_pab);
        map.put("兴业银行",R.mipmap.bank_cib);
        map.put("浦发银行",R.mipmap.bank_psdb);
        map.put("光大银行",R.mipmap.bank_ceb);
        map.put("民生银行",R.mipmap.bank_cmbc);
        map.put("邮政储蓄银行",R.mipmap.bank_psb);
        map.put("北京银行",R.mipmap.bank_bob);
        map.put("上海银行",R.mipmap.bank_bos);
    }

    @Override
    protected void onBack() {
        finish();
    }
}
