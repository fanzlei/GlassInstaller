package test;

import android.content.Intent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import cn.glassx.wear.installer.InstallManager;
import cn.glassx.wear.installer.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onClick_install(View view){
        Intent intent = new Intent("cn.wear.glassx.test.apkActionReceiver");
        intent.putExtra("action","install");
        //intent.putExtra("apkPath", Environment.getExternalStorageDirectory().toString()+ File.separator+"notebook2.apk");
        intent.putExtra("apkPath","/sdcard/notebook2.apk");
        sendBroadcast(intent);

    }

    public void onClick_uninstall(View view){
        Intent intent = new Intent("cn.wear.glassx.test.apkActionReceiver");
        intent.putExtra("action","unInstall");
        intent.putExtra("packageName","com.fanz.notebook2");
        sendBroadcast(intent);
    }

    public void onClick_task(View view){
        InstallManager.startTask(this);

    }
}
