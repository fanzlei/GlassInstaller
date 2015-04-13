package account.sync;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import authenticator.TokenUtils;
import cn.glassx.wear.account.IOauthToken;
import cn.glassx.wear.installer.AppConfig;

/**
 * Created by Fanz on 4/2/15.
 */
public class InstallReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(final Context context, final Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    /*自己获得token*/
                    AppConfig.authToken = TokenUtils.getAuthToken(
                            AccountManager.get(context).getAccountsByType(AppConfig.ACCOUNT_TYPE)[0],
                            context
                    );

                if(AppConfig.authToken == null || AppConfig.authToken.isEmpty()){
                    // 网络不可以时
                    Log.d("GlassInstaller","网络不可用");
                    return;
                }
                AccountUtils.requestSync(context,
                        AccountManager.get(context).getAccountsByType(AppConfig.ACCOUNT_TYPE)[0]);
            }
        }).start();
    }
}
