package com.jsojs.mywalletmodule.app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsojs.baselibrary.myview.MyDialog;
import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.util.ActivityStack;


/**
 * Created by Administrator on 2016/8/13.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextView title;
    private TextView option;
    private LinearLayout backButton;
    private OnClickOptionListener onClickOptionListener;
    private int actionBarStyle = 0;
    private ImageView optionImage;
    public static int WHITE_ACTION_BAR = 0;
    public static int SEARCH_ACTION_BAR = 1;
    public static int GOODS_INFO_ACTION_BAR = 2;
    public static int RED_ACTION_BAR = 3;
    protected Dialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getScreenManager().pushActivity(this);
        actionBarStyle = setActionBarStyle();
        Log.i("shao","actionbarnum->"+actionBarStyle);
        setActionbar();

        loadingDialog = MyDialog.createLoadingDialog(this);
        loadingDialog.hide();
        initContentView(savedInstanceState);

    }

    protected abstract void initContentView(Bundle savedInstanceState);

    public void setActionbar(){
        if(actionBarStyle==0||actionBarStyle==2){
            setTheme(R.style.MyTheme);
        }else{
            setTheme(R.style.AppTheme);
        }

        actionBar = getSupportActionBar();

        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(1);
        }

        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        setBarStyle(actionBarStyle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });

        if(option!=null){
            option.setText("");
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickOptionListener!=null){
                        onClickOptionListener.onClickOption();
                    }
                }
            });
        }


        if(optionImage!=null)
            optionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickOptionListener!=null){
                        onClickOptionListener.onClickOption();
                    }
                }
            });
    }

    private void setBarStyle(int i){
        switch (i){
            case 0:
                actionBar.setCustomView(R.layout.add_address_actionbar);
                backButton = (LinearLayout) actionBar.getCustomView().findViewById(R.id.add_address_backlayout);
                title = (TextView) actionBar.getCustomView().findViewById(R.id.add_address_title);
                option = (TextView) actionBar.getCustomView().findViewById(R.id.add_address_save);
                break;
            case 1:
                actionBar.setCustomView(R.layout.red_back_actionbar);
                title= (TextView) actionBar.getCustomView().findViewById(R.id.red_actionbar_title);
                backButton= (LinearLayout) actionBar.getCustomView().findViewById(R.id.red_actionbar_backlayout);
                optionImage= (ImageView) actionBar.getCustomView().findViewById(R.id.red_actionbar_search);
                break;
            case 2:
                actionBar.setCustomView(R.layout.goods_info_actionbar);
                title = (TextView) actionBar.getCustomView().findViewById(R.id.goods_actionbar_title);
                backButton = (LinearLayout) actionBar.getCustomView().findViewById(R.id.goods_actionbar_backlayout);
                optionImage = (ImageView) actionBar.getCustomView().findViewById(R.id.goods_actionbar_car);
                break;
            case 3:
                actionBar.setCustomView(R.layout.red_back_actionbar);
                title= (TextView) actionBar.getCustomView().findViewById(R.id.red_actionbar_title);
                backButton= (LinearLayout) actionBar.getCustomView().findViewById(R.id.red_actionbar_backlayout);
                optionImage= (ImageView) actionBar.getCustomView().findViewById(R.id.red_actionbar_search);
                optionImage.setVisibility(View.INVISIBLE);
                break;
        }

    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setOption(String option){
        if(this.option!=null)
        this.option.setText(option);
    }





    public void setOnClickOptionListener(OnClickOptionListener onClickOptionListener) {
        this.onClickOptionListener = onClickOptionListener;
    }

    protected abstract void onBack();

    public interface OnClickOptionListener{
        public void onClickOption();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public int setActionBarStyle(){
        return 0;
    }

    public void setOptionImage(int reId){
        if(optionImage!=null)optionImage.setBackgroundResource(reId);
    }


    protected void tokenOut(){
        Intent intent = new Intent("tokenError");
        intent.putExtra("isTokenOut", true);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getScreenManager().popActivity(this);
        backButton.setOnClickListener(null);
        if(option!=null)
            option.setOnClickListener(null);
        if(optionImage!=null)
            optionImage.setOnClickListener(null);
    }
}
