package authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import cn.glassx.wear.installer.AppConfig;

/**
 * Created by Fanz on 4/9/15.
 */
public class TokenUtils {
    private static AccountManager mAccountManager = null;

    /**
     * 获取指定账户的AuthToken，若Token过期则通过refreshToken重新获取AuthToken
     * @param
     * @return 返回String类型的AuthToken，错误则返回null
     * */
    public static String getAuthToken(Account account, Context context){
        if(mAccountManager == null){
            mAccountManager = AccountManager.get(context);
        }
        Bundle bundle = null;
        try {
            bundle = mAccountManager.getAuthToken(account, AppConfig.AUTHTOKEN_TYPE_FULL_ACCESS, null, true, null, null).getResult();

            if (bundle != null) {
                String access_token = bundle.getString(AccountManager.KEY_AUTHTOKEN);

                long expiresAt = Long.parseLong(mAccountManager.getUserData(account, AppConfig.EXPIRES_AT));

                Date expires = new Date(expiresAt);
                Calendar rightNow = Calendar.getInstance();

                if (rightNow.getTime().after(expires)) {
                    Log.e("access_token超时", String.valueOf(expiresAt).toString());
                    mAccountManager.invalidateAuthToken(AppConfig.ACCOUNT_TYPE, access_token);
                    bundle = mAccountManager.getAuthToken(account, AppConfig.AUTHTOKEN_TYPE_FULL_ACCESS, null, true, null, null).getResult();
                    if (bundle != null) {
                        access_token = bundle.getString(AccountManager.KEY_AUTHTOKEN);

                    }
                }
                return access_token;
            }
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
