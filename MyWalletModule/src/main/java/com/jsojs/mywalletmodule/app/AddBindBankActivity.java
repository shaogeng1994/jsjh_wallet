package com.jsojs.mywalletmodule.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jsojs.baselibrary.util.MyToast;
import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.Bank;
import com.jsojs.mywalletmodule.bean.BankList;
import com.jsojs.mywalletmodule.contract.AddBindBankContract;
import com.jsojs.mywalletmodule.presenter.AddBindBankPresenter;
import com.jsojs.mywalletmodule.util.ActivityStack;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/7/27.
 * update volley
 */
public class AddBindBankActivity extends BaseActivity implements AddBindBankContract.View{
    private static int WAIT_TIME = 60;
    private String custId,custName;
    private ArrayList<Bank> mBanks;
    private BottomSheetLayout bottomSheetLayout;
    private String bankCode;
    private EditText nameET,idCardET,bankcardET,phoneET,codeET;
    private TextView bankTV,getCodeTV,submitTV;
    private ImageView nameClearIV,idCardClearIV,cardClearIV;
    private int second = WAIT_TIME;
    private Timer timer;
    private final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(second == WAIT_TIME){
                        getCodeTV.setBackgroundResource(R.drawable.shape_corner_btn_disable);
                    }
                    getCodeTV.setText(second+"");
                    second--;
                    if(second == -1){
                        getCodeTV.setBackgroundResource(R.drawable.shape_corner_btn_red);
                        getCodeTV.setText("获取验证码");
                        second = WAIT_TIME;
                        timer.cancel();
                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };
    private TimerTask task;
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
    private View bottomView;
    private ListView bottomListView;
    private AddBindBankPresenter mAddBindBankPresenter;
    private Map<String,Integer> map = new HashMap<>();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        mAddBindBankPresenter = new AddBindBankPresenter(this,this);
        setTitle("绑定银行卡");
        setContentView(R.layout.activity_add_bindbank);
        initView();
        mAddBindBankPresenter.getBankList();
        mAddBindBankPresenter.getBankImg();
    }

    @Override
    protected void onBack() {
        finish();
    }

    private void initView(){
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.add_bindband_bottomsheet);
        nameET = (EditText) findViewById(R.id.add_bindband_name);
        idCardET = (EditText) findViewById(R.id.add_bindband_idcard);
        bankcardET = (EditText) findViewById(R.id.add_bindband_bankcard);
        phoneET = (EditText) findViewById(R.id.add_bindband_phone);
        bankTV = (TextView) findViewById(R.id.add_bindband_bankname);
        codeET = (EditText) findViewById(R.id.add_bindband_code);
        getCodeTV = (TextView) findViewById(R.id.add_bindband_getcode);
        submitTV = (TextView) findViewById(R.id.add_bindband_next);
        nameClearIV = (ImageView) findViewById(R.id.add_bindband_name_clear);
        idCardClearIV = (ImageView) findViewById(R.id.add_bindband_idcard_clear);
        cardClearIV = (ImageView) findViewById(R.id.add_bindband_bankcard_clear);
        bottomView = LayoutInflater.from(this).inflate(R.layout.bank_bottomsheet_layout,bottomSheetLayout,false);
        bottomListView = (ListView) bottomView.findViewById(R.id.bank_bottomsheet_listview);


        bankTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetLayout.showWithSheetView(bottomView);
            }
        });
        getCodeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddBindBankPresenter.getCode(nameET.getText().toString(),idCardET.getText().toString(),
                        bankcardET.getText().toString(),bankCode,bankTV.getText().toString(),
                        phoneET.getText().toString());
            }
        });

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddBindBankPresenter.addBank(nameET.getText().toString(),idCardET.getText().toString(),
                        bankcardET.getText().toString(),bankCode,bankTV.getText().toString(),
                        phoneET.getText().toString(),codeET.getText().toString());
            }
        });

        nameClearIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameET.setText("");
            }
        });
        idCardClearIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idCardET.setText("");
            }
        });
        cardClearIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankcardET.setText("");
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
        timer.schedule(task,0, 1000);
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
    public void showBankList(BankList bankList) {
        nameET.setText(bankList.getCustName());
        idCardET.setText(bankList.getCustId());
        final ListViewAdapter listViewAdapter = new ListViewAdapter(this,bankList.getBankList());
        bottomListView.setAdapter(listViewAdapter);
        bottomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bank bank = (Bank) listViewAdapter.getItem(position);
                bankTV.setText(bank.getName());
                bankCode = bank.getCode();
                bottomSheetLayout.dismissSheet();
            }
        });
    }

    @Override
    public void doTokenOut() {
        tokenOut();
    }

    @Override
    public void sendSuccess() {
        setTimer();
    }

    @Override
    public void addBankSuccess() {
        handler.removeMessages(1);
        setContentView(R.layout.activity_add_bindbank_success);
        setTitle("绑定成功");
        setOption("完成");
        setResult(RESULT_OK);
        setOnClickOptionListener(new OnClickOptionListener() {
            @Override
            public void onClickOption() {
                finish();
            }
        });
    }

    @Override
    public void getBankImgSuccess(Map banks) {
        this.map = banks;
    }

    private class ListViewAdapter extends BaseAdapter{
        private Context context;
        private List<Bank> banks;
        public ListViewAdapter(Context context,List<Bank> banks){
            this.context = context;
            this.banks = banks;
        }

        @Override
        public int getCount() {
            if (banks==null)
            return 0;
            return banks.size();
        }

        @Override
        public Object getItem(int position) {
            return banks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.bank_bottomsheet_item,null);
//            TextView textView = (TextView) convertView.findViewById(R.id.bank_bottomsheet_itemname);
//            NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.bank_bottomsheet_itemimg);
//            textView.setText(banks.get(position).getName());
//            networkImageView.setDefaultImageResId(R.color.colorGray);
//            if(!banks.get(position).getBankUrl().equals("")||banks.get(position).getBankUrl()!=null){
//                networkImageView.setImageUrl(banks.get(position).getBankUrl(),MVolley.getInstance(context).getImageLoader());
//            }
            Holder holder;
            if(convertView==null) {
                holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(R.layout.bind_bank_img_item,null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.bind_bank_img_item_img);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }
            holder.imageView.setBackgroundResource(map.get(banks.get(position).getName()));
            return convertView;
        }

        private class Holder {
            ImageView imageView;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bankTV.setOnClickListener(null);
        getCodeTV.setOnClickListener(null);
        submitTV.setOnClickListener(null);
        if(bottomListView!=null)
            bottomListView.setOnItemClickListener(null);
        handler.removeCallbacksAndMessages(null);
    }
}
