package test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import cn.glassx.wear.installer.AppConfig;
import cn.glassx.wear.installer.PackageInstaller;

/**
 * Created by Fanz on 3/30/15.
 * 执行应用安装、卸载操作的服务
 */
public class InstallerService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("GlassInstaller","服务启动");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra(AppConfig.ACTION);
        Log.d("GlassInstaller","action = "+action);
        if(action.equals(AppConfig.ACTION_INSTALL) && action != null){
            Log.d("GlassInstaller","安装应用");
            String apkPath = intent.getStringExtra(AppConfig.APK_PATH);
            PackageInstaller.install(apkPath);
        }
        else if(action.equals(AppConfig.ACTION_UNINSTALL) && action != null){
            Log.d("GlassInstaller","卸载应用");
            String packageName = intent.getStringExtra(AppConfig.PACKAGE_NAME);
            PackageInstaller.unInstall(packageName);
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
