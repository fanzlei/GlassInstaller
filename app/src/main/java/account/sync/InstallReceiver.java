package account.sync;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import cn.glassx.wear.account.IOauthToken;
import cn.glassx.wear.installer.AppConfig;

/**
 * Created by Fanz on 4/2/15.
 */
public class InstallReceiver extends BroadcastReceiver {

    private IOauthToken oauthToken;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            oauthToken = IOauthToken.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onReceive(final Context context, final Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Intent tokenIntent = new Intent("android.accounts.AccountAIDLService");
                    context.bindService(tokenIntent,conn,Context.BIND_AUTO_CREATE);
                    AppConfig.authToken = oauthToken.getOauthToken();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if(AppConfig.authToken == null || AppConfig.authToken.isEmpty()){
                    //Todo 网络不可以时

                    return;
                }
                AccountUtils.requestSync(context,
                        AccountManager.get(context).getAccountsByType(AppConfig.ACCOUNT_TYPE)[0]);
            }
        }).start();
    }
}
