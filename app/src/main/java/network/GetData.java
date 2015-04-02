package network;

import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.glassx.wear.installer.AppConfig;
import cn.glassx.wear.installer.InstallManager;
import cn.glassx.wear.installer.PackageInstaller;
import model.APKMessage;

/**
 * Created by Fanz on 3/31/15.
 *
 */
public class GetData  {

    /**
     * 从网络获取安装列表
     *
     * */
    public static void getTaskList(String authToken) {
        HttpUtils http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("authToken",authToken);
        http.send(HttpRequest.HttpMethod.POST,
                AppConfig.URL_GET_INSTALL_LIST,
                params,
                new RequestCallBack<String>(){
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        Log.d("GlassInstaller","获取列表内容当前"+current+"总共"+total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("GlassInstaller","获取列表内容成功："+responseInfo.result);
                        if(InstallManager.apkMessages != null){
                            //本地任务正在执行，请求将会在本次任务执行完毕在执行
                            InstallManager.restartWhenTaskEnd = true;
                            return;
                        }
                        InstallManager.apkMessages = new ArrayList<APKMessage>();
                        try {
                            JSONArray ja = new JSONArray(responseInfo.result);
                            for(int i =0;i<ja.length();i++){
                                JSONObject jo = ja.getJSONObject(i);
                                APKMessage apkMessage = new APKMessage();
                                apkMessage.setOperation(jo.getString("operation"));
                                apkMessage.setPackageName(jo.getString("packageName"));
                                apkMessage.setRemoteURL(jo.getString("remoteURL"));
                                apkMessage.setApkLabel(jo.getString("apkLabel"));
                                apkMessage.setVersion(jo.getInt("version"));
                                InstallManager.apkMessages.add(apkMessage);
                            }
                            Log.d("GlassInstaller",ja.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //获取到列表、开始执行任务
                        InstallManager.nextTask();
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();
                    }
                });
    }

    /**
     * 从网络获取安装包
     * @param apkMessage 必须有属性安装包地址remoteURL和安装包名字标签apkLabel
     * */
    public static void getPackage(APKMessage apkMessage) {
        HttpUtils http = new HttpUtils();
        HttpHandler handler = http.download(apkMessage.getRemoteURL(),
                AppConfig.URL_LOCAL_DOWNLOAD+apkMessage.getApkLabel()+".apk",
                false, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        Log.d("GlassInstaller","conn...");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        Log.d("GlassInstaller", "GET请求获取内容当前" + current + "总共" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        Log.d("GlassInstaller", "downloaded:" + responseInfo.result.getPath());
                        //下载成功，执行安装
                        int result = PackageInstaller.install(responseInfo.result.getPath());
                        if(result == -1){
                            Log.d("GlassInstaller","安装失败："+responseInfo.result.getPath());
                            Toast.makeText(InstallManager.context,"安装失败"+responseInfo.result.getPath(),Toast.LENGTH_SHORT).show();
                            File file =new File(responseInfo.result.getPath());
                            file.delete();
                        }else{
                            Toast.makeText(InstallManager.context,"安装成功,删除安装包"+responseInfo.result.getPath(),Toast.LENGTH_SHORT).show();
                            File file =new File(responseInfo.result.getPath());
                            file.delete();
                            //执行安装下一个
                            InstallManager.nextTask();
                        }
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("GlassInstaller","下载出错,继续执行下一个任务:"+msg);
                        Toast.makeText(InstallManager.context,"下载失败",Toast.LENGTH_SHORT).show();
                        //下载失败，跳过本次下载，下载下一个
                        InstallManager.nextTask();
                        error.printStackTrace();
                    }
                });
    }



}
