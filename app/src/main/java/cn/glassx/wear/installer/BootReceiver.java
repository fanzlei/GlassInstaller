package cn.glassx.wear.installer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Fanz on 4/2/15.
 * 监听开机广播
 *
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context,StickService.class));
    }
}
