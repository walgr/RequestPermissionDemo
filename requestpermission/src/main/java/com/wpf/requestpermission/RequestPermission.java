package com.wpf.requestpermission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class RequestPermission {

    private static int requestCode;
    private static List<Activity> activityList = new ArrayList<>();
    private static List<RequestResult> requestResultList = new ArrayList<>();

    public static void request(Activity activity, String[] permissionList, int requestCode, RequestResult requestResult) {
        RequestPermission.activityList.add(0,activity);
        RequestPermission.requestCode = requestCode;
        RequestPermission.requestResultList.add(0,requestResult);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionList = getShouldRequestList(permissionList);
            if(permissionList.length != 0)
                ActivityCompat.requestPermissions(activity, permissionList, requestCode);
            else onSuccess();
        } else onSuccess();
    }

    public static void request(Activity activity, String permission, int requestCode, RequestResult requestResult) {
        request(activity,new String[]{permission},requestCode,requestResult);
    }

    private static String[] getShouldRequestList(String[] permissionList) {
        List<String> permissions = new ArrayList<>();
        if(!activityList.isEmpty()) {
            for (String permission : permissionList) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(activityList.get(0), permission);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED)
                    permissions.add(permission);
            }
        }
        return permissions.toArray(new String[]{});
    }

    private static String[] getFailList(String[] permissionList,int[] grantResults) {
        List<String> failList = new ArrayList<>();
        for(int i = 0;i<grantResults.length;++i) {
            if(grantResults[i] == -1) failList.add(permissionList[i]);
        }
        return failList.toArray(new String[]{});
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RequestPermission.requestCode) {
            String[] failList = getFailList(permissions,grantResults);
            if(failList.length == 0) onSuccess();
            else onFail(failList);
        }
    }

    private static void onSuccess() {
        if(!requestResultList.isEmpty()) {
            requestResultList.get(0).onSuccess();
            removeActivity();
        }
    }

    private static void onFail(String[] failList) {
        if(!requestResultList.isEmpty()) {
            requestResultList.get(0).onFail(failList);
            removeActivity();
        }
    }

    private static void removeActivity() {
        if(!activityList.isEmpty()) activityList.remove(0);
        if(!requestResultList.isEmpty()) requestResultList.remove(0);
    }
}
