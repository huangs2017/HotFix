package hunter.changeSkinFrame;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class ResourceManager {

    private Resources mResources;
    private String mPackageName;
    private String mSuffix;

    public ResourceManager(Resources res, String pluginPackageName, String suffix) {
        mResources = res;
        mPackageName = pluginPackageName;
        mSuffix = suffix;
    }

    public Drawable getDrawable(String name) {
        name = appendSuffix(name);
        return mResources.getDrawable(mResources.getIdentifier(name, "drawable", mPackageName));
    }

    public int getColor(String name) {
        name = appendSuffix(name);
        return mResources.getColor(mResources.getIdentifier(name, "color", mPackageName));
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix))
            return name += "_" + mSuffix;
        return name;
    }

}
