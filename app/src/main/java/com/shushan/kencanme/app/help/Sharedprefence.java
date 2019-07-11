package com.shushan.kencanme.app.help;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by li.liu on 2017/6/14.
 */

public class Sharedprefence {
    public static final int STRING = 0;
    public static final int INT = 1;
    public static final int BOOLEAN = 2;
    private Context mContext;

    @Inject
    public Sharedprefence(Context context) {
        mContext = context;
    }


    public void ClearSharedPreferences(String dataBasesName) {
        SharedPreferences user = mContext.getSharedPreferences(dataBasesName, 0);
        SharedPreferences.Editor editor = user.edit();
        editor.clear();
        /**
         * commit与apply区别
         * 1.apply没有返回值而commit返回boolean表明修改是否提交成功
         * 2.apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘,
         * 而commit是同步的提交到硬件磁盘，因此，在多个并发的提交commit的时候，他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。
         * 3.apply方法不会提示任何失败的提示。
         * 由于在一个进程中，sharedPreference是单实例，一般不会出现并发冲突，如果对提交的结果不关心的话，建议使用apply，当然需要确保提交成功且有后续操作的话，还是需要用commit的。
         */
        editor.apply();
    }


    public void removeSharedPreferences(String dataBasesName, String key) {
        SharedPreferences user = mContext.getSharedPreferences(dataBasesName, 0);
        SharedPreferences.Editor editor = user.edit();
        editor.remove(key);
        editor.apply();
    }

    public SharedPreferences ReadSharedPreferences(String dataBasesName) {
        return mContext.getSharedPreferences(dataBasesName, 0);
    }

    public HashMap getAllByBasesName(String dataBasesName) {
        SharedPreferences user = mContext.getSharedPreferences(dataBasesName, 0);
        return (HashMap) user.getAll();
    }

    public <T> T getValueByName(String dataBasesName, String key, int type) {
        SharedPreferences user = mContext.getSharedPreferences(dataBasesName, 0);
        Object value = null;
        switch (type) {
            case 0:
                value = user.getString(key, "");
                break;
            case 1:
                value = user.getInt(key, 0);
                break;
            case 2:
                value = user.getBoolean(key, false);
        }

        return (T) value;
    }

    /**
     * 赋默认值
     */
    public <T> T getValueByName(String dataBasesName, String key, int type, Object defValue) {
        SharedPreferences user = mContext.getSharedPreferences(dataBasesName, 0);
        Object value = null;
        switch (type) {
            case 0:
                value = user.getString(key, (String) defValue);
                break;
            case 1:
                value = user.getInt(key, (Integer) defValue);
                break;
            case 2:
                value = user.getBoolean(key, (Boolean) defValue);
        }

        return (T) value;
    }

    public void WriteSharedPreferences(String dataBasesName, String name, Object value) {
        if (name != null && value != null) {
            SharedPreferences user = mContext.getSharedPreferences(dataBasesName, 0);
            SharedPreferences.Editor editor = user.edit();
            if (value instanceof Integer) {
                editor.putInt(name, Integer.parseInt(value.toString()));
            } else if (value instanceof Long) {
                editor.putLong(name, Long.parseLong(value.toString()));
            } else if (value instanceof Boolean) {
                editor.putBoolean(name, Boolean.parseBoolean(value.toString()));
            } else if (value instanceof String) {
                editor.putString(name, value.toString());
            } else if (value instanceof Float) {
                editor.putFloat(name, Float.parseFloat(value.toString()));
            }
            editor.apply();
        }
    }

    public void saveObject(String data, String key, Object object) {
        SharedPreferences preferences = mContext.getSharedPreferences(data, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream e = new ObjectOutputStream(baos);
            e.writeObject(object);
            String oAuth_Base64 = new String(Base64.encode(baos.toByteArray(), 0));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, oAuth_Base64);
            editor.apply();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

    }

    public <T> T readObject(String data, String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(data, 0);
        return readObject(preferences, key);
    }

    public <T> T readObject(SharedPreferences preferences, String key) {
        Object object = null;
        String string = preferences.getString(key, "");
        if (string.equals("")) {
            return null;
        } else {
            byte[] base64 = Base64.decode(string.getBytes(), 0);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);

            try {
                ObjectInputStream e = new ObjectInputStream(bais);
                object = e.readObject();
            } catch (Exception var7) {
                var7.printStackTrace();
            }

            return (T) object;
        }
    }

    public <T> HashMap<String, T> readObject(String data) {
        SharedPreferences preferences = mContext.getSharedPreferences(data, 0);
        return readObject(preferences);
    }

    public <T> HashMap<String, T> readObject(SharedPreferences preferences) {
        Object object = null;
        HashMap map = (HashMap) preferences.getAll();
        HashMap datas = new HashMap();

        for (Object o : map.keySet()) {
            String key = (String) o;
            byte[] base64 = Base64.decode(((String) map.get(key)).getBytes(), 0);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);

            try {
                ObjectInputStream e = new ObjectInputStream(bais);
                object = e.readObject();
                datas.put(key, object);
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }
        return datas;
    }
}
