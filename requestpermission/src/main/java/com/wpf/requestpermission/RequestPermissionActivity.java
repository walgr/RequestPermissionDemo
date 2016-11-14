package com.wpf.requestpermission;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 王朋飞 on 8-10-0010.
 *
 */

public class RequestPermissionActivity extends AppCompatActivity {

    public void request(Activity activity, String[] permissionList, int requestCode, RequestResult requestResult) {
        RequestPermission.request(activity,permissionList,requestCode,requestResult);
    }

    public void request(Activity activity, String permission, int requestCode, RequestResult requestResult) {
        RequestPermission.request(activity,permission,requestCode,requestResult);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RequestPermission.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
