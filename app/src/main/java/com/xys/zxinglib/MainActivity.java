package com.xys.zxinglib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        Button aboutStatusButton = (Button) this.findViewById(R.id.info);
        aboutStatusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Home.class);
                startActivity(intent);
            }
        });
        scanBarCodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            String str = HTTPRequest(scanResult);
            //String OverallStr = "";
            String errno_json= "",info_json = "",data_json = "";
            //解析JSON
            try {
                JSONObject jsonObject = new JSONObject(str);
                errno_json = jsonObject.getString("errno");
                info_json  = jsonObject.getString("info");
                data_json  = jsonObject.getString("data");
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                LoginStatus loginStatus = new LoginStatus(errno_json, info_json, data_json);
                bundle.putSerializable("loginStatus", loginStatus);
                Intent intent = new Intent(MainActivity.this, LoginReceive.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private String HTTPRequest(String scanResult) {
        String URL = "http://www.tdsast.cn/index.php/Interface/android";
        String msg = "";
        HttpPost request = new HttpPost(URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("accesskey", "Ef3VUypAKQ9C"));
        params.add(new BasicNameValuePair("type","1"));
        params.add(new BasicNameValuePair("data", scanResult));
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = new DefaultHttpClient().execute(request);
            if(response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity httpEntity = response.getEntity();
                msg = EntityUtils.toString(httpEntity);//取出应答字符串
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }
}