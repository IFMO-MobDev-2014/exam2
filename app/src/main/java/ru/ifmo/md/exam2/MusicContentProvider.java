package ru.ifmo.md.exam2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MusicContentProvider extends ContentProvider {
    //private static final String LOG_TAG = "myLogs";

    public static final String JUST_MUSIC = "just_music";
    public static final String TRACKS_TABLE_NAME = "tracks";
    public static final String PLAYLISTS_TABLE_NAME = "playlists";

    private static final int TRACKS = 1;
    private static final int TRACKS_ID = 2;
    private static final int PLAYLISTS = 3;
    private static final int PLAYLISTS_ID = 4;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Tables.AUTHORITY, Tables.Tracks.PATH, TRACKS);
        uriMatcher.addURI(Tables.AUTHORITY, Tables.Tracks.PATH + "/#", TRACKS_ID);
        uriMatcher.addURI(Tables.AUTHORITY, Tables.Playlists.PATH, PLAYLISTS);
        uriMatcher.addURI(Tables.AUTHORITY, Tables.Playlists.PATH + "/#", PLAYLISTS_ID);
    }

    private static class DbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = JUST_MUSIC + ".db";
        private static int DATABASE_VERSION = 2;
        Context context;

        private DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            createTables(sqLiteDatabase);
        }

        private void createTables(SQLiteDatabase sqLiteDatabase) {
            String qs = "CREATE TABLE " + TRACKS_TABLE_NAME + " ("
                    + Tables.Tracks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Tables.Tracks.AUTHOR_NAME + " TEXT, "
                    + Tables.Tracks.TITLE_NAME + " TEXT, "
                    + Tables.Tracks.URL_NAME + " TEXT, "
                    + Tables.Tracks.DURATION_NAME + " TEXT, "
                    + Tables.Tracks.POPULARITY_NAME + " TEXT, "
                    + Tables.Tracks.GENRES_NAME + " TEXT, "
                    + Tables.Tracks.YEAR_NAME + " TEXT" + ");";
            sqLiteDatabase.execSQL(qs);
            insertTracks(sqLiteDatabase);
            qs = "CREATE TABLE " + PLAYLISTS_TABLE_NAME + " ("
                    + Tables.Playlists._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Tables.Playlists.TITLE_NAME + " TEXT, "
                    + Tables.Playlists.AUTHOR_NAME + " TEXT, "
                    + Tables.Playlists.YEAR_NAME + " TEXT, "
                    + Tables.Playlists.GENRES_NAME + " TEXT" + ");";
            sqLiteDatabase.execSQL(qs);
            insertPlaylists(sqLiteDatabase);
        }

        private void insertTracks(SQLiteDatabase sqLiteDatabase) {
            JSONArray array = null;
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                context.getResources().openRawResource(R.raw.music)
                        ));

                StringBuilder json = new StringBuilder(1024);
                String tmp;
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                array = new JSONArray(json.toString());

                ContentValues cv = new ContentValues();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item =  (JSONObject) array.get(i);
                    String name = item.getString("name");
                    int k = name.indexOf('–');
                    String author = name.substring(0, k - 1);
                    String title = name.substring(k + 1);
                    cv.put(Tables.Tracks.AUTHOR_NAME, author.trim());
                    cv.put(Tables.Tracks.TITLE_NAME, title.trim());
                    cv.put(Tables.Tracks.URL_NAME, item.getString("url"));
                    cv.put(Tables.Tracks.DURATION_NAME, item.getString("duration"));
                    cv.put(Tables.Tracks.POPULARITY_NAME, item.getString("popularity"));
                    cv.put(Tables.Tracks.GENRES_NAME, item.getString("genres"));
                    cv.put(Tables.Tracks.YEAR_NAME, item.getString("year"));
                    sqLiteDatabase.insert(TRACKS_TABLE_NAME, null, cv);
                    cv.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ContentValues cv = new ContentValues();
            cv.put(Tables.Tracks.AUTHOR_NAME, "Berdnikov");
            cv.put(Tables.Tracks.TITLE_NAME, "Dark motifs");
            cv.put(Tables.Tracks.URL_NAME, "http://somesite.ru");
            cv.put(Tables.Tracks.DURATION_NAME, "3:30");
            cv.put(Tables.Tracks.POPULARITY_NAME, "100");
            cv.put(Tables.Tracks.GENRES_NAME, "[\"classical\", \"pop\"]");
            cv.put(Tables.Tracks.YEAR_NAME, "2015");
            sqLiteDatabase.insert(TRACKS_TABLE_NAME, null, cv);
            cv.clear();
        }

        private void insertPlaylists(SQLiteDatabase sqLiteDatabase) {
            ContentValues cv = new ContentValues();
            cv.put(Tables.Playlists.TITLE_NAME, "All tracks");
            cv.put(Tables.Playlists.AUTHOR_NAME, "[1]");
            sqLiteDatabase.insert(PLAYLISTS_TABLE_NAME, null, cv);
            cv.clear();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRACKS_TABLE_NAME + ";");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PLAYLISTS_TABLE_NAME + ";");
            createTables(sqLiteDatabase);
        }
    }

    public MusicContentProvider() {}

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //Log.d(LOG_TAG, "delete, " + uri.toString());

        int match = uriMatcher.match(uri);
        int affected;

        switch (match) {
            case TRACKS:
                affected = getDb().delete(TRACKS_TABLE_NAME,
                        (!TextUtils.isEmpty(selection) ?
                                " (" + selection + ')' : ""),
                        selectionArgs);
                break;
            case TRACKS_ID:
                long id = ContentUris.parseId(uri);
                affected = getDb().delete(TRACKS_TABLE_NAME,
                        Tables.Tracks._ID + "=" + id
                                + (!TextUtils.isEmpty(selection) ?
                                " (" + selection + ')' : ""),
                        selectionArgs);
                break;
            case PLAYLISTS:
                affected = getDb().delete(PLAYLISTS_TABLE_NAME,
                        (!TextUtils.isEmpty(selection) ?
                                " (" + selection + ')' : ""),
                        selectionArgs);
                break;
            case PLAYLISTS_ID:
                long postId = ContentUris.parseId(uri);
                affected = getDb().delete(PLAYLISTS_TABLE_NAME,
                        Tables.Playlists._ID + "=" + postId
                                + (!TextUtils.isEmpty(selection) ?
                                " (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown element: " +
                        uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public String getType(Uri uri) {
        //Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case TRACKS:
                return Tables.Tracks.CONTENT_TYPE;
            case TRACKS_ID:
                return Tables.Tracks.CONTENT_ITEM_TYPE;
            case PLAYLISTS:
                return Tables.Playlists.CONTENT_TYPE;
            case PLAYLISTS_ID:
                return Tables.Playlists.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown type: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        //Log.d(LOG_TAG, "insert, " + uri.toString());
        int u = uriMatcher.match(uri);
        if (u != TRACKS && u != PLAYLISTS) {
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = initialValues;
        } else {
            values = new ContentValues();
        }

        db = getDb();

        if (u == TRACKS) {
            long rowID = db.insert(TRACKS_TABLE_NAME, null, values);
            if (rowID > 0) {
                Uri resultUri = ContentUris.withAppendedId(Tables.Tracks.CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(resultUri, null);
                return resultUri;
            }
        } else if (u == PLAYLISTS) {
            long rowID = db.insert(PLAYLISTS_TABLE_NAME, null, values);
            if (rowID > 0) {
                Uri resultUri = ContentUris.withAppendedId(Tables.Playlists.CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(resultUri, null);
                return resultUri;
            }
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        //Log.d(LOG_TAG, "onCreate");
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String TABLE_NAME;
        Uri CONTENT_URI;
        switch (uriMatcher.match(uri)) {
            case TRACKS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = Tables.Tracks.TITLE_NAME + " ASC";
                }
                TABLE_NAME = TRACKS_TABLE_NAME;
                CONTENT_URI = Tables.Tracks.CONTENT_URI;
                break;
            case TRACKS_ID: {
                    String id = uri.getLastPathSegment();
                    if (TextUtils.isEmpty(selection)) {
                        selection = Tables.Tracks._ID + " = " + id;
                    } else {
                        selection = selection + " AND " + Tables.Tracks._ID + " = " + id;
                    }
                    TABLE_NAME = TRACKS_TABLE_NAME;
                    CONTENT_URI = Tables.Tracks.CONTENT_URI;
                }
                break;
            case PLAYLISTS:
                //Log.d(LOG_TAG, "URI_POSTS");
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = Tables.Playlists.TITLE_NAME + " ASC";
                }
                TABLE_NAME = PLAYLISTS_TABLE_NAME;
                CONTENT_URI = Tables.Playlists.CONTENT_URI;
                break;
            case PLAYLISTS_ID: {
                    String id = uri.getLastPathSegment();
                    //Log.d(LOG_TAG, "URI_POSTS_ID, " + id);
                    if (TextUtils.isEmpty(selection)) {
                        selection = Tables.Playlists._ID + " = " + id;
                    } else {
                        selection = selection + " AND " + Tables.Playlists._ID + " = " + id;
                    }
                    TABLE_NAME = PLAYLISTS_TABLE_NAME;
                    CONTENT_URI = Tables.Playlists.CONTENT_URI;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = getDb();
        Cursor cursor = db.query(TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int affected;

        switch (uriMatcher.match(uri)) {
            case TRACKS:
                affected = getDb().update(TRACKS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case TRACKS_ID:
                String id = uri.getPathSegments().get(1);
                affected = getDb().update(TRACKS_TABLE_NAME, values,
                        Tables.Tracks._ID + "=" + id
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            case PLAYLISTS:
                affected = getDb().update(PLAYLISTS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case PLAYLISTS_ID:
                String postId = uri.getPathSegments().get(1);
                affected = getDb().update(PLAYLISTS_TABLE_NAME, values,
                        Tables.Playlists._ID + "=" + postId
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return affected;
    }

    private SQLiteDatabase getDb() {
        return dbHelper.getWritableDatabase();
    }
}
