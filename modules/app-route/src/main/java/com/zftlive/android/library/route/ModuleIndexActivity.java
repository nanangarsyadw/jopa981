package com.zftlive.android.library.route;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 *
 *  业务模块入口基类
 *
 *  @author 曾繁添
 *  @version 1.0
 *
 */
public class ModuleIndexActivity extends Activity implements IRoute {

    private String indexParam = "";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void paserParam(Intent mIntent){
        Bundle mParam = mIntent.getExtras();
        if(null != mParam){
            indexParam = mParam.getString(INDEX_PARAMS);
        }
    }
}
