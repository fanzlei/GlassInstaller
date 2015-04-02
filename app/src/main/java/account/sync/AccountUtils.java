package account.sync;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import cn.glassx.wear.installer.AppConfig;

/**
 * Created by Fanz on 4/2/15.
 */
public class AccountUtils {

    /**
     * 手动请求同步账户关联的内容
     * */
    public static void requestSync(Context context, Account mConnectedAccount) {
        if(mConnectedAccount == null){return;}
        String authority = AppConfig.SYNC_ADAPTER_AUTHORITY;
        ContentResolver.setIsSyncable(mConnectedAccount, authority, 1); //打开自动同步
        ContentResolver.setSyncAutomatically(mConnectedAccount, authority, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // 手动同步
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
        ContentResolver.requestSync(mConnectedAccount, authority, bundle);
    }

}
