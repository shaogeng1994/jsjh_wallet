package com.jsojs.mywalletmodule.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.util.ActivityStack;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/27.
 */
public class MyWalletActivity extends BaseActivity {
    private ActionBar actionBar;
    private TextView money1TV,money2TV,money3TV;
    private RelativeLayout outLayout,bankLayout,rechargeLayout;

    @Override
    public int setActionBarStyle() {
        return WHITE_ACTION_BAR;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("我的钱包");
        setContentView(R.layout.activity_my_wallet2);
        initView();
        getMoney();
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
                startActivityForResult(intent,1000);
            }
        });
        outLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WithdrawActivity.class);
                intent.putExtra("balance",money1TV.getText().toString());
                startActivityForResult(intent,2000);
            }
        });
    }

    private void getMoney(){
//        String url = APIUrl.URL + "app/queryWallet";
        HashMap<String,String> map = new HashMap<>();
        map.put("token", MyToken.getMyToken(this));
        map.put("action","queryWallet");
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        money1TV.setText(jsonObject1.getString("TotalBalance"));
                        money2TV.setText("￥"+jsonObject1.getString("TotalFreezeAmount"));
                        money3TV.setText("￥"+jsonObject1.getString("TotalAmount"));
                    }else {
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
        if(requestCode ==2000&&resultCode==2001){
            getMoney();
        }
        if(requestCode == 1000&&resultCode==1001){
            getMoney();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getScreenManager().clearAllActivity();
    }
}
