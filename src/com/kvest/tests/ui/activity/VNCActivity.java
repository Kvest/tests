package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.kvest.tests.R;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Kvest on 23.02.2015.
 */
public class VNCActivity extends Activity {
    private static final String TAG = "KVEST_TAG";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VNCActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vnc_activity);

        findViewById(R.id.get_screenshot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new PullScreenAsyncTask().execute();
                testId();
            }
        });
    }

    class PullScreenAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            File ss = new File(Environment.getExternalStorageDirectory(), "img.png");
//            String cmd = "/system/bin/cat /dev/graphics/fb0 > "+ ss.getAbsolutePath();
            String cmd = "/system/bin/screencap -p " + ss.getAbsolutePath();

            try {
                Process sh = Runtime.getRuntime().exec("su", null, null);
                OutputStream os = sh.getOutputStream();
                os.write(cmd.getBytes("ASCII"));
                os.flush();
                os.close();
                sh.waitFor();
            } catch (IOException ioExceptin) {
                Log.d(TAG, ioExceptin.getLocalizedMessage());
            } catch (InterruptedException e) {
                Log.d(TAG, e.getLocalizedMessage());
            }


            return null;
        }

        private void runSuShellCommand(String cmd) {
            Runtime runtime = Runtime.getRuntime();
            Process proc = null;
            OutputStreamWriter osw = null;
            StringBuilder sbstdOut = new StringBuilder();
            StringBuilder sbstdErr = new StringBuilder();

            try { // Run Script
                proc = runtime.exec("su");
                osw = new OutputStreamWriter(proc.getOutputStream());
                osw.write(cmd);
                osw.flush();
                osw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (osw != null) {
                    try {
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                if (proc != null)
                    proc.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sbstdOut.append(readBufferedReader(new InputStreamReader(proc.getInputStream())));
            sbstdErr.append(readBufferedReader(new InputStreamReader(proc.getErrorStream())));
        }

        private String readBufferedReader(InputStreamReader input) {

            BufferedReader reader = new BufferedReader(input);
            StringBuilder found = new StringBuilder();
            String currLine = null;
            String sep = System.getProperty("line.separator");
            try {
                // Read it all in, line by line.
                while ((currLine = reader.readLine()) != null) {
                    found.append(currLine);
                    found.append(sep);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "---------------------START--------------------");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i(TAG, "---------------------DONE--------------------");
        }
    }

    private void testId() {
        Log.d(TAG, "KEYCODE_HOME=" + KeyEvent.KEYCODE_HOME);
        Cursor cursor = getContentResolver().query(Uri.parse("content://settings/secure"), null, null, null, null);
        try {
            printCursor(cursor);
        } finally {
            cursor.close();
        }

        cursor = getContentResolver().query(Uri.parse("content://settings/system"), null, null, null, null);
        try {
            printCursor(cursor);
        } finally {
            cursor.close();
        }
//
//        ContentValues cv = new ContentValues(1);
//        cv.put("value", "19db9df349a536a6");
//        getContentResolver().update(Uri.parse("content://settings/secure"), cv, "name=?", new String[]{"android_id"});
//
//        cursor = getContentResolver().query(Uri.parse("content://settings/secure"), null, "name=?", new String[]{"android_id"}, null);
//        try {
//            printCursor(cursor);
//        } finally {
//            cursor.close();
//        }

        //Android id
        String deviceId = Settings.System.getString(getContentResolver(),Settings.System.ANDROID_ID);
        Log.d(TAG, "deviceId=" + deviceId);

        deviceId = Settings.Secure.getString(getContentResolver(),Settings.System.ANDROID_ID);
        Log.d(TAG, "SecuredeviceId=" + deviceId);

        //IMEI
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tManager.getDeviceId();
        Log.d(TAG, "IMEI=" + deviceId);

//        private static ITelephonyRegistry sRegistry;
        try {
            Field f = tManager.getClass().getDeclaredField("sRegistry");
            f.setAccessible(true);
            Object obj = f.get(tManager); //IllegalAccessException
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          Log.d(TAG, "obj=" + obj.getClass().getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Class clazz = getBaseContext().getClass();


//        Log.d(TAG, "for " + clazz.getName());
//        Class superClazz = clazz.getSuperclass();
//        do {
//            Log.d(TAG, superClazz.getName() + "->");
//        } while ((superClazz = clazz.getSuperclass())!= null);
//        Field[] fields = clazz.getFields();
//        for (Field field : fields) {
//            Log.d(TAG, field.toString());
//        }
//        Method[] methods = clazz.getMethods();
//        for (Method method : methods) {
//            Log.d(TAG, method.toString());
//        }

        //Device serial id
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            deviceId = (String) get.invoke(c, "ro.serialno");
            Log.d(TAG, "deviceId=" + deviceId);
        } catch (Exception ignored) { }

        Method[] mthds = tManager.getClass().getMethods();
        for (Method method : mthds) {
            if (method.getName().equals("getDeviceId")) {
                //method.isBridge()
            }
        }
    }

    private void printCursor(Cursor cursor) {
        Log.d(TAG, cursor.getCount() + " ===========================================");
        String s = "";
        for (int i = 0; i < cursor.getColumnCount(); ++i) {
            s += cursor.getColumnName(i) + "\t";
        }
        Log.d(TAG, s);
        Log.d(TAG, "===========================================");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            s = "";
            for (int i = 0; i < cursor.getColumnCount(); ++i) {
                s += cursor.getString(i) + "\t";
            }

            Log.d(TAG, s);
            cursor.moveToNext();
        }
        Log.d(TAG, "===========================================");
    }
}
