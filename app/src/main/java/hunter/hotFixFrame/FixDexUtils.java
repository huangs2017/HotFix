package hunter.hotFixFrame;

import android.content.Context;
import java.io.File;
import java.util.HashSet;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class FixDexUtils {

    // 存放需要修复的dex集合
    private static HashSet<File> loadDex = new HashSet<>();

    public static void loadFixedDex(Context ctx) {
        loadDex.clear();// 修复前先清空
        File fileDir = ctx.getDir("odex", ctx.MODE_PRIVATE); // 下载的dex文件目录
        File[] files = fileDir.listFiles();
        for (File file : files) {
            loadDex.add(file);
        }
        createDexClassLoader(ctx, fileDir);
    }

    // 创建类加载器
    private static void createDexClassLoader(Context ctx, File fileDir) {
        String optDir = fileDir.getAbsolutePath() + "/opt_dex";  // dex文件解压到的目录
        File fileOpt = new File(optDir);
        if (!fileOpt.exists()) {
            fileOpt.mkdirs();
        }

        DexClassLoader myClassLoader;
        PathClassLoader sysClassLoader; // 系统的类加载器
        for (File dex : loadDex) {
    //      DexClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent)
            myClassLoader = new DexClassLoader(dex.getAbsolutePath(), optDir, null, ctx.getClassLoader());
            sysClassLoader = (PathClassLoader) ctx.getClassLoader();
            hotFix(myClassLoader, sysClassLoader);
        }
    }

    // 热修复 （核心代码）
    private static void hotFix(DexClassLoader myClassLoader, PathClassLoader sysClassLoader) {
        try {
            // 拿自己的dexElements数组对象
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(myClassLoader));
            // 拿系统的dexElements数组对象
            Object sysDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(sysClassLoader));

            // 合并并插桩
            Object dexElements = ArrayUtils.combineArray(myDexElements, sysDexElements);

            // 赋值前拿到系统的pathList
            Object sysPathList = ReflectUtils.getPathList(sysClassLoader);
            // 给系统的pathList重新赋值
            ReflectUtils.setField(sysPathList, sysPathList.getClass(), dexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*
DexClassLoader：可以加载未安装的jar、apk、dex文件
PathClassLoader：只能加载已安裝（即/data/app目录下）的apk文件

两者都继承于BaseDexClassLoader类，
DexClassLoader的构造方法多了一个optimizedDirectory参数

DexClassLoader可以指定自己的optimizedDirectory，所以它可以加载外部的dex，因为这个dex会被复制到内部路径的optimizedDirectory；
PathClassLoader没有optimizedDirectory，所以它只能加载内部的dex，这些大都是存在系统中已经安装过的apk里面的。

*/
