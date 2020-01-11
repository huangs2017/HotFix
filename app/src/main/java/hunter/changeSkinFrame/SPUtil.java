package hunter.changeSkinFrame;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {

    private static SharedPreferences sp;

    private static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("ChangeSkinSP", Context.MODE_PRIVATE);
        }
    }

    public static void putSkinSuffix(Context context, String suffix) {
        init(context);
        sp.edit().putString("skin_suffix", suffix).apply();
    }
    public static String getSkinSuffix(Context context) {
        init(context);
        return sp.getString("skin_suffix", "");
    }

    public static void putSkinPkgPath(Context context, String path) {
        init(context);
        sp.edit().putString("skin_pkg_path", path).apply();
    }
    public static String getSkinPkgPath(Context context) {
        init(context);
        return sp.getString("skin_pkg_path", "");
    }

    public static void putSkinPkgName(Context context, String pkgName) {
        init(context);
        sp.edit().putString("skin_pkg_name", pkgName).apply();
    }
    public static String getSkinPkgName(Context context) {
        init(context);
        return sp.getString("skin_pkg_name", "");
    }

    public static boolean clear(Context context) {
        init(context);
        return sp.edit().clear().commit();
    }

}
