package com.lin.download;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.lin.downloadwork.basic.Entrance;
import com.lin.downloadwork.business.ISwapCursorData;
import com.lin.downloadwork.business.ViewSupportLoader;
import com.lin.downloadwork.business.model.DownLoadInfo;

import y.com.sqlitesdk.framework.business.BusinessUtil;

public class AdActivity extends AppCompatActivity {


    private TextView id_onclick;

    private DownLoadInfo downLoadInfo;

    private ProgressBar id_ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
    }

    private void initView() {
        id_ProgressBar = (ProgressBar) findViewById(R.id.id_ProgressBar);
        id_onclick = (TextView) findViewById(R.id.id_onclick);
        id_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downLoadInfo != null) {
                    String objectId = downLoadInfo.getObjectId();
                    switch (downLoadInfo.getStatus()) {
                        case DownLoadInfo.COMPLETED_STATUS:
//                            if(new F)
                            Entrance.launchApp(v.getContext(), downLoadInfo.getSavePath());
                            break;
                        case DownLoadInfo.ERROR_STATUS:
                            Entrance.download(objectId);
                            break;
                        case DownLoadInfo.PAUSE_STATUS:
                            Entrance.download(objectId);
                            break;
                        case DownLoadInfo.DOING_STATUS:
                            Entrance.pause(objectId);
                            break;
                        case DownLoadInfo.NOT_HAD_STATUS:
                            Entrance.download(objectId);
                            break;
                        case DownLoadInfo.WAITTING_STATUS:
                            Entrance.download(objectId);
                            break;
                    }
                }

            }
        });
        new ViewSupportLoader().init(AdActivity.this, 1200, Entrance.createCl(AdActivity.this, "id = 5")
                , new ISwapCursorData() {
                    @Override
                    public Cursor swapCursor(Cursor newCursor) {
                        swapCursor2(newCursor);
                        return null;
                    }
                });
    }

    private void swapCursor2(Cursor newCursor) {

        if(newCursor == null){
            return;
        }

        try {
            downLoadInfo = BusinessUtil.reflectCursorOne(newCursor, DownLoadInfo.class, true);

            id_ProgressBar.setProgress((int) ((downLoadInfo.getCurrentLeng() * 1f / downLoadInfo.getToTalLeng()) * 100));


//            id_onclick.setProgress((downLoadInfo.getCurrentLeng() * 1f / downLoadInfo.getToTalLeng()));

            switch (downLoadInfo.getStatus()) {
                case DownLoadInfo.COMPLETED_STATUS:
                    id_onclick.setText("完成");
                    id_onclick.setTextColor(Color.RED);
                    id_ProgressBar.setProgress(100);
                    break;
                case DownLoadInfo.ERROR_STATUS:
                    id_onclick.setText("下载错误");
                    break;
                case DownLoadInfo.PAUSE_STATUS:
                    id_onclick.setText("已暂停"+String.format("%.2f",
                            (downLoadInfo.getCurrentLeng() * 1f / downLoadInfo.getToTalLeng()) * 100)+" %");
                    break;
                case DownLoadInfo.DOING_STATUS:
                    id_onclick.setText("正在下载 " +String.format("%.2f",
                            (downLoadInfo.getCurrentLeng() * 1f / downLoadInfo.getToTalLeng()) * 100) + " %");
                    break;
                case DownLoadInfo.NOT_HAD_STATUS:
                    id_onclick.setText("可以下载");
                    break;
                case DownLoadInfo.WAITTING_STATUS:
                    id_onclick.setText("等待中");
                    id_onclick.setTextColor(Color.BLUE);
                    break;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
}
