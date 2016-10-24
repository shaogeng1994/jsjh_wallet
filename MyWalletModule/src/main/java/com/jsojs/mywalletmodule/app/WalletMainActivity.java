package com.jsojs.mywalletmodule.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jsojs.mywalletmodule.R;

public class WalletMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button = (Button) findViewById(R.id.my_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("tokenError");
                intent.putExtra("isTokenOut",true);
                sendBroadcast(intent);
            }
        });
    }
}
