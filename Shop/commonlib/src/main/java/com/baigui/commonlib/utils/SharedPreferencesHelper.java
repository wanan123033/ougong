package com.baigui.commonlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.SharedPreferencesCompat.EditorCompat;
import android.support.v4.util.ArrayMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class SharedPreferencesHelper {
    private static final String SEPARATOR = "#";
    private static final String TAG = SharedPreferencesHelper.class.getName();

    public SharedPreferencesHelper() {
    }

    public static <T> SharedPreferences getSharedPreferences(Context context, Class<T> clx) {
        return context.getSharedPreferences(clx.getName(), 0);
    }

    public static <T> boolean save(Context context, T t) {
        Editor editor = buildNewEditor(context, t);
        return editor.commit();
    }

    public static <T> void saveAsync(Context context, T t) {
        Editor editor = buildNewEditor(context, t);
        EditorCompat.getInstance().apply(editor);
    }

    private static <T> Editor buildNewEditor(Context context, T t) {
        Class<?> clx = t.getClass();
        remove(context, clx);
        Map<String, SharedPreferencesHelper.Data> map = new ArrayMap();
        buildValuesToMap(clx, t, "", map);
        SharedPreferences sp = getSharedPreferences(context, clx);
        Editor editor = sp.edit();
        Set<String> existKeys = sp.getAll().keySet();
        Set<String> keys = map.keySet();
        Iterator var8 = keys.iterator();

        while(var8.hasNext()) {
            String key = (String)var8.next();
            SharedPreferencesHelper.Data data = (SharedPreferencesHelper.Data)map.get(key);
            Class<?> type = data.type;
            Object value = data.value;

            try {
                if (value == null) {
                    removeKeyFamily(editor, existKeys, key);
                } else if (!type.equals(Byte.class) && !type.equals(Byte.TYPE)) {
                    if (!type.equals(Short.class) && !type.equals(Short.TYPE)) {
                        if (!type.equals(Integer.class) && !type.equals(Integer.TYPE)) {
                            if (!type.equals(Long.class) && !type.equals(Long.TYPE)) {
                                if (!type.equals(Float.class) && !type.equals(Float.TYPE)) {
                                    if (!type.equals(Double.class) && !type.equals(Double.TYPE)) {
                                        if (!type.equals(Boolean.class) && !type.equals(Boolean.TYPE)) {
                                            if (!type.equals(Character.class) && !type.equals(Character.TYPE)) {
                                                if (type.equals(String.class)) {
                                                    editor.putString(key, value.toString());
                                                } else {
  //                                                  Logger.d(TAG, String.format("Con't support save this type:%s, value:%s, key:%s", type, value, key));
                                                }
                                            } else {
                                                editor.putString(key, value.toString());
                                            }
                                        } else {
                                            editor.putBoolean(key, (Boolean)value);
                                        }
                                    } else {
                                        editor.putString(key, String.valueOf(value));
                                    }
                                } else {
                                    editor.putFloat(key, (Float)value);
                                }
                            } else {
                                editor.putLong(key, (Long)value);
                            }
                        } else {
                            editor.putInt(key, (Integer)value);
                        }
                    } else {
                        editor.putInt(key, (Short)value);
                    }
                } else {
                    editor.putInt(key, (Byte)value);
                }
            } catch (IllegalArgumentException var14) {
//                Logger.d(TAG, "Save error:" + var14.getMessage());
            }
        }

        return editor;
    }

    private static boolean saveEditor(Editor editor, boolean isAsync) {
        if (isAsync) {
            EditorCompat.getInstance().apply(editor);
            return true;
        } else {
            return editor.commit();
        }
    }

    public static <T> T load(Context context, Class<T> clx) {
        SharedPreferences sp = getSharedPreferences(context, clx);
        Set<String> existKeys = sp.getAll().keySet();
        return existKeys.size() == 0 ? null : buildTargetFromSource(clx, null, "", existKeys, sp);
    }

    public static <T> T loadFormSource(Context context, Class<T> clx) {
        SharedPreferences sp = getSharedPreferences(context, clx);
        try {
            Class<?> type = sp.getClass();
            String methodName = "startReloadIfChangedUnexpectedly";
            Method method = null;

            try {
                method = type.getMethod(methodName);
            } catch (NoSuchMethodException var9) {
                do {
                    try {
                        method = type.getDeclaredMethod(methodName);
                    } catch (NoSuchMethodException var8) {
                        ;
                    }

                    type = type.getSuperclass();
                } while(type != null && method == null);

                if (method == null) {
                    throw new NoSuchMethodException();
                }
            }

            method.setAccessible(true);
            method.invoke(sp);
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        Set<String> existKeys = sp.getAll().keySet();
        return existKeys.size() == 0 ? null : buildTargetFromSource(clx, null, "", existKeys, sp);
    }

    public static <T> void remove(Context context, Class<T> clx) {
        SharedPreferences sp = getSharedPreferences(context, clx);
        Editor editor = sp.edit();
        editor.clear();
        EditorCompat.getInstance().apply(editor);
    }

    private static <T> void buildValuesToMap(Class<?> clx, T t, String preFix, Map<String, SharedPreferencesHelper.Data> map) {
        if (clx != null && !clx.equals(Object.class) && t != null) {
            Field[] fields = clx.getDeclaredFields();
            if (fields != null && fields.length != 0) {
                Field[] var5 = fields;
                int var6 = fields.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    Field field = var5[var7];
                    if (!isContSupport(field)) {
                        String fieldName = field.getName();
                        Class<?> fieldType = field.getType();
                        boolean isAccessible = field.isAccessible();
                        if (!isAccessible) {
                            field.setAccessible(true);
                        }

                        Object value;
                        try {
                            value = field.get(t);
                        } catch (IllegalAccessException | IllegalArgumentException var14) {
//                            Logger.d(TAG, "buildValuesToMap error:" + var14.getMessage());
                            continue;
                        }

                        if (isBasicType(fieldType)) {
                            String key = preFix + fieldName;
                            if (!map.containsKey(key)) {
                                map.put(key, new SharedPreferencesHelper.Data(fieldType, value));
                            }
                        } else {
                            buildValuesToMap(fieldType, value, preFix + fieldName + "#", map);
                        }
                    }
                }

                buildValuesToMap(clx.getSuperclass(), t, preFix, map);
            }
        }
    }

    private static <T> T buildTargetFromSource(Class<T> clx, T target, String preFix, Set<String> existKeys, SharedPreferences sp) {
        if (clx != null && !clx.equals(Object.class)) {
            if (target == null) {
                try {
                    target = clx.newInstance();
                } catch (InstantiationException var17) {
                    var17.printStackTrace();
                    return null;
                } catch (IllegalAccessException var18) {
                    var18.printStackTrace();
                    return null;
                }
            }

            Field[] fields = clx.getDeclaredFields();
            if (fields != null && fields.length != 0) {
                Field[] var6 = fields;
                int var7 = fields.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    Field field = var6[var8];
                    if (!isContSupport(field)) {
                        String fieldName = field.getName();
                        Class<?> fieldType = field.getType();
                        boolean isAccessible = field.isAccessible();
                        if (!isAccessible) {
                            field.setAccessible(true);
                        }

                        String key = preFix + fieldName;
                        Object value = null;
                        if (isBasicType(fieldType)) {
                            if (existKeys.contains(key)) {
                                if (!fieldType.equals(Byte.class) && !fieldType.equals(Byte.TYPE)) {
                                    if (!fieldType.equals(Short.class) && !fieldType.equals(Short.TYPE)) {
                                        if (!fieldType.equals(Integer.class) && !fieldType.equals(Integer.TYPE)) {
                                            if (!fieldType.equals(Long.class) && !fieldType.equals(Long.TYPE)) {
                                                if (!fieldType.equals(Float.class) && !fieldType.equals(Float.TYPE)) {
                                                    if (!fieldType.equals(Double.class) && !fieldType.equals(Double.TYPE)) {
                                                        if (!fieldType.equals(Boolean.class) && !fieldType.equals(Boolean.TYPE)) {
                                                            if (!fieldType.equals(Character.class) && !fieldType.equals(Character.TYPE)) {
                                                                if (fieldType.equals(String.class)) {
                                                                    value = sp.getString(key, "");
                                                                }
                                                            } else {
                                                                value = sp.getString(key, "").charAt(0);
                                                            }
                                                        } else {
                                                            value = sp.getBoolean(key, false);
                                                        }
                                                    } else {
                                                        value = Double.valueOf(sp.getString(key, "0.00"));
                                                    }
                                                } else {
                                                    value = sp.getFloat(key, 0.0F);
                                                }
                                            } else {
                                                value = sp.getLong(key, 0L);
                                            }
                                        } else {
                                            value = sp.getInt(key, 0);
                                        }
                                    } else {
                                        value = (short)sp.getInt(key, 0);
                                    }
                                } else {
                                    value = (byte)sp.getInt(key, 0);
                                }
                            }
                        } else {
                            value = buildTargetFromSource(fieldType, null, preFix + fieldName + "#", existKeys, sp);
                        }

                        if (value != null) {
                            try {
                                field.set(target, value);
                            } catch (IllegalAccessException var16) {
                                var16.printStackTrace();
                                //Logger.d(TAG, String.format("Set field error, Key:%s, type:%s, value:%s", key, fieldType, value));
                            }
                        } else {
                        }
                    }
                }

                return (T) buildTargetFromSource(clx.getSuperclass(), target, preFix, existKeys, sp);
            } else {
                return target;
            }
        } else {
            return target;
        }
    }

    private static void removeKeyFamily(Editor editor, Set<String> existKeys, String removeKey) {
        String preFix = removeKey + "#";
        Iterator var4 = existKeys.iterator();

        while(true) {
            String str;
            do {
                if (!var4.hasNext()) {
                    return;
                }

                str = (String)var4.next();
            } while(!str.equals(removeKey) && !str.startsWith(preFix));

            editor.remove(str);
        }
    }

    private static boolean isContSupport(Field field) {
        return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isAbstract(field.getModifiers());
    }

    private static boolean isBasicType(Class<?> clx) {
        return clx.equals(Byte.class) || clx.equals(Byte.TYPE) || clx.equals(Short.class) || clx.equals(Short.TYPE) || clx.equals(Integer.class) || clx.equals(Integer.TYPE) || clx.equals(Long.class) || clx.equals(Long.TYPE) || clx.equals(Float.class) || clx.equals(Float.TYPE) || clx.equals(Double.class) || clx.equals(Double.TYPE) || clx.equals(Boolean.class) || clx.equals(Boolean.TYPE) || clx.equals(Character.class) || clx.equals(Character.TYPE) || clx.equals(String.class);
    }

    private static class Data {
        Class<?> type;
        Object value;

        Data(Class<?> type, Object value) {
            this.type = type;
            this.value = value;
        }
    }
}
