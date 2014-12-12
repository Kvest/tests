package com.kvest.tests.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;

import static com.kvest.tests.provider.TestProviderContract.*;

/**
 * User: roman
 * Date: 12/1/14
 * Time: 5:35 PM
 */
public class TestProvider extends ContentProvider {
    private TestsSQLStorage sqlStorage;

    private static final int TEST_LIST_URI_INDICATOR = 1;
    private static final int TEST_ITEM_URI_INDICATOR = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, TESTS_PATH, TEST_LIST_URI_INDICATOR);
        uriMatcher.addURI(CONTENT_AUTHORITY, TESTS_PATH + "/#", TEST_ITEM_URI_INDICATOR);
    }

    @Override
    public boolean onCreate() {
        //create sql storage
        sqlStorage = new TestsSQLStorage(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        boolean hasSelection = !TextUtils.isEmpty(selection);

        switch (uriMatcher.match(uri)) {
            case TEST_LIST_URI_INDICATOR :
                return queryFullTable(Tables.Tests.TABLE_NAME , uri, projection, selection, selectionArgs, sortOrder);
            case TEST_ITEM_URI_INDICATOR :
                return queryFullTable(Tables.Tests.TABLE_NAME , uri, projection,
                        Tables.Tests.Columns._ID + "=" + uri.getLastPathSegment() + (hasSelection ? (" AND " + selection) : ""),
                        (hasSelection ? selectionArgs : null), sortOrder);
        }

        throw new IllegalArgumentException("Unknown uri for query : " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case TEST_LIST_URI_INDICATOR :
                return simpleInsert(Tables.Tests.TABLE_NAME, uri, values);
        }
        throw new IllegalArgumentException("Unknown uri for insert : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        boolean hasSelection = !TextUtils.isEmpty(selection);

        switch (uriMatcher.match(uri)) {
            case TEST_LIST_URI_INDICATOR :
                return simpleDelete(Tables.Tests.TABLE_NAME, uri, selection, selectionArgs);
            case TEST_ITEM_URI_INDICATOR :
                return simpleDelete(Tables.Tests.TABLE_NAME, uri,
                        Tables.Tests.Columns._ID + "=" + uri.getLastPathSegment() + (hasSelection ? (" AND " + selection) : ""),
                        (hasSelection ? selectionArgs : null));
        }

        throw new IllegalArgumentException("Unknown uri for delete : " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        boolean hasSelection = !TextUtils.isEmpty(selection);

        switch (uriMatcher.match(uri)) {
            case TEST_LIST_URI_INDICATOR :
                return simpleUpdate(Tables.Tests.TABLE_NAME, uri, values, selection, selectionArgs);
            case TEST_ITEM_URI_INDICATOR :
                return simpleUpdate( Tables.Tests.TABLE_NAME, uri, values,
                        Tables.Tests.Columns._ID + "=" + uri.getLastPathSegment() + (hasSelection ? (" AND " + selection) : ""),
                        (hasSelection ? selectionArgs : null));
        }

        throw new IllegalArgumentException("Unknown uri for update : " + uri);
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("TestProvider doesn't implement getType() method");
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        final SQLiteDatabase db = sqlStorage.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Helper method for selecting all data from table by name
     * @param tableName Name of the table in the database
     * @param uri The URI to query
     * @param projection The list of columns to put into the cursor. If null all columns are included
     * @param selection A selection criteria to apply when filtering rows. If null then all rows are included
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings
     * @param sortOrder How the rows in the cursor should be sorted. If null then the provider is free to define the sort order
     * @return Cursor to the data
     */
    protected Cursor queryFullTable(String tableName, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tableName);

        //make query
        SQLiteDatabase db = sqlStorage.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Helper method for inserting row into the table by the table name
     * @param tableName Name of the table in the database
     * @param uri The URI of the insertion request
     * @param values A set of column_name/value pairs to add to the database. This must not be null.
     * @return The URI for the newly inserted item
     */
    protected Uri simpleInsert(String tableName, Uri uri, ContentValues values) {
        SQLiteDatabase db = sqlStorage.getWritableDatabase();

        long rowId = db.insert(tableName, null, values);

        if (rowId > 0)
        {
            Uri resultUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(uri, null);


            return resultUri;
        }
        throw new SQLiteException("Faild to insert row into " + uri);
    }

    /**
     * Helper method for deleting rows from the table by the table name
     * @param tableName Name of the table in the database
     * @param uri The URI of the deleting request
     * @param selection An optional restriction to apply to rows when deleting.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings
     * @return The number of rows affected
     */
    protected int simpleDelete(String tableName, Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = sqlStorage.getWritableDatabase();

        int rowsDeleted = db.delete(tableName, selection, selectionArgs);

        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    /**
     * Helper method for updating rows from the table by the table name
     * @param tableName Name of the table in the database
     * @param uri The URI to query
     * @param values A set of column_name/value pairs to update in the database. This must not be null
     * @param selection An optional filter to match rows to update
     * @param selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings
     * @return The number of rows affected
     */
    protected int simpleUpdate(String tableName, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = sqlStorage.getWritableDatabase();
        int rowsUpdated = db.update(tableName, values, selection, selectionArgs);

        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
