package com.jsojs.mywalletmodule.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import com.jsojs.baselibrary.util.MyToast;
import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Administrator on 2016/7/28.
 */
public class RechargeActivity extends BaseActivity {
    private RadioButton radioButton1,radioButton2,radioButton3;
    private EditText amountET;
    private TextView submitTV;
    private String orderId;
    private TextView balanceTextView;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
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
        radioButton1.setChecked(true);


        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amountET.getText().toString().equals("")){
                    subPay();
                }else{
                    MyToast.makeToast(getApplicationContext(),"填写金额");
                }
            }
        });
    }

    private void subPay(){
//        String url = APIUrl.URL + "app/rechargeAdd";
        HashMap<String,String> map = new HashMap<>();
        map.put("token", MyToken.getMyToken(this));
        map.put("action","rechargeAdd");
        map.put("amount",amountET.getText().toString());
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        orderId = jsonObject1.getString("order_id");
                        Intent intent = new Intent(getApplicationContext(),RechargePayActivity.class);
                        intent.putExtra("id",orderId);
                        intent.putExtra("check",getCheck());
                        startActivityForResult(intent,1000);
                    }else MyJson.getMsg(getApplicationContext(),jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
        if(requestCode==1000&&resultCode==1001){
            setResult(1001);
            finish();
        }
    }

}
