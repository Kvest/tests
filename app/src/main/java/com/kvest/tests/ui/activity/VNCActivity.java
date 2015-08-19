package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.kvest.tests.R;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;

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

        ((TextView)findViewById(R.id.log)).setText(getDeviceInfo());

        findViewById(R.id.get_screenshot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new PullScreenAsyncTask().execute();
                testId();
            }
        });
    }

    private String getDeviceInfo() {
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();

//        public static final GservicesValue<Long> androidId = GservicesValue.value("android_id", Long.valueOf(0L));
//        localProperties.androidId = ((Long)G.androidId.get()).longValue();

        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        sb.append("id=").append(id).append("==").append("\n");

        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        sb.append("MacAddress=").append(address).append("\n");

        String serial = null;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception ignored) {
        }
        sb.append("serial number=").append(Build.SERIAL).append("\n");
        sb.append("serial=").append(serial).append("\n");


        sb.append("deviceName=").append(Build.DEVICE).append("\n");
        sb.append("productName=").append(Build.PRODUCT).append("\n");
        sb.append("modelName=").append(Build.MODEL).append("\n");
        sb.append("manufacturer=").append(Build.MANUFACTURER).append("\n");
        sb.append("buildFingerprint=").append(Build.FINGERPRINT).append("\n");
        sb.append("osVersion=").append(Build.VERSION.RELEASE).append("\n");
        sb.append("androidBuildBrand=").append(Build.BRAND).append("\n");
        sb.append("gmtOffsetMillis=").append(TimeZone.getDefault().getRawOffset()).append("\n");

        boolean devModeOn = (1 == Settings.Secure.getInt(getContentResolver(), "adb_enabled", 0));
        sb.append("devModeOn=").append(devModeOn).append("\n");
        boolean nonPlayInstallAllowed = (1 != Settings.Secure.getInt(getContentResolver(), "install_non_market_apps", 0));
        sb.append("nonPlayInstallAllowed=").append(nonPlayInstallAllowed).append("\n");

        Locale localLocale = getResources().getConfiguration().locale;
        sb.append("language=").append(localLocale.getISO3Language()).append("\n");
        sb.append("locale=").append(localLocale.toString()).append("\n");

        String str1 = tManager.getNetworkOperator();
        sb.append("cellOperator=").append(TextUtils.isEmpty(str1) ? "empty" : str1).append("\n");

        String str2 = tManager.getSimOperator();
        sb.append("simOperator=").append(TextUtils.isEmpty(str2) ? "empty" : str2).append("\n");

        str1 = tManager.getDeviceId();
        sb.append("esn=meid=imei=").append(TextUtils.isEmpty(str1) ? "empty" : str1).append("\n");

        str2 = tManager.getLine1Number();
        if (!TextUtils.isEmpty(str2)) {
            sb.append("phoneNumber=").append(str2).append("\n");
        } else {
            sb.append("phoneNumber is empty\n");
        };

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        sb.append("location=").append(location == null ? "null" : location.toString()).append("\n");

        ArrayList<InetAddress> localArrayList = getNonLoopbackInetAddresses();
        sb.append("ipAddr=");
        for (int i = 0; i < localArrayList.size(); ++i) {
            sb.append("\t").append(i).append(")").append(localArrayList.get(i).getHostAddress()).append("\n");
        }

        return sb.toString();
    }

    public static ArrayList<InetAddress> getNonLoopbackInetAddresses()
    {
        ArrayList localArrayList = new ArrayList();
        try
        {
            Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
            while (localEnumeration1.hasMoreElements())
            {
                Enumeration localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
                while (localEnumeration2.hasMoreElements())
                {
                    InetAddress localInetAddress = (InetAddress)localEnumeration2.nextElement();
                    if (localInetAddress.isLoopbackAddress())
                        continue;
                    localArrayList.add(localInetAddress);
                }
            }
        }
        catch (SocketException localSocketException)
        {
            Log.e("NetUtils", "Unable to retrieve network interfaces", localSocketException);
        }
        return localArrayList;
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
