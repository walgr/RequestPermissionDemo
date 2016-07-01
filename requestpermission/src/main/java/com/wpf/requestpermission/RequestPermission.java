package com.wpf.requestpermission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王朋飞 on 7-1-0001.
 * 申请权限
 */

public abstract class RequestPermission {

    private int requestCode;
    private Activity activity;
    private String[] permissionList;

    protected RequestPermission(Activity activity, String[] permissionList, int requestCode) {
        this.requestCode = requestCode;
        this.activity = activity;
        this.permissionList = permissionList;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionList = getShouldRequestList();
            if(permissionList.length != 0)
                ActivityCompat.requestPermissions(activity, permissionList, requestCode);
            else onSuccess();
        } else onSuccess();
    }

    protected RequestPermission(Activity activity, String permission, int requestCode) {
        this.requestCode = requestCode;
        this.activity = activity;
        this.permissionList = new String[]{permission};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionList = getShouldRequestList();
            if(permissionList.length != 0)
                ActivityCompat.requestPermissions(activity, permissionList, requestCode);
            else onSuccess();
        } else onSuccess();
    }

    private String[] getShouldRequestList() {
        List<String> permissions = new ArrayList<>();
        for(String permission : permissionList) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(activity, permission);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) permissions.add(permission);
        }
        return permissions.toArray(new String[]{});
    }

    private String[] getFailList(String[] permissionList,int[] grantResults) {
        List<String> failList = new ArrayList<>();
        for(int i = 0;i<grantResults.length;++i) {
            if(grantResults[i] == -1) failList.add(permissionList[i]);
        }
        return failList.toArray(new String[]{});
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == this.requestCode && Arrays.equals(permissions, getShouldRequestList())) {
            String[] failList = getFailList(permissions,grantResults);
            if(failList.length == 0)
                this.onSuccess();
            else
                this.onFail(failList);
        }

    }

    public abstract void onSuccess();

    public abstract void onFail(String[] failList);
}
