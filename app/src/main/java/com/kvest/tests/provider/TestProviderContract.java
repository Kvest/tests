package com.kvest.tests.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * User: roman
 * Date: 12/1/14
 * Time: 5:17 PM
 */
public class TestProviderContract {
    public static final String CONTENT_AUTHORITY = "com.kvest.tests";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String TESTS_PATH = "tests";

    public static final Uri TESTS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TESTS_PATH);

    public interface Tables {
        interface Tests {
            String TABLE_NAME = "tests";

            interface Columns extends BaseColumns {
                String NAME = "name";
                String TYPE = "type";
                String DESCRIPTION = "description";
            }

            String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
                    + Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Columns.NAME + " TEXT,"
                    + Columns.TYPE + " INTEGER,"
                    + Columns.DESCRIPTION + " TEXT);";

            String DROP_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
        }
    }
}
