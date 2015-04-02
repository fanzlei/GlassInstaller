package cn.glassx.wear.installer;


import android.util.Log;

import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 * Created by Fanz on 3/30/15.
 * 静默安装、卸载类
 *
 */
public class PackageInstaller {

    private static String cmd_install = "pm install -r ";
    private static String cmd_uninstall = "pm uninstall ";

    /**
     * 执行静默安装
     * @param apkPath 保存在SD卡中的APK的路径
     * */
    public static int install(String apkPath){
        String cmd = cmd_install + apkPath;
        Log.d("GlassInstaller","静默安装命令："+cmd);
        return excuteSuCMD(cmd);
    }

    /**
     * 执行静默卸载
     * @param packageName 需要卸载的应用包名
     * */
    public static int unInstall(String packageName){
        String cmd = cmd_uninstall + packageName;
        Log.d("GlassInstaller","静默卸载命令："+cmd);
        return excuteSuCMD(cmd);
    }

    //执行shell命令
    private static int excuteSuCMD(String cmd) {
        try {
            //申请获取Root权限
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(
                    (OutputStream) process.getOutputStream());
            // 部分手机Root之后Library path 丢失，导入library path可解决该问题
            dos.writeBytes((String) "export LD_LIBRARY_PATH=/vendor/lib:/system/lib\n");
            cmd = String.valueOf(cmd);
            dos.writeBytes((String) (cmd + "\n"));
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            process.waitFor();
            int result = process.exitValue();
            return (Integer) result;
        } catch (Exception localException) {
            localException.printStackTrace();
            return -1;
        }
    }

}

