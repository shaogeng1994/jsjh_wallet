package com.jsojs.mywalletmodule.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.BindBank;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/27.
 */
public class MyBindBankActivity extends BaseActivity {
    private ArrayList<BindBank> mBindBank;
    private ListView listView;
    private LinearLayout addLayout;
    private ListViewAdapter listViewAdapter;
    private Map<String,Integer> map = new HashMap<>();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        pushBankImg();
        setTitle("银行卡");
        setOption("");
        setContentView(R.layout.activity_my_bindbank);
        initView();
        getBindBank();
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddBindBankActivity.class);
                startActivityForResult(intent,1000);
            }
        });
    }



    @Override
    protected void onBack() {
        finish();
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.my_bindbank_listview);
        addLayout = (LinearLayout) findViewById(R.id.my_bindbank_addlayout);

    }


    private void getBindBank(){
//        String url = APIUrl.URL + "app/queryBindBankList";
        HashMap<String,String> map = new HashMap<>();
        map.put("token", MyToken.getMyToken(this));
        map.put("action","queryBindBankList");
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                Log.i("shao","bank-->"+jsonObject.toString());
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
                            bindBank.setId(jsonObject2.getString("id"));
                            bindBanks.add(bindBank);
                        }
                        mBindBank = bindBanks;
                        listView.setVisibility(View.VISIBLE);
                        listViewAdapter = new ListViewAdapter(MyBindBankActivity.this,mBindBank);
                        listView.setAdapter(listViewAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(MyBindBankActivity.this,BankCardInfoActivity.class);
                                BindBank bindBank = (BindBank) listViewAdapter.getItem(position);
                                intent.putExtra("position",position);
                                intent.putExtra("bank",bindBank);
                                startActivityForResult(intent,2000);
                            }
                        });

                    }else {
                        listView.setVisibility(View.INVISIBLE);
                        MyJson.getMsg(getApplicationContext(),jsonObject);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class ListViewAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<BindBank> bindBanks;
        public ListViewAdapter(Context context,ArrayList<BindBank> bindBanks){
            this.context =context;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bindbank_listview_item,null);
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.bindbank_item_layout);
            final TextView bankCardTV = (TextView) convertView.findViewById(R.id.bindbank_item_bankcard);
            TextView bankNameTV = (TextView) convertView.findViewById(R.id.bindbank_item_bankname);
            final TextView delete = (TextView) convertView.findViewById(R.id.bindbank_item_delete);
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.bindbank_item_layout);
            bankCardTV.setText(bindBanks.get(position).getBankcard());
            bankNameTV.setText(bindBanks.get(position).getBankname());
            if(map.get(bindBanks.get(position).getBankname())!=null)
            linearLayout.setBackgroundResource(map.get(bindBanks.get(position).getBankname()));
//            layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(delete.getVisibility()==View.GONE){
//                        delete.setVisibility(View.VISIBLE);
//                    }else{
//                        delete.setVisibility(View.GONE);
//                    }
//                }
//            });
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MyBindBankActivity.this);
//                    builder.setTitle("提示").setMessage("是否确认解绑？");
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            removeBind(position);
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.show();
//                }
//            });
            return convertView;
        }

        public void removeData(int position){
            bindBanks.remove(position);
            notifyDataSetChanged();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000&&resultCode==1001){
            getBindBank();
        }else if(requestCode==2000&&resultCode==RESULT_OK){
            int position = data.getIntExtra("position",-1);
            if(position!=-1){
                listViewAdapter.removeData(position);
            }else{
                getBindBank();
            }
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

}
