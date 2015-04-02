package test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Fanz on 3/30/15.
 * 接收应用安装、卸载操作的广播
 */
public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("GlassInstaller","接收到应用安装、卸载广播");
        Intent intent1 = new Intent("cn.glassx.installerService");
        intent1.putExtras(intent.getExtras());
        context.startService(intent1);
    }
}
