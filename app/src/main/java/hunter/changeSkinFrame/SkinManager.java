package hunter.changeSkinFrame;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import hunter.changeSkinFrame.attr.SkinAttrSupport;
import hunter.changeSkinFrame.attr.SkinView;

public class SkinManager {

    private Context mContext;
    private ResourceManager mResourceManager;
    private boolean usePlugin;
    private String mSuffix = "";
    private List<Activity> activityList = new ArrayList<>();


    private SkinManager() { }

    private static class SingletonHolder {
        static SkinManager sInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SingletonHolder.sInstance;
    }

    // 初始化---------------------------------------------------------------------------------------------------
    public void init(Context context) {
        mContext = context.getApplicationContext();
        String skinPkgPath = SPUtil.getSkinPkgPath(mContext);
        String skinPkgName = SPUtil.getSkinPkgName(mContext);
        mSuffix = SPUtil.getSkinSuffix(mContext);
        if (validPluginParams(skinPkgPath, skinPkgName))
            loadPlugin(skinPkgPath, skinPkgName, mSuffix);
    }

    private boolean validPluginParams(String skinPkgPath, String skinPkgName) {
        if (TextUtils.isEmpty(skinPkgPath) || TextUtils.isEmpty(skinPkgName))
            return false;
        File file = new File(skinPkgPath);
        if (!file.exists())
            return false;
        return true;
    }

    private void loadPlugin(String skinPkgPath, String skinPkgName, String suffix) {
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath); // android 5.0以下有效
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = mContext.getResources();
        Resources res = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mResourceManager = new ResourceManager(res, skinPkgName, suffix);
        usePlugin = true;
    }
    // 初始化---------------------------------------------------------------------------------------------------

    public void register(final Activity activity) {
        activityList.add(activity);
        activity.findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                ViewGroup content = activity.findViewById(android.R.id.content);
                injectSkin(content);
            }
        });
    }

    public void injectSkin(View view) {
        List<SkinView> skinViews = new ArrayList<>();
        SkinAttrSupport.addSkinViews(view, skinViews); //--------------
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }

    public void unregister(Activity activity) {
        activityList.remove(activity);
    }

    public ResourceManager getResourceManager() {
        if (!usePlugin) {
            mResourceManager = new ResourceManager(mContext.getResources(), mContext.getPackageName(), mSuffix);
        }
        return mResourceManager;
    }

    // 应用内换肤，传入资源区别的后缀
    public void changeSkin(String suffix) {
        usePlugin = false;
        mSuffix = suffix;
        SPUtil.clear(mContext);
        SPUtil.putSkinSuffix(mContext, suffix);
        notifyChangedListeners();
    }

    public void removeAnySkin() {
        usePlugin = false;
        mSuffix = null;
        SPUtil.clear(mContext);
        notifyChangedListeners();
    }

    // 插件皮肤
    public void changeSkin(final String skinPkgPath, final String skinPkgName) {
        if (!validPluginParams(skinPkgPath, skinPkgName)) {
            Toast.makeText(mContext, "换肤失败", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                loadPlugin(skinPkgPath, skinPkgName, "");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                mSuffix = "";
                SPUtil.putSkinSuffix(mContext, "");
                SPUtil.putSkinPkgPath(mContext, skinPkgPath);
                SPUtil.putSkinPkgName(mContext, skinPkgName);
                notifyChangedListeners();
                Toast.makeText(mContext, "换肤成功", Toast.LENGTH_SHORT).show();

            }
        }.execute();
    }

    public void notifyChangedListeners() {
        for (Activity activity : activityList) {
            ViewGroup content = activity.findViewById(android.R.id.content);
            injectSkin(content);
        }
    }

}