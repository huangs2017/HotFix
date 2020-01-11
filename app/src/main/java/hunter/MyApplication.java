package hunter;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import androidx.multidex.MultiDex;
import java.io.File;
import hunter.changeSkinFrame.SkinManager;
import hunter.hotFixFrame.FixDexUtils;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);         // 开启dex分包
        FixDexUtils.loadFixedDex(this); // 热修复初始化
    }


    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this); // 换肤初始化
    }

    public static String skinPkgPath = Environment.getExternalStorageDirectory() + File.separator + "skin_plugin.apk";
    public static String skinPkgName = "com.imooc.skin_plugin";
}
