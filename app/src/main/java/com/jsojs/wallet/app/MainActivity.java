package com.jsojs.wallet.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jsojs.mywalletmodule.*;
import com.jsojs.mywalletmodule.BuildConfig;
import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.app.MyWalletActivity;
import com.jsojs.mywalletmodule.bean.WalletConfig;
import com.jsojs.mywalletmodule.bean.WalletMsg;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.WalletManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button button,button2;
    private WalletApi mWalletApi;
    private String token = "9c13ebc96f357d54006cd5e3e5eec9e6770596";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWalletApi = WalletApiImpl.getInstance(this);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.test_btn);
        button2 = (Button) findViewById(R.id.test_btn2);


        final Map map = new HashMap();
        map.put("supplier_id","1159");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalletManager.getInstance().setWalletConfig(new WalletConfig("http://app.jiushangjiuhui.com:803/app/","1.0.5","2",map));
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences mSharedPreferences = getSharedPreferences("InfoSharedPreferences", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("token",token);
        mEditor.commit();
    }

    private void test(){
//        mWalletApi.queryWallet(token, new ResponseCallBack<WalletMsg>() {
//            @Override
//            public void callBack(ApiResponse<WalletMsg> response) {
//                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mWalletApi.delBindBank(token, "", new ResponseCallBack() {
//            @Override
//            public void callBack(ApiResponse response) {
//                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_SHORT).show();
//            }
//        });
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivity(intent);
    }
}
