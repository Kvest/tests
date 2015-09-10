package com.kvest.tests.ui.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kvest.tests.R;
import com.kvest.tests.provider.TestProviderContract;
import com.kvest.tests.ui.adapter.RecyclerViewCursorTestAdapter;
import com.kvest.tests.ui.adapter.TestAdapter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static com.kvest.tests.provider.TestProviderContract.CONTENT_AUTHORITY;
import static com.kvest.tests.provider.TestProviderContract.TESTS_URI;

/**
 * Created by roman on 9/10/15.
 */
public class CursorAdapterTestActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String[] ID_PROJECTION = new String[]{TestProviderContract.Tables.Tests.Columns._ID};
    private static final int LOAD_TEST_DATA = 0;
    private static final int SINGLE_INSERT_TOKEN = 0;
    private static final int DELETE_FIRST_TOKEN = 1;
    private static final int DELETE_FIRST_THREE_TOKEN = 2;
    private static final int UPDATE_TYPE_TOKEN = 3;
    private static final int UPDATE_TYPE_BATCH_TOKEN = 4;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CursorAdapterTestActivity.class);
        context.startActivity(intent);
    }


    private TestAdapter testAdapter;
    private EditText typeEdit;
    private EditText nameEdit;
    private EditText oldTypeEdit;
    private EditText newTypeEdit;

    private TestAsyncQueryHandler testAsyncQueryHandler;

    private RecyclerViewCursorTestAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cursor_adapter_test_activity);

        testAsyncQueryHandler = new TestAsyncQueryHandler(getContentResolver());

        init();

        getLoaderManager().initLoader(LOAD_TEST_DATA, null, this);
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setItemAnimator(new SlideInUpAnimator());

        adapter = new RecyclerViewCursorTestAdapter(this);

        //set an adapter
        recyclerView.setAdapter(adapter);

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
        findViewById(R.id.set_cursor_null).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoaderManager().destroyLoader(LOAD_TEST_DATA);
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
                TestProviderContract.Tables.Tests.Columns.TYPE + "=?", new String[]{oldTypeEdit.getText().toString()}, null);
    }

    private void changeType() {
        if (oldTypeEdit.getText().equals("") || newTypeEdit.getText().equals("")) {
            return;
        }

        ContentValues cv = new ContentValues(1);
        cv.put(TestProviderContract.Tables.Tests.Columns.TYPE, Integer.parseInt(newTypeEdit.getText().toString()));

        testAsyncQueryHandler.startUpdate(UPDATE_TYPE_TOKEN, null, TESTS_URI, cv,
                TestProviderContract.Tables.Tests.Columns.TYPE + "=?", new String[]{oldTypeEdit.getText().toString()});
    }

    private void addNewMultipleItems() {
        if (typeEdit.getText().equals("")) {
            return;
        }

        int count = 3;
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(count);
        for (int i = 0; i < count; ++i) {
            ContentValues cv = new ContentValues(3);
            cv.put(TestProviderContract.Tables.Tests.Columns.TYPE, Integer.parseInt(typeEdit.getText().toString()));
            cv.put(TestProviderContract.Tables.Tests.Columns.NAME, nameEdit.getText().toString() + "[" + i + "]");
            cv.put(TestProviderContract.Tables.Tests.Columns.DESCRIPTION, "This is description " + i);

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
        cv.put(TestProviderContract.Tables.Tests.Columns.TYPE, Integer.parseInt(typeEdit.getText().toString()));
        cv.put(TestProviderContract.Tables.Tests.Columns.NAME, nameEdit.getText().toString());
        cv.put(TestProviderContract.Tables.Tests.Columns.DESCRIPTION, "This is description");

        testAsyncQueryHandler.startInsert(SINGLE_INSERT_TOKEN, null, TESTS_URI, cv);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOAD_TEST_DATA :
                return new CursorLoader(this, TESTS_URI, RecyclerViewCursorTestAdapter.PROJECTION, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOAD_TEST_DATA :
                Log.d("KVEST_TAG", "LOAD_TEST_DATA loaded");
                adapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("KVEST_TAG", "LOAD_TEST_DATA reset");
        switch (loader.getId()) {
            case LOAD_TEST_DATA :
                adapter.swapCursor(null);
                break;
        }
    }

    public class TestAsyncQueryHandler extends AsyncQueryHandler {
        public TestAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            ArrayList<ContentProviderOperation> operations;
            switch (token) {
                case DELETE_FIRST_TOKEN :
                    cursor.moveToFirst();
                    if (!cursor.isAfterLast()) {
                        long id = cursor.getLong(cursor.getColumnIndex(TestProviderContract.Tables.Tests.Columns._ID));
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

                        long id = cursor.getLong(cursor.getColumnIndex(TestProviderContract.Tables.Tests.Columns._ID));
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
                        long id = cursor.getLong(cursor.getColumnIndex(TestProviderContract.Tables.Tests.Columns._ID));
                        Uri uri = Uri.withAppendedPath(TESTS_URI, Long.toString(id));

                        ContentProviderOperation updateOperation = ContentProviderOperation.newUpdate(uri).withValue(TestProviderContract.Tables.Tests.Columns.TYPE, newType).build();
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
    }
}
