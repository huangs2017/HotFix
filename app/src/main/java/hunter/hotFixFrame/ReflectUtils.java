package hunter.hotFixFrame;

import java.lang.reflect.Field;

public class ReflectUtils {

    /*
     * 通过反射获取某对象
     * @param obj       该属性所属类的对象
     * @param clazz     该属性所属类
     * @param fieldName 属性名
     * @return          该属性对象
     */
    private static Object getField(Object obj, Class<?> clazz, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /*
     * 给某属性赋值
     * @param obj   该属性所属类的对象
     * @param clazz 该属性所属类
     * @param value 值
     */
    public static void setField(Object obj, Class<?> clazz, Object value) throws Exception  {
        Field field = clazz.getDeclaredField("dexElements");
        field.setAccessible(true);
        field.set(obj, value);
    }


    /*
     * 通过反射获取BaseDexCLassLoader对象中的PathList对象
     * @param baseDexClassLoader BaseDexClassLoader对象
     * @return PathList对象
     */
    public static Object getPathList(Object baseDexClassLoader) throws Exception {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    /*
     * 通过反射获取BaseDexClassLoader对象中的PathList对象， 再获取dexELements对象
     * @param paramObject PathList对象
     * @return dexELements对象
     */
    public static Object getDexElements(Object pathList) throws Exception {
        return getField(pathList, pathList.getClass(), "dexElements");
    }

}