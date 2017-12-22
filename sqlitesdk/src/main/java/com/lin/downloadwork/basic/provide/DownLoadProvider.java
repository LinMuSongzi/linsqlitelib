package com.lin.downloadwork.basic.provide;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadProvider extends ContentProvider {

    public static final String DOWNLOAD_DB_NAME = "download_framwork";
    public static final String PROVIDER_NAME = "com.download.lin.file.provide";
    static UriMatcher matcher;
    static final String QUERY = "query";
    static final String QUERY_WAITTING = "query_waitting";
    static final String UPDATE = "update";
    static final String DELETE = "delete";
    static final String INSERT = "insert";
    static final int QUERY_ALL_CODE = 0x1abcd;
    static final int QUERY_StATUS_CODE = 0x1abc9;
    static final int UPDATE_CODE = 0x2abcd;
    static final int DELETE_ONE_CODE = 0x3abcd;
    static final int INSERT_ONE_CODE = 0x4abcd;
    public static final Uri CONTENT_QUERY_ALL_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + QUERY);


    public static final Uri CONTENT_QUERY_StATUS_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + QUERY_WAITTING);

    public static final Uri CONTENT_UPDATE_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + UPDATE);
    public static final Uri CONTENT_DELETE_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + DELETE);
    public static final Uri CONTENT_INSERT_ONE_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + INSERT);

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PROVIDER_NAME, QUERY_WAITTING, QUERY_StATUS_CODE);
        matcher.addURI(PROVIDER_NAME, QUERY, QUERY_ALL_CODE);
        matcher.addURI(PROVIDER_NAME, UPDATE, UPDATE_CODE);
        matcher.addURI(PROVIDER_NAME, DELETE, DELETE_ONE_CODE);
        matcher.addURI(PROVIDER_NAME, INSERT, INSERT_ONE_CODE);
    }

    private DownLoadProviderImp downLoadProviderImp;

    @Override
    public boolean onCreate() {
        return (downLoadProviderImp = new DownLoadProviderImp(getContext())).create();
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return downLoadProviderImp.query(uri, projection, selection, selectionArgs, sortOrder);
    }


    @Override
    public String getType(Uri uri) {
        return downLoadProviderImp.getType(uri);
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return downLoadProviderImp.insert(uri, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return downLoadProviderImp.delete(uri, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return downLoadProviderImp.update(uri, values, selection, selectionArgs);
    }


}
