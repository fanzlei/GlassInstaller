package cn.glassx.wear.installer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import account.sync.InstallReceiver;
import test.ActionReceiver;
/**
 * 系统后台一直运行的服务，开机自启动
 * 确保能够接收到应用安装广播
 * */
public class StickService extends Service {
    public StickService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent("cn.glassx.wear.installer.stickService"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(new InstallReceiver(),new IntentFilter("cn.wear.glassx.apkActionReceiver"));
        registerReceiver(new ActionReceiver(),new IntentFilter("cn.wear.glassx.test.apkActionReceiver"));
        return super.onStartCommand(intent, Service.START_STICKY, startId);
    }
}
