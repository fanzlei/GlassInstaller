package model;

/**
 * Created by Fanz on 3/31/15.
 * APK安装包信息类
 */
public class APKMessage {

    private String packageName;
    private String remoteURL;
    private String localURL;
    private String apkLabel;
    private double apkSize;
    private int versionCode;

    public int getVersion() {
        return versionCode;
    }

    public void setVersion(int versionCode) {
        this.versionCode = versionCode;
    }

    private String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public APKMessage(){}

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRemoteURL() {
        return remoteURL;
    }

    public void setRemoteURL(String remoteURL) {
        this.remoteURL = remoteURL;
    }

    public String getLocalURL() {
        return localURL;
    }

    public void setLocalURL(String localURL) {
        this.localURL = localURL;
    }

    public String getApkLabel() {
        return apkLabel;
    }

    public void setApkLabel(String apkLabel) {
        this.apkLabel = apkLabel;
    }

    public double getApkSize() {
        return apkSize;
    }

    public void setApkSize(double apkSize) {
        this.apkSize = apkSize;
    }
}
