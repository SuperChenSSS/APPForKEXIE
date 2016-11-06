package com.xys.zxinglib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by SuperChen on 2016/11/6.
 */

public class LoginReceive extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_status);
        TextView errno = (TextView)findViewById(R.id.errno);
        TextView info  = (TextView)findViewById(R.id.info);
        TextView data  = (TextView)findViewById(R.id.data);

        Intent intent = getIntent();
        LoginStatus loginStatus = (LoginStatus) intent.getSerializableExtra("loginStatus");
        errno.setText("errno:" + loginStatus.getErrno());
        info.setText("info:" + loginStatus.getInfo());
        data.setText("data:" + loginStatus.getData());
    }
}
