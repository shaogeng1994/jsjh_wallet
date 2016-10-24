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

import com.android.volley.toolbox.NetworkImageView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jsojs.baselibrary.util.MyToast;
import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.Bank;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/7/27.
 * update volley
 */
public class AddBindBankActivity extends BaseActivity {
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

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("绑定银行卡");
        setContentView(R.layout.activity_add_bindbank);
        initView();
        getBankList();
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


        bankTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        getCodeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameET.getText().toString().equals("")||phoneET.getText().toString().equals("")||idCardET.getText().toString().equals("")||
                        bankCode==null||bankcardET.getText().toString().equals("")){
                    MyToast.makeToast(getApplicationContext(),"请填写完整");
                }else{
                    getCode();
                }
            }
        });

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codeET.getText().toString().equals("")){
                    MyToast.makeToast(getApplicationContext(),"输入验证码");
                }else if(!idCardET.getText().toString().matches(REGEX_ID_CARD)){
                    MyToast.makeToast(getApplicationContext(),"请正确输入身份证号码");
                }else{
                    addBindBank();
                }
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

    private void showBottomSheet(){
        View view = LayoutInflater.from(this).inflate(R.layout.bank_bottomsheet_layout,bottomSheetLayout,false);
        ListView listView = (ListView) view.findViewById(R.id.bank_bottomsheet_listview);
        bottomSheetLayout.showWithSheetView(view);
        final ListViewAdapter listViewAdapter = new ListViewAdapter(this,mBanks);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bank bank = (Bank) listViewAdapter.getItem(position);
                bankTV.setText(bank.getBankName());
                bankCode = bank.getBankCode();
                bottomSheetLayout.dismissSheet();
            }
        });
    }


    private void getBankList(){
//        String url = APIUrl.URL + "app/queryBankList";
        HashMap<String,String> map = new HashMap<>();
        map.put("token", MyToken.getMyToken(this));
        map.put("action","queryBankList");
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    ArrayList<Bank> banks = new ArrayList<Bank>();
                    if(code.equals("1")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String bankurl = MyJson.getString(jsonObject1,"bank_baseUrl");
                        if(jsonObject1.get("custId")!=null){
                            custId = MyJson.getString(jsonObject1,"custId");
                            idCardET.setText(custId);
                        }
                        if(jsonObject1.get("custName")!=null){
                            custName = MyJson.getString(jsonObject1,"custName");
                            nameET.setText(custName);
                        }
                        JSONArray jsonArray = jsonObject1.getJSONArray("bankList");
                        for(int i=0;i<jsonArray.length();i++){
                            Bank bank = new Bank();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            bank.setBankCode(MyJson.getString(jsonObject2,"code"));
                            bank.setCode(MyJson.getString(jsonObject2,"bankcode"));
                            bank.setBankName(MyJson.getString(jsonObject2,"name"));
                            bank.setBankUrl(bankurl+bank.getCode()+".jpg");
                            banks.add(bank);
                        }
                    }

                    mBanks = banks;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCode(){
//        String url = APIUrl.URL + "app/bindBankCode";
        HashMap<String,String> map = new HashMap<>();
        map.put("token",MyToken.getMyToken(this));
        map.put("custName",nameET.getText().toString());
        map.put("custId",idCardET.getText().toString());
        map.put("bankCard",bankcardET.getText().toString());
        map.put("bankCode",bankCode);
        map.put("bankName",bankTV.getText().toString());
        map.put("mobile",phoneET.getText().toString());
        map.put("action","bindBankCode");
        MVolley.GetResponseLintener lintener = new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        MyToast.makeToast(getApplicationContext(),"发送成功");
                        setTimer();
                    }else{
                        MyJson.getMsg(getApplicationContext(),jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, lintener);
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

    private void addBindBank(){
//        String url = APIUrl.URL + "app/addBingBank";
        HashMap<String,String> map = new HashMap<>();
        map.put("token",MyToken.getMyToken(this));
        map.put("custName",nameET.getText().toString());
        map.put("custId",idCardET.getText().toString());
        map.put("bankCard",bankcardET.getText().toString());
        map.put("bankCode",bankCode);
        map.put("bankName",bankTV.getText().toString());
        map.put("mobile",phoneET.getText().toString());
        map.put("smsCode",codeET.getText().toString());
        map.put("action","addBingBank");
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        MyToast.makeToast(getApplicationContext(),"添加成功");
//                        parseJSON(jsonObject);
//                        setResult(1001);
//                        finish();
                        addSuccess();
                    }else{
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
        private ArrayList<Bank> banks;
        public ListViewAdapter(Context context,ArrayList<Bank> banks){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.bank_bottomsheet_item,null);
            TextView textView = (TextView) convertView.findViewById(R.id.bank_bottomsheet_itemname);
            NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.bank_bottomsheet_itemimg);
            textView.setText(banks.get(position).getBankName());
            networkImageView.setDefaultImageResId(R.color.colorGray);
            if(!banks.get(position).getBankUrl().equals("")||banks.get(position).getBankUrl()!=null){
                networkImageView.setImageUrl(banks.get(position).getBankUrl(),MVolley.getInstance(context).getImageLoader());
            }
            return convertView;
        }
    }

    private void addSuccess(){
        handler.removeMessages(1);
        setContentView(R.layout.activity_add_bindbank_success);
        setTitle("绑定成功");
        setOption("完成");
        setResult(1001);
        setOnClickOptionListener(new OnClickOptionListener() {
            @Override
            public void onClickOption() {
                finish();
            }
        });
    }
}
