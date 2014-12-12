package com.kvest.tests.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.kvest.tests.provider.TestProviderContract.Tables.*;

/**
 * User: roman
 * Date: 12/1/14
 * Time: 5:33 PM
 */
public class TestsSQLStorage extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tests.db";

    private static final int DATABASE_VERSION_V1 = 101;  // 1.0
    private static final int DATABASE_VERSION = DATABASE_VERSION_V1;

    public TestsSQLStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tests.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing to do
    }
}
