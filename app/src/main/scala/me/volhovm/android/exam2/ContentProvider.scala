package me.volhovm.android.exam2

import android.content.{ContentProvider, ContentValues, UriMatcher}
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.provider.BaseColumns
import android.text.TextUtils

object MyContentProvider {
  val AUTHORITY: String = "me.volhovm.android.exam2.provider"
  
  val MAIN_CONTENT_URI: Uri = Uri.parse("content://" + AUTHORITY + "/" + DatabaseHelper.FIRST_TABLE_NAME)
  val AUXILIARY_CONTENT_URI: Uri = Uri.parse("content://" + AUTHORITY + "/" + DatabaseHelper.PLAYLISTS_TABLE_NAME)
  
  val FIRST_ITEMS = 1
  val FIRST_ITEM_ID = 2
  val SECOND_ITEMS = 3
  val SECOND_ITEMS_ID = 4
  private val sUriMatcher: UriMatcher = new UriMatcher(0)
  sUriMatcher.addURI(AUTHORITY, DatabaseHelper.FIRST_TABLE_NAME, FIRST_ITEMS)
  sUriMatcher.addURI(AUTHORITY, DatabaseHelper.FIRST_TABLE_NAME + "/#", FIRST_ITEM_ID)
  sUriMatcher.addURI(AUTHORITY, DatabaseHelper.PLAYLISTS_TABLE_NAME, SECOND_ITEMS)
  sUriMatcher.addURI(AUTHORITY, DatabaseHelper.PLAYLISTS_TABLE_NAME + "/#", SECOND_ITEMS_ID)
}

class MyContentProvider extends ContentProvider {
  import me.volhovm.android.exam2.MyContentProvider._

  private var mDbHelper: DatabaseHelper = null

  override def onCreate(): Boolean = {
    mDbHelper = new DatabaseHelper(getContext)
    false
  }

  override def getType(uri: Uri): String = sUriMatcher.`match`(uri) match {
    case FIRST_ITEMS => "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + DatabaseHelper.FIRST_TABLE_NAME
    case FIRST_ITEM_ID => "vnd.android.cursor.item/vnd" + AUTHORITY + "." + DatabaseHelper.FIRST_TABLE_NAME
    case SECOND_ITEMS => "vnd.android.cursor.item/vnd" + AUTHORITY + "." + DatabaseHelper.PLAYLISTS_TABLE_NAME
    case SECOND_ITEMS_ID => "vnd.android.cursor.item/vnd" + AUTHORITY + "." + DatabaseHelper.PLAYLISTS_TABLE_NAME
  }

  override def update(uri: Uri, values: ContentValues, selection: String, selectionArgs: Array[String]): Int = {
    val ret = sUriMatcher.`match`(uri) match {
      case FIRST_ITEMS => mDbHelper.getWritableDatabase.update(DatabaseHelper.FIRST_TABLE_NAME, values, selection, selectionArgs)
      case FIRST_ITEM_ID =>
        if (TextUtils.isEmpty(selection))
          mDbHelper.getWritableDatabase.update(DatabaseHelper.FIRST_TABLE_NAME, values, BaseColumns._ID + "=" + uri.getLastPathSegment, null)
        else
          mDbHelper.getWritableDatabase.update(DatabaseHelper.FIRST_TABLE_NAME, values, BaseColumns._ID + "=" + uri.getLastPathSegment + " and " + selection, selectionArgs)
      case SECOND_ITEMS => mDbHelper.getWritableDatabase.update(DatabaseHelper.PLAYLISTS_TABLE_NAME, values, selection, selectionArgs)
      case SECOND_ITEMS_ID =>
        if (TextUtils.isEmpty(selection))
          mDbHelper.getWritableDatabase.update(DatabaseHelper.PLAYLISTS_TABLE_NAME, values, BaseColumns._ID + "=" + uri.getLastPathSegment, null)
        else
          mDbHelper.getWritableDatabase.update(DatabaseHelper.PLAYLISTS_TABLE_NAME, values, BaseColumns._ID + "=" + uri.getLastPathSegment + " and " + selection, selectionArgs)
      case _ => throw new IllegalArgumentException("URI IS WRONG: " + uri.toString)
    }
    getContext.getContentResolver.notifyChange(uri, null)
    ret
  }

  override def insert(uri: Uri, values: ContentValues): Uri = {
    val ret = sUriMatcher.`match`(uri) match {
      case FIRST_ITEMS => Uri.parse(DatabaseHelper.FIRST_TABLE_NAME + "/" + mDbHelper.getWritableDatabase.insert(DatabaseHelper.FIRST_TABLE_NAME, null, values))
      case SECOND_ITEMS => Uri.parse(DatabaseHelper.PLAYLISTS_TABLE_NAME + "/" + mDbHelper.getWritableDatabase.insert(DatabaseHelper.PLAYLISTS_TABLE_NAME, null, values))
      case a => throw new IllegalArgumentException("URI IS WRONG: " + uri.toString)
    }
    getContext.getContentResolver.notifyChange(uri, null)
    ret
  }

  override def delete(uri: Uri, selection: String, selectionArgs: Array[String]): Int = {
    val ret = sUriMatcher.`match`(uri) match {
      case FIRST_ITEMS => mDbHelper.getWritableDatabase.delete(DatabaseHelper.FIRST_TABLE_NAME, selection, selectionArgs)
      case FIRST_ITEM_ID =>
        if (TextUtils.isEmpty(selection))
          mDbHelper.getWritableDatabase.delete(DatabaseHelper.FIRST_TABLE_NAME, BaseColumns._ID + "=" + uri.getLastPathSegment, null)
        else
          mDbHelper.getWritableDatabase.delete(DatabaseHelper.FIRST_TABLE_NAME, BaseColumns._ID + "=" + uri.getLastPathSegment + " and " + selection, selectionArgs)
      case SECOND_ITEMS => mDbHelper.getWritableDatabase.delete(DatabaseHelper.PLAYLISTS_TABLE_NAME, selection, selectionArgs)
      case SECOND_ITEMS_ID =>
        if (TextUtils.isEmpty(selection))
          mDbHelper.getWritableDatabase.delete(DatabaseHelper.PLAYLISTS_TABLE_NAME, BaseColumns._ID + "=" + uri.getLastPathSegment, null)
        else
          mDbHelper.getWritableDatabase.delete(DatabaseHelper.PLAYLISTS_TABLE_NAME, BaseColumns._ID + "=" + uri.getLastPathSegment + " and " + selection, selectionArgs)
      case _ => throw new IllegalArgumentException("URI IS WRONG: " + uri.toString)
    }
    getContext.getContentResolver.notifyChange(uri, null)
    ret
  }

  override def query(uri: Uri, projection: Array[String], selection: String,
                     selectionArgs: Array[String], sortOrder: String): Cursor = {
    val builder = new SQLiteQueryBuilder()
    sUriMatcher.`match`(uri) match {
      case FIRST_ITEM_ID =>
        builder.setTables(DatabaseHelper.FIRST_TABLE_NAME)
        builder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment)
      case FIRST_ITEMS =>
        builder.setTables(DatabaseHelper.FIRST_TABLE_NAME)
      case SECOND_ITEMS_ID =>
        builder.setTables(DatabaseHelper.PLAYLISTS_TABLE_NAME)
        builder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment)
      case SECOND_ITEMS =>
        builder.setTables(DatabaseHelper.PLAYLISTS_TABLE_NAME)
      case _ => throw new IllegalArgumentException("WRONG URI: " + uri.toString)
    }
    val cursor = builder.query(mDbHelper.getReadableDatabase, projection, selection, selectionArgs, null, null, sortOrder)
    cursor.setNotificationUri(getContext.getContentResolver, uri)
    cursor
  }

}