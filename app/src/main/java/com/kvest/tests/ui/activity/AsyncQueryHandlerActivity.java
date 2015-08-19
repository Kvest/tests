package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.kvest.tests.R;
import com.kvest.tests.ui.adapter.TestAdapter;

import java.util.ArrayList;

import static com.kvest.tests.provider.TestProviderContract.*;

/**
 * User: roman
 * Date: 11/28/14
 * Time: 5:15 PM
 */
public class AsyncQueryHandlerActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String[] ID_PROJECTION = new String[]{Tables.Tests.Columns._ID};
    private static final int LOAD_TEST_DATA = 0;
    private static final int SINGLE_INSERT_TOKEN = 0;
    private static final int DELETE_FIRST_TOKEN = 1;
    private static final int DELETE_FIRST_THREE_TOKEN = 2;
    private static final int UPDATE_TYPE_TOKEN = 3;
    private static final int UPDATE_TYPE_BATCH_TOKEN = 4;

    private TestAdapter testAdapter;
    private EditText typeEdit;
    private EditText nameEdit;
    private EditText oldTypeEdit;
    private EditText newTypeEdit;

    private TestAsyncQueryHandler testAsyncQueryHandler;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AsyncQueryHandlerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_query_handler_activity);

        init();

        if (savedInstanceState == null) {
            getLoaderManager().initLoader(LOAD_TEST_DATA, null, this);
        }

        testAsyncQueryHandler = new TestAsyncQueryHandler(getContentResolver());
    }

    private void init() {
        ListView listView = (ListView)findViewById(R.id.list_view);
        testAdapter = new TestAdapter(this);
        listView.setAdapter(testAdapter);

        typeEdit = (EditText)findViewById(R.id.type_edit);
        nameEdit = (EditText)findViewById(R.id.name_edit);
        oldTypeEdit = (EditText)findViewById(R.id.old_type_edit);
        newTypeEdit = (EditText)findViewById(R.id.new_type_edit);
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        findViewById(R.id.multi_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMultipleItems();
            }
        });
        findViewById(R.id.delete_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFirst();
            }
        });
        findViewById(R.id.delete_three_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFirstThree();
            }
        });
        findViewById(R.id.change_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeType();
            }
        });
        findViewById(R.id.change_type_batch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTypeBatch();
            }
        });
    }

    private void deleteFirstThree () {
        testAsyncQueryHandler.startQuery(DELETE_FIRST_THREE_TOKEN, null, TESTS_URI, ID_PROJECTION, null, null, null);
    }

    private void deleteFirst() {
        testAsyncQueryHandler.startQuery(DELETE_FIRST_TOKEN, null, TESTS_URI, ID_PROJECTION, null, null, null);
    }


    private void changeTypeBatch() {
        if (oldTypeEdit.getText().equals("") || newTypeEdit.getText().equals("")) {
            return;
        }

        testAsyncQueryHandler.startQuery(UPDATE_TYPE_BATCH_TOKEN, newTypeEdit.getText().toString(), TESTS_URI, ID_PROJECTION,
                                         Tables.Tests.Columns.TYPE + "=?", new String[]{oldTypeEdit.getText().toString()}, null);
    }

    private void changeType() {
        if (oldTypeEdit.getText().equals("") || newTypeEdit.getText().equals("")) {
            return;
        }

        ContentValues cv = new ContentValues(1);
        cv.put(Tables.Tests.Columns.TYPE, Integer.parseInt(newTypeEdit.getText().toString()));

        testAsyncQueryHandler.startUpdate(UPDATE_TYPE_TOKEN, null, TESTS_URI, cv,
                                          Tables.Tests.Columns.TYPE + "=?", new String[]{oldTypeEdit.getText().toString()});
    }

    private void addNewMultipleItems() {
        if (typeEdit.getText().equals("")) {
            return;
        }

        int count = 3;
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(count);
        for (int i = 0; i < count; ++i) {
            ContentValues cv = new ContentValues(3);
            cv.put(Tables.Tests.Columns.TYPE, Integer.parseInt(typeEdit.getText().toString()));
            cv.put(Tables.Tests.Columns.NAME, nameEdit.getText().toString() + "[" + i + "]");
            cv.put(Tables.Tests.Columns.DESCRIPTION, "This is description " + i);

            ContentProviderOperation insertOperation = ContentProviderOperation.newInsert(TESTS_URI).withValues(cv).build();
            operations.add(insertOperation);
        }

        try {
            getContentResolver().applyBatch(CONTENT_AUTHORITY, operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private void addNewItem() {
        if (typeEdit.getText().equals("")) {
            return;
        }

        ContentValues cv = new ContentValues(3);
        cv.put(Tables.Tests.Columns.TYPE, Integer.parseInt(typeEdit.getText().toString()));
        cv.put(Tables.Tests.Columns.NAME, nameEdit.getText().toString());
        cv.put(Tables.Tests.Columns.DESCRIPTION, "This is description");

        testAsyncQueryHandler.startInsert(SINGLE_INSERT_TOKEN, null, TESTS_URI, cv);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOAD_TEST_DATA :
                return new CursorLoader(this, TESTS_URI, TestAdapter.PROJECTION, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOAD_TEST_DATA :
                Log.d("KVEST_TAG", "LOAD_TEST_DATA loaded");
                testAdapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOAD_TEST_DATA :
                testAdapter.swapCursor(null);
                break;
        }
    }

    public class TestAsyncQueryHandler extends AsyncQueryHandler {
        public TestAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            Log.d("KVEST_TAG", "onQueryComplete");
            ArrayList<ContentProviderOperation> operations;
            switch (token) {
                case DELETE_FIRST_TOKEN :
                    cursor.moveToFirst();
                    if (!cursor.isAfterLast()) {
                        long id = cursor.getLong(cursor.getColumnIndex(Tables.Tests.Columns._ID));
                        Uri uri = Uri.withAppendedPath(TESTS_URI, Long.toString(id));

                        this.startDelete(token, null, uri, null, null);
                    }
                    break;
                case DELETE_FIRST_THREE_TOKEN :
                    cursor.moveToFirst();
                    int count = 3;
                    operations = new ArrayList<ContentProviderOperation>(count);
                    for (int i = 0; i < 3; ++i) {
                        if (cursor.isAfterLast()) {
                            break;
                        }

                        long id = cursor.getLong(cursor.getColumnIndex(Tables.Tests.Columns._ID));
                        Uri uri = Uri.withAppendedPath(TESTS_URI, Long.toString(id));

                        ContentProviderOperation deleteOperation = ContentProviderOperation.newDelete(uri).build();
                        operations.add(deleteOperation);

                        cursor.moveToNext();
                    }

                    if (operations.size() > 0) {
                        try {
                            getContentResolver().applyBatch(CONTENT_AUTHORITY, operations);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (OperationApplicationException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case UPDATE_TYPE_BATCH_TOKEN :
                    cursor.moveToFirst();
                    int newType = Integer.parseInt((String)cookie);
                    operations = new ArrayList<ContentProviderOperation>(cursor.getCount());
                    while (!cursor.isAfterLast()) {
                        long id = cursor.getLong(cursor.getColumnIndex(Tables.Tests.Columns._ID));
                        Uri uri = Uri.withAppendedPath(TESTS_URI, Long.toString(id));

                        ContentProviderOperation updateOperation = ContentProviderOperation.newUpdate(uri).withValue(Tables.Tests.Columns.TYPE, newType).build();
                        operations.add(updateOperation);

                        cursor.moveToNext();
                    }
                    if (operations.size() > 0) {
                        try {
                            getContentResolver().applyBatch(CONTENT_AUTHORITY, operations);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (OperationApplicationException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            cursor.close();
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            Log.d("KVEST_TAG", "onInsertComplete " + uri);
        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
            Log.d("KVEST_TAG", "onUpdateComplete");
        }

        @Override
        protected void onDeleteComplete(int token, Object cookie, int result) {
            Log.d("KVEST_TAG", "onDeleteComplete");
        }
    }
}
