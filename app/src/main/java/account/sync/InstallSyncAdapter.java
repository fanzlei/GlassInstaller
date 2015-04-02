package account.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import cn.glassx.wear.installer.InstallManager;

/**
 * Created by Fanz on 4/2/15.
 */
public class InstallSyncAdapter extends AbstractThreadedSyncAdapter {

    private Context context ;

    public InstallSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        InstallManager.startTask(context);
    }
}
