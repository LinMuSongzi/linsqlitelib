package com.lin.download;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public Toast toast;
    @Bind(R.id.id_progressBar)
    SeekBar id_progressBar;
    @Bind(R.id.id_start_btn)
    View id_start_btn;
    @Bind(R.id.id_puase_btn)
    View id_puase_btn;
    @Bind(R.id.id_lenght)
    TextView id_lenght;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

    }

//    private void test() {
//        EventBus.getDefault().register(this);
//        Zygote.createGet(StrEntity.class,"http://www.baidu.com",null).asyncExecute();
//
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessage(StrEntity strEntity){
//        if(strEntity.getRequet()!=null){
//
//        }
//    }
//

    public void onStartClick(View view) {
        startActivity(new Intent(this,AdActivity.class));
    }

    public void onPauseClick(View view) {
        startActivity(new Intent(this,FileListActivity.class));
    }

    private void toastShow(String s) {
        toast.setText(s);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
