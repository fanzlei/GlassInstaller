package cn.glassx.wear.installer;

/**
 * Created by Fanz on 3/30/15.
 */
public class AppConfig {

    /**应用安装、卸载操作广播的BroadCastReceiver的action属性*/
    public static final String RECEIVER_ACTION = "cn.wear.glassx.apkActionReceiver";

    /**APK操作类型*/
    public static final String ACTION = "action";
    /**安装应用操作*/
    public static final String ACTION_INSTALL = "install";
    /**卸载应用操作*/
    public static final String ACTION_UNINSTALL = "unInstall";

    public static final String APK_PATH= "apkPath";
    public static final String PACKAGE_NAME = "packageName";

    /**账户同步的authority*/
    public static final String SYNC_ADAPTER_AUTHORITY = "cn.glassx.wear.install";
    /**
     * 账户类型
     */
    public static final String ACCOUNT_TYPE = "cn.glassx.wear";
    /**请求同步的AuthToken，从XAPI中获得*/
    public static String authToken = null;

    /**获取安装列表的服务器地址*/
    public static final String URL_GET_INSTALL_LIST = "http://172.16.10.74:8080/GlassInstallerTest/TestData";
    /**本地下载路径*/
    public static final String URL_LOCAL_DOWNLOAD = "/sdcard/Download/";

}
