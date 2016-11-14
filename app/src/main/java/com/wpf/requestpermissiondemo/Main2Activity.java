package com.wpf.requestpermissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.wpf.requestpermission.RequestPermissionActivity;
import com.wpf.requestpermission.RequestResult;

public class Main2Activity extends RequestPermissionActivity {

    private String Tag = "Main2Activity";

    private String permission = Manifest.permission.CALL_PHONE;
    private String[] permissions = new String[]{permission};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        request(this, permission, 2, new RequestResult() {
            @Override
            public void onSuccess() {
                Log.e(Tag,"成功");
            }

            @Override
            public void onFail(String[] failList) {
                Log.e(Tag,"失败");
                finish();

            }
        });
    }
}
