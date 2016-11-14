package com.wpf.requestpermission;

/**
 * Created by 王朋飞 on 11-14-0014.
 * 申请结果接口
 */

public interface RequestResult {
    void onSuccess();
    void onFail(String[] failList);
}
