package com.jsojs.mywalletmodule.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.BindBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29.
 */
public class SelectBindBankActivity extends BaseActivity {
    private ListView listView;
    private ArrayList<BindBank> mBindBanks;
    private LinearLayout addLayout;
    private Map<String,Integer> map = new HashMap<>();
    private ListViewAdapter listViewAdapter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        pushBankImg();
        setTitle("银行卡");
        mBindBanks = (ArrayList<BindBank>) getIntent().getSerializableExtra("banks");
        setContentView(R.layout.activity_my_bindbank);
        initView();
    }

    @Override
    protected void onBack() {
        finish();
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.my_bindbank_listview);
        addLayout = (LinearLayout) findViewById(R.id.my_bindbank_addlayout);

        addLayout.setVisibility(View.GONE);


        listViewAdapter = new ListViewAdapter(this,mBindBanks);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private class ListViewAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<BindBank> bindBanks;
        public ListViewAdapter(Context context,ArrayList<BindBank> bindBanks){
            this.context =context.getApplicationContext();
            this.bindBanks = bindBanks;
        }

        @Override
        public int getCount() {
            if (bindBanks==null)
                return 0;
            return bindBanks.size();
        }

        @Override
        public Object getItem(int position) {
            return bindBanks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bindbank_listview_item,null);
            TextView bankCardTV = (TextView) convertView.findViewById(R.id.bindbank_item_bankcard);
            TextView bankNameTV = (TextView) convertView.findViewById(R.id.bindbank_item_bankname);
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.bindbank_item_layout);
            bankCardTV.setText(bindBanks.get(position).getBankcard());
            bankNameTV.setText(bindBanks.get(position).getBankname());
            bankCardTV.setText(bindBanks.get(position).getBankcard());
            bankNameTV.setText(bindBanks.get(position).getBankname());
            if(map.get(bindBanks.get(position).getBankname())!=null)
                linearLayout.setBackgroundResource(map.get(bindBanks.get(position).getBankname()));
            return convertView;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if(listView!=null)
            listView.setOnItemClickListener(null);
    }
}
