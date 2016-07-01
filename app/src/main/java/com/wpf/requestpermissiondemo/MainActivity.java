package com.wpf.requestpermissiondemo;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wpf.requestpermission.RequestPermission;

public class MainActivity extends AppCompatActivity {

    private String Tag = "MainActivity";
    private RequestPermission requestPermission;

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission = new RequestPermission(this,permissions,1) {
            @Override
            public void onSuccess() {
                Log.e(Tag,"获取成功");
            }

            @Override
            public void onFail(String[] failList) {
                Log.e(Tag,"获取失败");
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        requestPermission.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
