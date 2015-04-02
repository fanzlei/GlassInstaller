package account.sync;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class InstallSyncService extends Service {
    Context context = this;

    private static final Object sSyncAdapterLock = new Object();
    private static InstallSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {

        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null)
                sSyncAdapter = new InstallSyncAdapter(context, true);

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
