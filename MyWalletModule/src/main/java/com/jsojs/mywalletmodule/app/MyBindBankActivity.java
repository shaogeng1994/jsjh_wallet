package com.jsojs.mywalletmodule.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.BindBank;
import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.contract.MyBindBankContract;
import com.jsojs.mywalletmodule.presenter.MyBindBankPresenter;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/27.
 */
public class MyBindBankActivity extends BaseActivity implements MyBindBankContract.View{
    public final static int REQUEST_ADDBANK = 1000;
    public final static int REQUEST_INFO = 2000;
    private ListView listView;
    private LinearLayout addLayout;
    private ListViewAdapter listViewAdapter;
    private MyBindBankPresenter myBindBankPresenter;
    private Map<String,Integer> map;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        myBindBankPresenter = new MyBindBankPresenter(this,this);
        myBindBankPresenter.getBankImg();
        setTitle("银行卡");
        setOption("");
        setContentView(R.layout.activity_my_bindbank);
        initView();
        myBindBankPresenter.getBankList();
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddBindBankActivity.class);
                startActivityForResult(intent,REQUEST_ADDBANK);
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

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.hide();
    }

    @Override
    public void showBankList(BindBankList bindBankList) {
        listView.setVisibility(View.VISIBLE);
        listViewAdapter = new ListViewAdapter(MyBindBankActivity.this,bindBankList.getBank());
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyBindBankActivity.this,BankCardInfoActivity.class);
                BindBank bindBank = (BindBank) listViewAdapter.getItem(position);
                intent.putExtra("position",position);
                intent.putExtra("bank",bindBank);
                startActivityForResult(intent,REQUEST_INFO);
            }
        });
    }

    @Override
    public void doTokenOut() {
        tokenOut();
    }

    @Override
    public void bankImg(Map<String, Integer> map) {
        this.map = map;
    }

    private class ListViewAdapter extends BaseAdapter{
        private Context context;
        private List<BindBank> bindBanks;
        public ListViewAdapter(Context context,List<BindBank> bindBanks){
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
        if(requestCode == REQUEST_ADDBANK && resultCode == RESULT_OK){
            myBindBankPresenter.getBankList();
        }else if(requestCode == REQUEST_INFO && resultCode == RESULT_OK){
            int position = data.getIntExtra("position",-1);
            if(position!=-1){
                listViewAdapter.removeData(position);
            }else{
                myBindBankPresenter.getBankList();
            }
        }
    }



}
