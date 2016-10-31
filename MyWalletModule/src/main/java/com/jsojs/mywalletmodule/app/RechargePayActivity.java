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
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jsojs.baselibrary.util.MyToast;
import com.jsojs.baselibrary.volley.MVolley;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.bean.QuickPay;
import com.jsojs.mywalletmodule.bean.QuickPayOrder;
import com.jsojs.mywalletmodule.bean.QuickPayment;
import com.jsojs.mywalletmodule.bean.RechargeOrder;
import com.jsojs.mywalletmodule.contract.RechargePayContract;
import com.jsojs.mywalletmodule.presenter.RechargePayPresenter;
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
public class RechargePayActivity extends BaseActivity implements RechargePayContract.View {
    private final static int REQUEST_WEBPAY = 1000;
    private static int WAIT_TIME = 60;
    private int check;
    private TextView idTV,phoneTV,amountTV,getCodeTV,submitTV,balanceTV,bankNameTV;
    private EditText codeEdi;
    private LinearLayout balanceLayout,codeLayout,bankLayout,phoneLayout;
    private int second = WAIT_TIME;
    private Timer timer;
    private final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(second == WAIT_TIME){
                        getCodeTV.setBackgroundResource(R.drawable.shape_corner_btn_disable);
                        getCodeTV.setClickable(false);
                    }
                    getCodeTV.setText(second+"");
                    second--;
                    if(second == -1){
                        getCodeTV.setBackgroundResource(R.drawable.shape_corner_btn_red);
                        getCodeTV.setClickable(true);
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
    private QuickPayment mQuickPayment;
    private ImageView bankImg;
    private TextView bankTV;
    private String BindId;
    private String bankCode;
    private String dateTime;
    private String orderId;
    private RechargePayContract.Presenter mPresenter;
    private String bankUrl;
    private View bottomView;
    private ListView bottomListView;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        mPresenter = new RechargePayPresenter(this,this);
        setContentView(R.layout.wallet_activity_pay_online);
        check = getIntent().getIntExtra("check",0);
        orderId = getIntent().getStringExtra("id");
        mPresenter.mode(check);
        initView();
        mPresenter.PayOrder(orderId);
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
        bottomView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_banklist,bottomSheetLayout,false);
        bottomListView = (ListView) bottomView.findViewById(R.id.bottomsheet_banklist);


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
                bottomSheetLayout.showWithSheetView(bottomView);
            }
        });

        getCodeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCode(idTV.getText().toString(),mQuickPayment.getBindId(),mQuickPayment.getBankcode());

            }
        });
        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkPayMode(check);
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
    public void doTokenOut() {
        tokenOut();
    }

    @Override
    public void getCodeSuccess(String dateTime) {
        this.dateTime = dateTime;
        setTimer();
    }

    @Override
    public void getOrderSuccess(RechargeOrder rechargeOrder) {
        idTV.setText(rechargeOrder.getOrderNumber());
        amountTV.setText(rechargeOrder.getPay_amount());
        bankUrl = rechargeOrder.getBank_baseUrl();
        List<QuickPayment> quickPays = rechargeOrder.getParinfo();
        List<QuickPayment> quickPays1 = new ArrayList<>();
        List<QuickPayment> quickPays2 = new ArrayList<>();
        List<QuickPayment> quickPays3 = rechargeOrder.getQuick_payment();
        for(int i=0;i<quickPays3.size();i++){
            if(quickPays3.get(i).getCardtype().equals("借记卡"))quickPays1.add(quickPays3.get(i));
            else quickPays2.add(quickPays3.get(i));

        }
        final ListViewAdapter adapter;
        if(check==2){
            adapter = new ListViewAdapter(this,quickPays1);
        }else if(check==3){
            adapter = new ListViewAdapter(this,quickPays2);
        }else{
            adapter = new ListViewAdapter(this,quickPays);
        }

        bottomListView.setAdapter(adapter);
        bottomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mQuickPayment = (QuickPayment) adapter.getItem(position);
                if(check==1) {
                    BindId = mQuickPayment.getBindId();
                    bankCode = mQuickPayment.getBankcode();
                    phoneTV.setText(mQuickPayment.getTelephone());
                }
                bankNameTV.setText(mQuickPayment.getBankname());
                submitTV.setBackgroundResource(R.drawable.shape_corner_btn_red);
                bottomSheetLayout.dismissSheet();
            }
        });
    }

    @Override
    public void quickPaySuccess() {
        setResult(RESULT_OK);
        Intent intent = new Intent(this,RechargeSuccessActivity.class);
        intent.putExtra("name",bankNameTV.getText().toString());
        intent.putExtra("amount",amountTV.getText().toString());
        startActivity(intent);
    }

    @Override
    public void payOnlineSuccess(String html) {
        Intent intent = new Intent(RechargePayActivity.this,WebViewActivity.class);
        intent.putExtra("html",html);
        startActivityForResult(intent,REQUEST_WEBPAY);
    }

    @Override
    public void doQuickPay() {
        mPresenter.quickPay(idTV.getText().toString(),mQuickPayment.getBindId(),dateTime,codeEdi.getText().toString());
    }

    @Override
    public void doOnlinePay() {
        mPresenter.onlinePay(idTV.getText().toString(),mQuickPayment.getBankcode());
    }

    @Override
    public void selectBindBank(QuickPayment quickPayment) {
        this.mQuickPayment = quickPayment;
        bankNameTV.setText(mQuickPayment.getBankname());
        bankCode = mQuickPayment.getBankcode();
    }

    @Override
    public void noBindBank() {
        finish();
    }

    @Override
    public void getOrderFailure() {
        finish();
    }


    private class ListViewAdapter extends BaseAdapter {
        private Context context;
        private List<QuickPayment> quickPayments;
        public ListViewAdapter(Context context,List<QuickPayment> quickPayments){
            this.context = context;
            this.quickPayments = quickPayments;
        }

        @Override
        public int getCount() {
            return quickPayments.size();
        }

        @Override
        public Object getItem(int position) {
            return quickPayments.get(position);
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
                viewHolder.bankImg = (ImageView) convertView.findViewById(R.id.bank_item_img);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            QuickPayment quickPayment = quickPayments.get(position);
            viewHolder.dailylimitTV.setText(quickPayment.getDailylimit());
            viewHolder.typeTV.setText(quickPayment.getCardtype());
            viewHolder.singlequotaTV.setText(quickPayment.getSinglequota());
            viewHolder.nameTV.setText(quickPayment.getBankname());
            if(!bankUrl.equals("")){
                MVolley.getInstance(context).loadImageByVolley(viewHolder.bankImg,bankUrl+quickPayment.getBankcode()+".jpg");
            }
            return convertView;
        }

        private class ViewHolder{
            public TextView nameTV,typeTV,dailylimitTV,singlequotaTV;
            public ImageView bankImg;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_WEBPAY && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }

}
