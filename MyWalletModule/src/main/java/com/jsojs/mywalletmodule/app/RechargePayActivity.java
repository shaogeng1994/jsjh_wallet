package com.jsojs.mywalletmodule.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.jsojs.mywalletmodule.bean.QuickPay;
import com.jsojs.mywalletmodule.bean.QuickPayOrder;
import com.jsojs.mywalletmodule.util.MyJson;
import com.jsojs.mywalletmodule.util.MyToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/7/28.
 */
public class RechargePayActivity extends BaseActivity {
    private static int WAIT_TIME = 60;
    private int check;
    private TextView idTV,phoneTV,amountTV,getCodeTV,submitTV,balanceTV,bankNameTV;
    private EditText codeEdi;
    private QuickPayOrder quickPayOrder;
    private LinearLayout balanceLayout,codeLayout,bankLayout,phoneLayout;
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
    private BottomSheetLayout bottomSheetLayout;
    private LinearLayout selectBankLayout;
    private QuickPay mQuickPay;
    private ImageView bankImg;
    private TextView bankTV;
    private String BindId;
    private String bankCode;
    private String dateTime;
    private String orderId;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.wallet_activity_pay_online);
        check = getIntent().getIntExtra("check",0);
        orderId = getIntent().getStringExtra("id");
        initView();
        getPayOrder();
    }

    @Override
    protected void onBack() {
        finish();
    }

    private void initView() {
        idTV = (TextView) findViewById(R.id.pay_online_id);
        phoneTV = (TextView) findViewById(R.id.pay_online_phone);
        amountTV = (TextView) findViewById(R.id.pay_online_amount);
        getCodeTV = (TextView) findViewById(R.id.pay_online_getcode);
        submitTV = (TextView) findViewById(R.id.pay_online_submit);
        codeEdi = (EditText) findViewById(R.id.pay_online_code);
        balanceLayout = (LinearLayout) findViewById(R.id.pay_online_balancelayout);
        balanceTV = (TextView) findViewById(R.id.pay_online_balance);
        codeLayout = (LinearLayout) findViewById(R.id.pay_online_codelayout);
        bankLayout = (LinearLayout) findViewById(R.id.pay_online_bankpay);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.pay_online_bottomsheet);
        selectBankLayout = (LinearLayout) findViewById(R.id.pay_online_selectbank);
        bankNameTV = (TextView) findViewById(R.id.pay_online_bankname);
        bankImg = (ImageView) findViewById(R.id.pay_online_bankimg);
        bankTV = (TextView) findViewById(R.id.pay_online_banktv);
        phoneLayout = (LinearLayout) findViewById(R.id.pay_online_phonelayout);


        if (check == 1) {
            setTitle("快捷支付");
            codeLayout.setVisibility(View.VISIBLE);
            bankLayout.setVisibility(View.VISIBLE);
            bankImg.setBackgroundResource(R.mipmap.js_img_quick);
            bankTV.setText("快捷支付");
        } else if (check == 2) {
            setTitle("储蓄卡");
            bankLayout.setVisibility(View.VISIBLE);
            phoneLayout.setVisibility(View.GONE);
        } else if (check == 3) {
            setTitle("信用卡");
            bankImg.setBackgroundResource(R.mipmap.js_img_credit);
            bankTV.setText("信用卡");
            bankLayout.setVisibility(View.VISIBLE);
            phoneLayout.setVisibility(View.GONE);

        }



        selectBankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quickPayOrder.getQuickPayList()!=null){
                    selectBank();
                }
            }
        });

        getCodeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuickPay!=null){
                    getpabcode();
                }

            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canClick()){
                    submitPay();
                }
            }
        });
    }


    private boolean canClick(){
        String name=bankNameTV.getText().toString();
        String code = codeEdi.getText().toString();
        switch (check){
            case 1:
                if(BindId!=null&&!codeEdi.getText().toString().equals(""))return true;
                break;
            case 2:
                if(!name.equals(""))return true;
                break;
            case 3:
                if(!name.equals(""))return true;
                break;
        }
        return false;
    }


    private void getPayOrder(){
//        String url = APIUrl.URL + "app/rechargePay";
        HashMap<String,String> map = new HashMap<>();
        map.put("token", MyToken.getMyToken(this));
        map.put("action","rechargePay");
        map.put("orderId",orderId);
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                parseJSON(jsonObject);
                idTV.setText(quickPayOrder.getOrderNumber());
                //phoneTV.setText(quickPayOrder.getMobile());
                amountTV.setText(quickPayOrder.getPayAmount());
            }
        });
    }



    private void parseJSON(JSONObject jsonObject){
        try {
            String code = jsonObject.getString("code");
            if(code.equals("1")){
                QuickPayOrder quickPayOrder = new QuickPayOrder();
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                quickPayOrder.setMobile(jsonObject1.getString("bind_mobile"));
                //quickPayOrder.setMobiled(jsonObject1.getString("bind_mobiled"));
                quickPayOrder.setOrderNumber(jsonObject1.getString("orderNumber"));
                quickPayOrder.setPayAmount(jsonObject1.getString("pay_amount"));
                String bankBaseUrl = jsonObject1.getString("bank_baseUrl");
                if(check==1||check==2||check==3){
                    JSONArray jsonArray = new JSONArray();
                    if(check==1){
                        jsonArray = jsonObject1.getJSONArray("parinfo");
                        if(jsonArray.length()==0){
                            MyToast.makeToast(getApplicationContext(),"没有绑定银行卡，请用储蓄卡或信用卡支付");
                            finish();
                        }
                    }else{
                        jsonArray = jsonObject1.getJSONArray("quick_payment");
                    }

                    List<QuickPay> quickPays = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        QuickPay quickPay = new QuickPay();
                        quickPay.setBankCode(jsonObject2.getString("bankcode"));
                        quickPay.setBankName(jsonObject2.getString("bankname"));
                        quickPay.setCardType(jsonObject2.getString("cardtype"));
                        quickPay.setDailylimit(jsonObject2.getString("dailylimit").equals("0")?"无限制":jsonObject2.getString("dailylimit"));
                        quickPay.setId(jsonObject2.getString("id"));
                        quickPay.setSinglequota(jsonObject2.getString("singlequota").equals("0")?"无限制":jsonObject2.getString("singlequota"));
                        quickPay.setBankurl(bankBaseUrl+quickPay.getBankCode()+".jpg");
                        if(check==1){
                            quickPay.setBindId(jsonObject2.getString("bindId"));
                            quickPay.setTelephone(jsonObject2.getString("telephone"));
                        }
                        quickPays.add(quickPay);
                        quickPayOrder.setQuickPayList(quickPays);
                    }
                }
                this.quickPayOrder = quickPayOrder;
                preCheck();
            }else{
                MyJson.getMsg(getApplicationContext(),jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void preCheck(){
        List<QuickPay> quickPays = quickPayOrder.getQuickPayList();
        if(quickPays!=null){
            List<QuickPay> quickPays1 = new ArrayList<>();
            List<QuickPay> quickPays2 = new ArrayList<>();
            for(int i=0;i<quickPays.size();i++){
                if(quickPays.get(i).getCardType().equals("借记卡"))quickPays1.add(quickPays.get(i));
                else quickPays2.add(quickPays.get(i));

            }
            if(check==2){
                mQuickPay = quickPays1.get(0);
            }else if(check==3){
                mQuickPay = quickPays2.get(0);
            }else {
                mQuickPay = quickPays.get(0);
            }

            if(check==1) {
                BindId = mQuickPay.getBindId();
                phoneTV.setText(mQuickPay.getTelephone());
                bankCode = mQuickPay.getBankCode();
            }
            bankNameTV.setText(mQuickPay.getBankName());
            submitTV.setBackgroundResource(R.drawable.shape_corner_btn_red);
        }
    }


    //快捷支付获取验证码
    private void getpabcode(){
//        String url = APIUrl.URL+"app/rechargePabCode";
        HashMap<String, String> map = new HashMap<>();
        map.put("token",MyToken.getMyToken(this));
        map.put("action","rechargePabCode");
        map.put("orderNumber",quickPayOrder.getOrderNumber());
        map.put("bindId",BindId);
        map.put("bankCode",bankCode);
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetResponseLintener() {
            @Override
            public void getResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    if(code.equals("1")){
                        MyToast.makeToast(getApplicationContext(),"发送成功");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        dateTime = jsonObject1.getString("dateTime");
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

    private void submitPay(){
//        String url = APIUrl.URL ;
        HashMap<String,String> map = new HashMap<>();
        map.put("token",MyToken.getMyToken(this));
        map.put("orderNumber",quickPayOrder.getOrderNumber());

        if(check==2||check==3){
//            url = url + "app/rechargeToPabPay";
            map.put("action","rechargeToPabPay");
            map.put("plantBankId",mQuickPay.getBankCode());
        }else if(check==1){
//            url = url + "app/rechargePabPay";
            map.put("action","rechargePabPay");
            map.put("bindId",BindId);
            map.put("dateTime",dateTime);
            map.put("phone_code",codeEdi.getText().toString());
        }
        MVolley.getInstance(this).addRequest(APIUrl.URL, map, new MVolley.GetStringResponseLintener() {
            @Override
            public void getResponse(String s) {
                if(check==2||check==3){
                    Intent intent = new Intent(RechargePayActivity.this,WebViewActivity.class);
                    intent.putExtra("html",s);
                    startActivityForResult(intent,1000);
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String code = jsonObject.getString("code");
                        if(code.equals("1")){
                            MyToast.makeToast(getApplicationContext(),"支付成功");
                            setResult(1001);
                            finish();
                        }
                        else MyJson.getMsg(getApplicationContext(),jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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


    //弹出选择银行的bottomsheet
    private void selectBank(){
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_banklist,bottomSheetLayout,false);
        ListView listView = (ListView) view.findViewById(R.id.bottomsheet_banklist);
        bottomSheetLayout.showWithSheetView(view);
        List<QuickPay> quickPays = quickPayOrder.getQuickPayList();
        List<QuickPay> quickPays1 = new ArrayList<>();
        List<QuickPay> quickPays2 = new ArrayList<>();
        for(int i=0;i<quickPays.size();i++){
            if(quickPays.get(i).getCardType().equals("借记卡"))quickPays1.add(quickPays.get(i));
            else quickPays2.add(quickPays.get(i));

        }
        final ListViewAdapter adapter;
        if(check==2){
            adapter = new ListViewAdapter(this,quickPays1);
        }else if(check==3){
            adapter = new ListViewAdapter(this,quickPays2);
        }else{
            adapter = new ListViewAdapter(this,quickPays);
        }

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mQuickPay = (QuickPay) adapter.getItem(position);
                if(check==1) {
                    BindId = mQuickPay.getBindId();
                    bankCode = mQuickPay.getBankCode();
                    phoneTV.setText(mQuickPay.getTelephone());
                }
                bankNameTV.setText(mQuickPay.getBankName());
                submitTV.setBackgroundResource(R.drawable.shape_corner_btn_red);
                bottomSheetLayout.dismissSheet();
            }
        });
    }


    private class ListViewAdapter extends BaseAdapter {
        private Context context;
        private List<QuickPay> quickPays;
        public ListViewAdapter(Context context,List<QuickPay> quickPays){
            this.context = context;
            this.quickPays = quickPays;
        }

        @Override
        public int getCount() {
            return quickPays.size();
        }

        @Override
        public Object getItem(int position) {
            return quickPays.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.bank_list_item,null);
                viewHolder.dailylimitTV = (TextView) convertView.findViewById(R.id.bank_item_dailylimit);
                viewHolder.nameTV = (TextView) convertView.findViewById(R.id.bank_item_name);
                viewHolder.singlequotaTV = (TextView) convertView.findViewById(R.id.bank_item_singlequota);
                viewHolder.typeTV = (TextView) convertView.findViewById(R.id.bank_item_type);
                viewHolder.bankImg = (NetworkImageView) convertView.findViewById(R.id.bank_item_img);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            QuickPay quickPay = quickPays.get(position);
            viewHolder.dailylimitTV.setText(quickPay.getDailylimit());
            viewHolder.typeTV.setText(quickPay.getCardType());
            viewHolder.singlequotaTV.setText(quickPay.getSinglequota());
            viewHolder.nameTV.setText(quickPay.getBankName());
            viewHolder.bankImg.setDefaultImageResId(R.color.colorGray);
            if(!quickPay.getBankurl().equals("")){
                viewHolder.bankImg.setImageUrl(quickPay.getBankurl(),MVolley.getInstance(context).getImageLoader());
            }
            return convertView;
        }

        private class ViewHolder{
            public TextView nameTV,typeTV,dailylimitTV,singlequotaTV;
            public NetworkImageView bankImg;
        }
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
