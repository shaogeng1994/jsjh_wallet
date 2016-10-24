package com.jsojs.mywalletmodule.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsojs.baselibrary.util.MyToast;
import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.BindBank;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/7/29.
 */
public class WithdrawActivity extends BaseActivity {
    private ArrayList<BindBank> mBindBanks;
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
                    }
                    getcodeTV.setText(second+"");
                    second--;
                    if(second == -1){
                        getcodeTV.setBackgroundResource(R.drawable.shape_corner_btn_red);
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

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("提现");
        setContentView(R.layout.activity_withdraw);
        initView();
        getBindBank();

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

        getcodeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amountET.getText().toString().equals("")){
                    MyToast.makeToast(getApplicationContext(),"请输入金额");
                }else if(bankNameTV.getText().toString().equals("")){
                    MyToast.makeToast(getApplicationContext(),"请选择银行卡");
                }else {
                    getCode();
                }
            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serialNo==null){
                    MyToast.makeToast(getApplicationContext(),"请先获取验证码");
                }else if(codeET.getText().toString().equals("")){
                    MyToast.makeToast(getApplicationContext(),"请输入验证码");
                }else{
                    if(amountET.getText().toString().equals(amount)){
                        submit();
                    }else {
                        Toast.makeText(getApplicationContext(),"修改金额后，请重新获取验证码",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        bankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WithdrawActivity.this,SelectBindBankActivity.class);
                intent.putExtra("banks",(Serializable)mBindBanks);
                startActivityForResult(intent,1000);
            }
        });
    }

    private void getBindBank(){
//        String url = APIUrl.URL + "app/queryBindBankList";
        HashMap<String,String> map = new HashMap<>();
        map.put("token", MyToken.getMyToken(this));
        map.put("action","queryBindBankList");
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = jsonObject1.getJSONArray("bank");
                        ArrayList<BindBank> bindBanks = new ArrayList<BindBank>();
                        for(int i=0;i<jsonArray.length();i++){
                            BindBank bindBank = new BindBank();
                            JSONObject  jsonObject2 = jsonArray.getJSONObject(i);
                            bindBank.setBankname(jsonObject2.getString("bankname"));
                            bindBank.setBankcard(jsonObject2.getString("bankcard"));
                            bindBank.setMobile(jsonObject2.getString("mobile"));
                            bindBank.setId(jsonObject2.getString("id"));
                            bindBanks.add(bindBank);
                        }
                        mBindBanks = bindBanks;
                        bankNameTV.setText(mBindBanks.get(position).getBankname());
                        String card = mBindBanks.get(position).getBankcard();
                        String cardEnd = "尾号"+card.substring(card.length()-4,card.length());
                        bankCardTV.setText(cardEnd);
                        mobileTV.setText(mBindBanks.get(position).getMobile());

                    }else if(code.equals("2")){
                        MyToast.makeToast(getApplicationContext(),jsonObject.getString("msg"));
                        Intent intent = new Intent(WithdrawActivity.this,AddBindBankActivity.class);
                        startActivityForResult(intent,2000);
                    }else {
                        MyJson.getMsg(getApplicationContext(),jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        timer.schedule(task,1000, 1000);
    }

    private void getCode(){
//        String url = APIUrl.URL + "app/withdrawalsCode";
        HashMap<String,String> map = new HashMap<>();
        map.put("token",MyToken.getMyToken(this));
        map.put("action","withdrawalsCode");
        map.put("cardId",mBindBanks.get(position).getId());
        map.put("amount",amountET.getText().toString());
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        MyToast.makeToast(getApplicationContext(),"发送成功");
                        amount = amountET.getText().toString();
                        serialNo = jsonObject.getJSONObject("data").getString("serialNo");
                        setTimer();

                    }else{
                        MyJson.getMsg(getApplicationContext(),jsonObject);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void submit(){
//        String url = APIUrl.URL + "app/withdrawals";
        HashMap<String,String> map = new HashMap<>();
        map.put("token",MyToken.getMyToken(this));
        map.put("action","withdrawals");
        map.put("cardId",mBindBanks.get(position).getId());
        map.put("serialNo",serialNo);
        map.put("smsCode",codeET.getText().toString());
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        withdrawSuccess();
                    }else{
                        MyJson.getMsg(getApplicationContext(),jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000&&resultCode==1001){
            position = data.getIntExtra("position",0);
            getBindBank();
            serialNo = null;
        }else if(requestCode==2000&&resultCode==1001){
            getBindBank();
        }
    }

    private void withdrawSuccess(){
        setResult(2001);
        Intent intent = new Intent(this,WithdrawSuccessActivity.class);
        intent.putExtra("name",bankNameTV.getText().toString());
        intent.putExtra("end",bankCardTV.getText().toString());
        intent.putExtra("amount",amountET.getText().toString());
        startActivity(intent);
        finish();
    }

}
