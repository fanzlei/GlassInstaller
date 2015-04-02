package cn.glassx.wear.installer;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

import model.APKMessage;
import network.GetData;

/**
 * Created by Fanz on 3/31/15.
 * 安装管理类
 * 安装列表中有多个数据，则依次执行任务
 */
public class InstallManager {

    public static  List<APKMessage> apkMessages;
    private static int nowtask = 0;
    public static Context context;

    /**是否在任务结束后再次执行任务
     * 用于本地正在执行安装、卸载任务的时候，用户再次请求任务
     * 当apkMessages不等于null的时候本地任务正在执行
     * */
    public static boolean restartWhenTaskEnd = false;


    /**
     * 开始执行安装、卸载任务
     * */
    public static void startTask(Context context){
        InstallManager.context = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetData.getTaskList(AppConfig.authToken);
            }
        }).start();

    }

    /**执行下一个任务,自动调用*/
    public   static void nextTask(){
        Log.d("GlassInstaller","apkMessages.size="+apkMessages.size()+";nowtask="+nowtask);
        if(apkMessages.size() > nowtask){
            APKMessage apkMessage = apkMessages.get(nowtask++);
            switch (apkMessage.getOperation()){
                case AppConfig.ACTION_INSTALL:
                    //安装任务
                    if(isNeedToInstall(apkMessage)){
                        GetData.getPackage(apkMessage);
                    }else{
                        //不需要安装，执行下一个任务
                        nextTask();
                    }
                    break;
                case AppConfig.ACTION_UNINSTALL:
                    // 卸载任务
                    int result = PackageInstaller.unInstall(apkMessage.getPackageName());
                    if(result == -1){
                        Log.d("GlassInstaller","卸载失败");
                    }else{Log.d("GlassInstaller","卸载成功");}
                    nextTask();
                    break;
            }
        }else{
            apkMessages = null;
            nowtask = 0;
            if(restartWhenTaskEnd){startTask(context);}
        }
    }

    /**判断应用本地是否已经安装或者需要升级*/
    private static boolean isNeedToInstall(APKMessage apkMessage){
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(apkMessage.getPackageName(),0);

        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if(packageInfo == null){
            //系统未安装该应用
            Log.d("GlassInstaller","系统未安装应用"+apkMessage.getPackageName());
            return true;
        }else {
            if(apkMessage.getVersion() > packageInfo.versionCode){
                //应用需要升级
                Log.d("GlassInstaller","本地版本号"+packageInfo.versionCode+"需要安装的应用版本号"+apkMessage.getVersion());
                return true;
            }
        }

        return false;
    }





}
